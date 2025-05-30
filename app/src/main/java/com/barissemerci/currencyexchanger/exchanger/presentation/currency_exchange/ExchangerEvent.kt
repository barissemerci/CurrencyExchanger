package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange

sealed interface ExchangerEvent {
    data object ShowTransactionInfo : ExchangerEvent
    data class ShowTransactionError(val message: String) : ExchangerEvent
}