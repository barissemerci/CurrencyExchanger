package com.barissemerci.currencyexchanger.core.presentation.util

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object ExchangerScreen : Route

    @Serializable
    data class CurrencySelectScreen(val isSellCurrency: Boolean) : Route

}