package com.barissemerci.currencyexchanger.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.barissemerci.currencyexchanger.exchanger.data.local.database.AvailableBalance
import com.barissemerci.currencyexchanger.exchanger.data.local.database.BalanceConverters
import com.barissemerci.currencyexchanger.exchanger.data.local.database.BalanceDao

@Database(entities = [AvailableBalance::class], version = 1)
@TypeConverters(BalanceConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun balanceDao(): BalanceDao
}