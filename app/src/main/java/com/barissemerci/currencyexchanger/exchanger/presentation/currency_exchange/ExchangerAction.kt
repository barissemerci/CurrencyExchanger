package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange


sealed interface ExchangerAction {
    data object OnSubmit : ExchangerAction
    data object OnClickChangeBuyCurrency : ExchangerAction
    data object OnDismissBuyCurrencyList : ExchangerAction

    data class OnChangeSellAmount(val amount: String) : ExchangerAction
    data class OnChangeBuyCurrency(val currencyIndex: Int) : ExchangerAction

    data object OnDismissConversionResultDialog : ExchangerAction

    //TODO DELETE THESE ACTIONS BEFORE PUSHING
    data object Load1000EuroToWallet : ExchangerAction
    data object IncreaseFreeExchangeCount : ExchangerAction
}