package com.barissemerci.currencyexchanger.exchanger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barissemerci.currencyexchanger.core.domain.util.onError
import com.barissemerci.currencyexchanger.core.domain.util.onSuccess
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_count.ExchangeCountDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates.ExchangeRatesDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ConvertCurrencyUseCase
import com.barissemerci.currencyexchanger.exchanger.presentation.mappers.toSelectableList
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
import java.math.RoundingMode

class ExchangerViewModel(
    exchangeRatesDataSource: ExchangeRatesDataSource,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val exchangeCountDataSource: ExchangeCountDataSource,
    //TODO DELETE IT BEFORE PUSHING
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
                                    exchangeCurrencyList = exchangeRates.rates.toSelectableList()
                                )
                            }
                        }
                    }.onError { }
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
                            sellAmountValue = parsed ?: BigDecimal.ZERO
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
                            amount = _state.value.sellAmountValue
                        ).onSuccess { conversionResult ->
                            _state.update {
                                it.copy(
                                    showConversionResultDialog = true,
                                    conversionResult = conversionResult
                                )
                            }
                        }.onError {
                            eventChannel.send(ExchangerEvent.ShowTransactionError("You don't have enough money"))
                        }
                    }

                }
                _state.update { it.copy(showConversionResultDialog = true) }
            }

            ExchangerAction.OnClickChangeBuyCurrency -> {
                _state.update { it.copy(showBuyCurrencyList = true) }

            }


            ExchangerAction.OnDismissBuyCurrencyList -> {
                _state.update { it.copy(showBuyCurrencyList = false) }
            }

            ExchangerAction.IncreaseFreeExchangeCount -> {
                viewModelScope.launch {
                    exchangeCountDataSource.incrementFreeConversion()

                }
            }

            ExchangerAction.Load1000EuroToWallet -> {
                viewModelScope.launch {
                    availableBalanceDataSource.updateBalance("EUR", BigDecimal(1000))
                }
            }

            ExchangerAction.OnDismissConversionResultDialog -> {
                _state.update { it.copy(showConversionResultDialog = false) }
            }
        }
    }

    private fun observeRemainingConversions() {
        viewModelScope.launch {
            exchangeCountDataSource.remainingFreeConversions.collect { count ->
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
        println("Converted fromRate: $fromRate")
        val toRate = rates.rates[state.selectedBuyCurrency]
        println("Converted toRate: $toRate")

        if (fromRate != null && toRate != null) {
            val rate = toRate.divide(fromRate, 6, RoundingMode.HALF_EVEN)
            val converted = amount.multiply(rate)
            val formatted = converted.setScale(2, RoundingMode.HALF_EVEN).toPlainString()
            println("Converted amount: $formatted")
            _state.update { it.copy(buyAmount = formatted) }
        }
    }


    private fun observeAvailableBalances() {
    viewModelScope.launch {
        availableBalanceDataSource.getAllBalances().collect{balances->
            _state.update {
                it.copy(availableBalances = balances)
            }


        }
    }

    }
}