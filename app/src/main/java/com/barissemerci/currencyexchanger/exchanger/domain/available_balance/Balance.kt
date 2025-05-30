package com.barissemerci.currencyexchanger.exchanger.domain.available_balance

import java.math.BigDecimal

data class Balance(
    val currencyType: String,
    val currencyAmount: BigDecimal,
)