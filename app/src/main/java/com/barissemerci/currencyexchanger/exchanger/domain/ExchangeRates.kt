package com.barissemerci.currencyexchanger.exchanger.domain

import java.math.BigDecimal


data class ExchangeRates(
    val base: String,
    val date: String,
    val rates: Map<String, BigDecimal>
)