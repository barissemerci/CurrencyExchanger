package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.formatAmount(
    scale: Int = 2,
): String {
    return if (this.stripTrailingZeros().scale() <= 0) {
        this.toBigInteger().toString()
    } else {
        this.setScale(scale, RoundingMode.HALF_EVEN).toPlainString()
    }
}