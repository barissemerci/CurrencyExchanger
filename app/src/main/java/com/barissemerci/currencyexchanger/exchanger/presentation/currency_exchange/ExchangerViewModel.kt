package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.domain.util.onError
import com.barissemerci.currencyexchanger.core.domain.util.onSuccess
import com.barissemerci.currencyexchanger.core.presentation.util.UiText
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.commission_rule.CommissionRuleType
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_count.ExchangeCountDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates.ExchangeRatesDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ConvertBuyAmountUseCase
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ExchangeCurrencyUseCase
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.mappers.toSelectableList
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.utils.formatAmount
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
    private val exchangeCountDataSource: ExchangeCountDataSource,
    private val convertBuyAmountUseCase: ConvertBuyAmountUseCase,
    private val availableBalanceDataSource: AvailableBalanceDataSource

) : ViewModel() {
    private val _state = MutableStateFlow(ExchangerState())
    val state = _state
        .onStart {
            viewModelScope.launch {
                observeRemainingConversions()
                observeAvailableBalances()

                while (isActive) {
                    exchangeRatesDataSource.getExchangeRates().onSuccess { exchangeRates ->
                        _state.update {
                            it.copy(exchangeRates = exchangeRates)
                        }
                        if (exchangeRates != null) {
                            _state.update {
                                it.copy(
                                    exchangeCurrencyList = exchangeRates.rates.toSelectableList(
                                        it.selectedBuyCurrency
                                    )
                                )
                            }
                        }
                    }.onError {
                        eventChannel.send(
                            ExchangerEvent.ShowFetchingCurrencyError(
                                UiText.StringResource(
                                    R.string.error_fetching_currencies
                                )
                            )
                        )
                    }
                    updateBuyAmount()
                    delay(5000)
                }

            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ExchangerState()
        )


    private val eventChannel = Channel<ExchangerEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ExchangerAction) {
        when (action) {
            is ExchangerAction.OnChangeBuyCurrency -> {
                _state.update {
                    it.copy(
                        exchangeCurrencyList =
                            it.exchangeCurrencyList.mapIndexed { index, item ->
                                if (index == action.currencyIndex) item.copy(isSelected = true) else item.copy(
                                    isSelected = false
                                )
                            },
                        selectedBuyCurrency = it.exchangeCurrencyList[action.currencyIndex].currency,
                        showSellCurrencyList = false
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
                viewModelScope.launch {

                    val exchangeRate =
                        _state.value.exchangeRates?.rates?.get(_state.value.selectedBuyCurrency)
                    if (exchangeRate != null) {
                        convertCurrencyUseCase.invoke(
                            exchangeRate = exchangeRate,
                            fromCurrency = _state.value.selectedSellCurrency,
                            toCurrency = _state.value.selectedBuyCurrency,
                            amount = _state.value.sellAmountValue,
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

                }
            }

            ExchangerAction.OnDismissConversionResultDialog -> {
                _state.update { it.copy(showConversionResultDialog = false) }
            }

            else -> Unit
        }
    }

    private fun observeRemainingConversions() {
        viewModelScope.launch {
            exchangeCountDataSource.exchangeCount.collect { count ->
                _state.update {
                    it.copy(remainingFreeConversions = count)
                }
            }
        }
    }

    private fun updateBuyAmount() {
        val state = _state.value
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