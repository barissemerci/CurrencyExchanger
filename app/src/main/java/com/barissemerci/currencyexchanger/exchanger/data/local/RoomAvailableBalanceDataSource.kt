package com.barissemerci.currencyexchanger.exchanger.data.local

import com.barissemerci.currencyexchanger.exchanger.data.local.database.AvailableBalance
import com.barissemerci.currencyexchanger.exchanger.data.local.database.BalanceDao
import com.barissemerci.currencyexchanger.exchanger.data.local.mappers.toAvailableBalance
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.Balance
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class RoomAvailableBalanceDataSource(
    private val balanceDao: BalanceDao
) : AvailableBalanceDataSource {
    override fun getAllBalances(): Flow<List<Balance>> {
        return balanceDao.observeBalances().map {
            it.map { balance ->
                Balance(balance.currencyCode, balance.amount)
            }
        }
    }

    override suspend fun getBalance(code: String): Balance? {
        return balanceDao.getBalance(code)?.toAvailableBalance()
    }

    override suspend fun updateBalance(code: String, newAmount: BigDecimal) {
        balanceDao.upsertBalance(AvailableBalance(code, newAmount))
    }
}