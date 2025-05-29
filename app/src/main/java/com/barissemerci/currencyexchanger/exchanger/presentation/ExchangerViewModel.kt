package com.barissemerci.currencyexchanger.exchanger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ExchangerViewModel : ViewModel() {
    private val _state = MutableStateFlow(ExchangerState())
    val state = _state
        .onStart {}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ExchangerState()
        )

    fun onAction(action: ExchangerAction) {
        when (action) {
            is ExchangerAction.OnChangeBuyCurrency -> {
                _state.update { it.copy(selectedBuyCurrency = action.currency) }
            }

            is ExchangerAction.OnChangeSellAmount -> {
                _state.update { it.copy(sellAmount = action.amount) }
            }

            is ExchangerAction.OnChangeSellCurrency -> {
                _state.update { it.copy(selectedSellCurrency = action.currency) }

            }

            ExchangerAction.OnSubmit -> {

                _state.update { it.copy(showTransactionInfo = true) }
            }
        }
    }
}