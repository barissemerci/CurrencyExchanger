package com.barissemerci.currencyexchanger.exchanger.presentation


sealed interface ExchangerAction {
    data object OnSubmit : ExchangerAction
    data object OnClickChangeBuyCurrency : ExchangerAction
    data object OnDismissBuyCurrencyList : ExchangerAction

    data class OnChangeSellAmount(val amount: String) : ExchangerAction
    data class OnChangeBuyCurrency(val currencyIndex: Int) : ExchangerAction
}