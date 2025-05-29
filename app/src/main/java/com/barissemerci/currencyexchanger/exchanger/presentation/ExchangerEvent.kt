package com.barissemerci.currencyexchanger.exchanger.presentation

sealed interface ExchangerEvent {
    data object ShowTransactionInfo : ExchangerEvent
    data object ShowTransactionError : ExchangerEvent
}