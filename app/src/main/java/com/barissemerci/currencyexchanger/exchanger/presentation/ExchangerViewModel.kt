package com.barissemerci.currencyexchanger.exchanger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barissemerci.currencyexchanger.core.domain.util.onError
import com.barissemerci.currencyexchanger.core.domain.util.onSuccess
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_count.ExchangeCountDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates.ExchangeRatesDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ConvertCurrencyUseCase
import com.barissemerci.currencyexchanger.exchanger.presentation.mappers.toSelectableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.math.RoundingMode

class ExchangerViewModel(
    exchangeRatesDataSource: ExchangeRatesDataSource,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val exchangeCountDataSource: ExchangeCountDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(ExchangerState())


    val state = _state
        .onStart {
            viewModelScope.launch {
                observeRemainingConversions()

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
                val parsed = action.amount.toBigDecimal()

                _state.update {
                    it.copy(
                        sellAmountText = action.amount,
                        sellAmountValue = parsed
                    )
                }
                updateBuyAmount()
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
                        )
                    }

                }
                _state.update { it.copy(showTransactionInfo = true) }
            }

            ExchangerAction.OnClickChangeBuyCurrency -> {
                _state.update { it.copy(showBuyCurrencyList = true) }

            }


            ExchangerAction.OnDismissBuyCurrencyList -> {
                _state.update { it.copy(showBuyCurrencyList = false) }
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
}