package com.barissemerci.currencyexchanger.exchanger.presentation.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.formatAmount(): String {
    return if (this.stripTrailingZeros().scale() <= 0) {
        this.toBigInteger().toString()
    } else {
        this.setScale(2, RoundingMode.HALF_EVEN).toPlainString()
    }
}