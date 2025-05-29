package com.barissemerci.currencyexchanger.exchanger.domain.available_balance

import com.barissemerci.currencyexchanger.exchanger.data.local.database.AvailableBalance
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface AvailableBalanceDataSource {
    fun getAllBalances() : Flow<List<AvailableBalance>>
    suspend fun getBalance(code: String): AvailableBalance?
    suspend fun updateBalance(code: String, newAmount: BigDecimal)
}