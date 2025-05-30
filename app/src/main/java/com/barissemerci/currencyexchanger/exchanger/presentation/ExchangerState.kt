package com.barissemerci.currencyexchanger.exchanger.presentation

import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.Selectable
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.Balance
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates.ExchangeRates
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ConversionResult
import java.math.BigDecimal

data class ExchangerState(
    val exchangeRates: ExchangeRates? = null,
    val exchangeCurrencyList: List<Selectable> = emptyList(),
    val availableBalances: List<Balance> = emptyList(),

    val sellAmountText: String = "",
    val sellAmountValue: BigDecimal = BigDecimal.ZERO,

    val buyAmount: String = "",
    val selectedBuyCurrency: String = "USD",


    val selectedSellCurrency: String = "EUR",

    val showConversionResultDialog: Boolean = false,
    val showSellCurrencyList: Boolean = false,
    val showBuyCurrencyList: Boolean = false,


    val remainingFreeConversions: Int = 0,

    val conversionResult: ConversionResult? = null
)

