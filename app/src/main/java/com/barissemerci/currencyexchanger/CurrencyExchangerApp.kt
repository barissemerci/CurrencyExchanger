package com.barissemerci.currencyexchanger

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CurrencyExchangerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyExchangerApp)
            modules()
        }
    }
}