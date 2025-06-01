package com.barissemerci.currencyexchanger

import android.app.Application
import com.barissemerci.currencyexchanger.core.di.appModule
import com.barissemerci.currencyexchanger.core.domain.usecase.InitializeBalanceUseCase
import com.barissemerci.currencyexchanger.exchanger.di.exchangerModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class CurrencyExchangerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyExchangerApp)
            modules(
                appModule,
                exchangerModule
            )
        }

        val initializeBalanceUseCase: InitializeBalanceUseCase =
            GlobalContext.get().get()
        CoroutineScope(Dispatchers.Default).launch {
            initializeBalanceUseCase()
        }
    }
}