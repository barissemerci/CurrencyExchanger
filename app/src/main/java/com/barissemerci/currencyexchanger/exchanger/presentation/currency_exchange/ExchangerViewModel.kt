package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.domain.util.Result
import com.barissemerci.currencyexchanger.core.domain.util.onError
import com.barissemerci.currencyexchanger.core.domain.util.onSuccess
import com.barissemerci.currencyexchanger.core.presentation.util.UiText
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.commission_rule.CommissionRuleType
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates.ExchangeRatesDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ConvertBuyAmountUseCase
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ExchangeCurrencyUseCase
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.mappers.toSelectableList
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.utils.formatAmount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ExchangerViewModel(
    exchangeRatesDataSource: ExchangeRatesDataSource,
    private val convertCurrencyUseCase: ExchangeCurrencyUseCase,
    private val convertBuyAmountUseCase: ConvertBuyAmountUseCase,
    private val availableBalanceDataSource: AvailableBalanceDataSource

) : ViewModel() {
    private val _state = MutableStateFlow(ExchangerState())

    private val exchangeRatesFlow = flow {
        while (currentCoroutineContext().isActive) {
            exchangeRatesDataSource.getExchangeRates()
                .onSuccess { exchangeRates ->
                    emit(Result.Success(exchangeRates))
                }
                .onError {
                    emit(Result.Error(it))
                }
            delay(5000)
        }
    }.flowOn(Dispatchers.IO)

    val state = combine(
        _state,
        exchangeRatesFlow
    ) { currentState, ratesResult ->
        when (ratesResult) {
            is Result.Success -> {
                val exchangeRates = ratesResult.data
                val buyList = exchangeRates?.rates?.toSelectableList(currentState.selectedBuyCurrency) ?: emptyList()
                val sellList = exchangeRates?.rates?.toSelectableList(currentState.selectedSellCurrency) ?: emptyList()

                val newState = currentState.copy(
                    exchangeRates = exchangeRates,
                    exchangeBuyCurrencyList = buyList,
                    exchangeSellCurrencyList = sellList
                )

                updateBuyAmount()
                newState
            }
            is Result.Error -> {
                viewModelScope.launch {
                    eventChannel.send(
                        ExchangerEvent.ShowFetchingCurrencyError(
                            UiText.StringResource(R.string.error_fetching_currencies)
                        )
                    )
                }
                currentState
            }
        }
    }.onStart {
        observeAvailableBalances()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExchangerState()
    )

    private val eventChannel = Channel<ExchangerEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ExchangerAction) {
        when (action) {
            is ExchangerAction.OnChangeSellCurrency -> {
                _state.update {
                    it.copy(
                        exchangeSellCurrencyList =
                            state.value.exchangeSellCurrencyList.mapIndexed { index, item ->
                                if (index == action.currencyIndex) item.copy(isSelected = true) else item.copy(
                                    isSelected = false
                                )
                            },
                        selectedSellCurrency = state.value.exchangeSellCurrencyList[action.currencyIndex].currency,
                    )
                }
            }

            is ExchangerAction.OnChangeBuyCurrency -> {
                _state.update {
                    it.copy(
                        exchangeBuyCurrencyList =
                            state.value.exchangeBuyCurrencyList.mapIndexed { index, item ->
                                if (index == action.currencyIndex) item.copy(isSelected = true) else item.copy(
                                    isSelected = false
                                )
                            },
                        selectedBuyCurrency = state.value.exchangeBuyCurrencyList[action.currencyIndex].currency,
                    )
                }
            }

            is ExchangerAction.OnChangeSellAmount -> {
                val parsed = action.amount.toBigDecimalOrNull()

                if (parsed != null || action.amount.isBlank()) {
                    _state.update {
                        it.copy(
                            sellAmountText = action.amount,
                            sellAmountValue = parsed ?: BigDecimal.ZERO,
                            isSubmitButtonEnabled = action.amount.isNotBlank() && parsed != BigDecimal.ZERO
                        )
                    }

                    updateBuyAmount()
                }


            }


            ExchangerAction.OnSubmit -> {
                Log.d("ExchangerViewModel", "OnSubmit")
                viewModelScope.launch {
                    val exchangeRate =
                        state.value.exchangeRates?.rates?.get(state.value.selectedBuyCurrency)
                    if (exchangeRate != null) {
                        convertCurrencyUseCase.invoke(
                            exchangeRate = exchangeRate,
                            fromCurrency = state.value.selectedSellCurrency,
                            toCurrency = state.value.selectedBuyCurrency,
                            amount = state.value.sellAmountValue,
                            ruleType = CommissionRuleType.FIRST_5_FREE
                        ).onSuccess { conversionResult ->
                            _state.update {
                                it.copy(
                                    showConversionResultDialog = true,
                                    conversionResult = conversionResult
                                )
                            }
                        }.onError {
                            eventChannel.send(
                                ExchangerEvent.ShowTransactionError(
                                    UiText.StringResource(
                                        R.string.error_not_enough_money
                                    )
                                )
                            )
                        }
                    }
                    else{
                        Log.e("ExchangerViewModel", "Exchange rate is null")
                    }

                }
            }

            ExchangerAction.OnDismissConversionResultDialog -> {
                _state.update { it.copy(showConversionResultDialog = false) }
            }

            ExchangerAction.OnSwitchCurrencies -> {
                _state.update {
                    it.copy(
                        selectedSellCurrency = it.selectedBuyCurrency,
                        selectedBuyCurrency = it.selectedSellCurrency
                    )
                }
                updateBuyAmount()
            }

            else -> Unit

        }
    }


    private fun updateBuyAmount() {
        val state = state.value
        val rates = state.exchangeRates ?: return
        val amount = state.sellAmountValue

        val fromRate = rates.rates[state.selectedSellCurrency]
        val toRate = rates.rates[state.selectedBuyCurrency]

        if (fromRate != null && toRate != null) {
            val converted = convertBuyAmountUseCase.invoke(
                sellAmount = amount,
                toRate = toRate,
                fromRate = fromRate
            )

            val formatted = converted.formatAmount()
            _state.update { it.copy(buyAmount = formatted) }
        }
    }


    private fun observeAvailableBalances() {
        viewModelScope.launch {
            availableBalanceDataSource.getAllBalances().collect { balances ->
                _state.update {
                    it.copy(availableBalances = balances)
                }
            }
        }

    }
}