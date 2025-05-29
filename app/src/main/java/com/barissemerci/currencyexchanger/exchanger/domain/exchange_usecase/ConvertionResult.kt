package com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase

import java.math.BigDecimal

data class ConversionResult(
    val fromCurrency: String,
    val toCurrency: String,
    val sellAmount: BigDecimal,
    val buyAmount: BigDecimal,
    val commissionFee: BigDecimal,
    val rate: BigDecimal
)