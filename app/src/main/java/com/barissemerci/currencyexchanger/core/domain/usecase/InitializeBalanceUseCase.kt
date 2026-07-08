package com.barissemerci.currencyexchanger.core.domain.usecase

import com.barissemerci.currencyexchanger.core.domain.FirstLaunchDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import kotlinx.coroutines.flow.first
import java.math.BigDecimal

class InitializeBalanceUseCase(
    private val firstLaunchDataSource: FirstLaunchDataSource,
    private val balanceDataSource: AvailableBalanceDataSource
) {
    suspend operator fun invoke() {
        if (firstLaunchDataSource.isFirstLaunch.first()) {
            balanceDataSource.updateBalance("EUR", BigDecimal(1000))
            firstLaunchDataSource.setFirstLaunchDone()
        }
    }
}
