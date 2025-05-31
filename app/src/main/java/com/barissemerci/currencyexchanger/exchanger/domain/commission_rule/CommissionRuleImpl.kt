package com.barissemerci.currencyexchanger.exchanger.domain.commission_rule

import java.math.BigDecimal

// Every 10th conversion is free
class EveryTenthFreeRule : CommissionRule {
    private val commissionRate: BigDecimal = BigDecimal("0.01")
    private val thConversionCount = 10
    override fun calculateCommission(conversionCount: Int, amount: BigDecimal): BigDecimal {
        return if (conversionCount % thConversionCount == 0) {
            BigDecimal.ZERO
        } else {
            amount.multiply(commissionRate)
        }
    }
}

// Amount below 200 EUR is free
class FreeUnder200EuroRule : CommissionRule {
    private val commissionRate: BigDecimal = BigDecimal("0.01")
    private val threshold = BigDecimal("200")
    override fun calculateCommission(conversionCount: Int, amount: BigDecimal): BigDecimal {
        return if (amount <= threshold) {
            BigDecimal.ZERO
        } else {
            amount.multiply(commissionRate)
        }
    }
}

// For first 5 conversion, commission is free
class First5ConversionsFreeRule : CommissionRule {
    private val freeLimit: Int = 5
    private val commissionRate: BigDecimal = BigDecimal("0.01")
    override fun calculateCommission(conversionCount: Int, amount: BigDecimal): BigDecimal {
        return if (conversionCount <= freeLimit) {
            BigDecimal.ZERO
        } else {
            amount.multiply(commissionRate)
        }
    }
}
