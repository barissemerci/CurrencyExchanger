package com.barissemerci.currencyexchanger.exchanger.presentation

import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.Selectable
import com.barissemerci.currencyexchanger.exchanger.domain.ExchangeRates
import java.math.BigDecimal

data class ExchangerState(
    val exchangeRates: ExchangeRates? = null,
    val exchangeCurrencyList : List<Selectable> = emptyList(),
    val availableBalances: List<AvailableBalance> = emptyList(),

    val sellAmountText: String = "",
    val sellAmountValue: BigDecimal? = null,

    val buyAmount: String = "",
    val selectedBuyCurrency: String = "USD",


    val selectedSellCurrency: String = "EUR",

    val showTransactionInfo: Boolean = false,
    val showSellCurrencyList : Boolean = false,
    val showBuyCurrencyList : Boolean = false,
)

data class AvailableBalance(
    val currencyType: String,
    val currencyAmount: Double,
)