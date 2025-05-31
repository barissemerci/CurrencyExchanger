package com.barissemerci.currencyexchanger.exchanger.domain.commission_rule

import java.math.BigDecimal

class CommissionRuleManager {
    private val ruleMap: Map<CommissionRuleType, CommissionRule> = mapOf(
        CommissionRuleType.EVERY_TENTH_FREE to EveryTenthFreeRule(),
        CommissionRuleType.FREE_UNDER_200_EURO to FreeUnder200EuroRule(),
        CommissionRuleType.FIRST_5_FREE to First5ConversionsFreeRule()
    )

    fun calculateCommission(
        ruleType: CommissionRuleType,
        conversionCount: Int,
        amount: BigDecimal
    ): BigDecimal {
        val rule = ruleMap[ruleType]
            ?: throw IllegalArgumentException("Invalid rule type: $ruleType")
        return rule.calculateCommission(conversionCount, amount)
    }
}