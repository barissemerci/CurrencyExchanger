package com.barissemerci.currencyexchanger.core.di

import androidx.room.Room
import com.barissemerci.currencyexchanger.core.data.local.database.AppDatabase
import com.barissemerci.currencyexchanger.exchanger.data.local.database.BalanceDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "my_database"
        ).build()
    }

    single<BalanceDao> {
        get<AppDatabase>().balanceDao()
    }


}