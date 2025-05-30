package com.barissemerci.currencyexchanger.exchanger.domain.available_balance

import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface AvailableBalanceDataSource {
    fun getAllBalances(): Flow<List<Balance>>
    suspend fun getBalance(code: String): Balance?
    suspend fun updateBalance(code: String, newAmount: BigDecimal)
}