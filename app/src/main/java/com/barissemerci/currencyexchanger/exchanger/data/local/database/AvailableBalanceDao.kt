package com.barissemerci.currencyexchanger.exchanger.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Query("SELECT * FROM available_balances")
    fun observeBalances(): Flow<List<AvailableBalance>>

    @Query("SELECT * FROM available_balances WHERE currencyCode = :code")
    suspend fun getBalance(code: String): AvailableBalance?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBalance(balance: AvailableBalance)
}