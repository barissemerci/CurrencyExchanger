package com.barissemerci.currencyexchanger.exchanger.domain.commission_rule

import java.math.BigDecimal

interface CommissionRule {
    fun calculateCommission(
        conversionCount: Int,
        amount: BigDecimal
    ): BigDecimal
}