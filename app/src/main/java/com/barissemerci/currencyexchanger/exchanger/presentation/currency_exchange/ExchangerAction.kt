package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange


sealed interface ExchangerAction {
    data object OnSubmit : ExchangerAction
    data object OnClickChangeBuyCurrency : ExchangerAction

    data class OnChangeSellAmount(val amount: String) : ExchangerAction
    data class OnChangeBuyCurrency(val currencyIndex: Int) : ExchangerAction

    data object OnDismissConversionResultDialog : ExchangerAction
}