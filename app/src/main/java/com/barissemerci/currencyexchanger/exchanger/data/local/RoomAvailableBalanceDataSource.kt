package com.barissemerci.currencyexchanger.exchanger.data.local

import com.barissemerci.currencyexchanger.exchanger.data.local.database.AvailableBalance
import com.barissemerci.currencyexchanger.exchanger.data.local.database.BalanceDao
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class RoomAvailableBalanceDataSource(
    private val balanceDao: BalanceDao

) : AvailableBalanceDataSource {
    override fun getAllBalances(): Flow<List<AvailableBalance>> {
        return balanceDao.observeBalances()
    }

    override suspend fun getBalance(code: String): AvailableBalance? {
        return balanceDao.getBalance(code)
    }

    override suspend fun updateBalance(code: String, newAmount: BigDecimal) {
        balanceDao.upsertBalance(AvailableBalance(code, newAmount))
    }
}