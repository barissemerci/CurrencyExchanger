package com.barissemerci.currencyexchanger.exchanger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

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

    }

}