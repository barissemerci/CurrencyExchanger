package com.barissemerci.currencyexchanger.exchanger.presentation

data class ExchangerState(
    val availableBalances: List<AvailableBalance> = emptyList(),
    val sellAmount: Double = 0.0,
    val selectedSellCurrency: String = "",
    val buyAmount: Double = 0.0,
    val selectedBuyCurrency: String = "",
    val showTransactionInfo: Boolean = false,
)

data class AvailableBalance(
    val currencyType: String,
    val currencyAmount: Double,
)