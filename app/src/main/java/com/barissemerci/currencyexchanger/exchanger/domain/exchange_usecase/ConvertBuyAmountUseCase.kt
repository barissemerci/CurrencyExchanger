package com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase

import java.math.BigDecimal
import java.math.RoundingMode

class ConvertBuyAmountUseCase {
    operator fun invoke(
        sellAmount: BigDecimal,
        toRate: BigDecimal,
        fromRate: BigDecimal
    ): BigDecimal {
        val rate = toRate.divide(fromRate, 6, RoundingMode.HALF_EVEN)
        val converted = sellAmount.multiply(rate)
        return converted
    }
}