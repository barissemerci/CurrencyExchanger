package com.barissemerci.currencyexchanger.exchanger.presentation

sealed interface ExchangerAction {
    data object OnSubmit : ExchangerAction
    data class OnChangeSellCurrency(val currency: String) : ExchangerAction
    data class OnChangeSellAmount(val amount: Double) : ExchangerAction
    data class OnChangeBuyCurrency(val currency: String) : ExchangerAction
}