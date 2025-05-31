package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange

import com.barissemerci.currencyexchanger.core.presentation.util.UiText

sealed interface ExchangerEvent {
    data object ShowTransactionInfo : ExchangerEvent
    data class ShowTransactionError(val message: UiText) : ExchangerEvent
}