package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange

import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.Selectable
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.Balance
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates.ExchangeRates
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ConversionResult
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.utils.formatAmount
import java.math.BigDecimal

data class ExchangerState(
    val exchangeRates: ExchangeRates? = null,
    val exchangeCurrencyList: List<Selectable> = emptyList(),
    val availableBalances: List<Balance> = emptyList(),

    val sellAmountText: String = BigDecimal.ZERO.formatAmount(),
    val sellAmountValue: BigDecimal = BigDecimal.ZERO,

    val buyAmount: String = BigDecimal.ZERO.formatAmount(),
    val selectedBuyCurrency: String = "USD",

    val selectedSellCurrency: String = "EUR",

    val showConversionResultDialog: Boolean = false,
    val showSellCurrencyList: Boolean = false,
    val showBuyCurrencyList: Boolean = false,


    val remainingFreeConversions: Int = 0,

    val conversionResult: ConversionResult? = null
)

