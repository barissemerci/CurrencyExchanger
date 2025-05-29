package com.barissemerci.currencyexchanger.exchanger.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.barissemerci.currencyexchanger.core.data.networking.HttpClientFactory
import com.barissemerci.currencyexchanger.exchanger.data.local.DataStoreExchangeCountDataSource
import com.barissemerci.currencyexchanger.exchanger.data.local.RoomAvailableBalanceDataSource
import com.barissemerci.currencyexchanger.exchanger.data.networking.RemoteExchangeRatesDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_count.ExchangeCountDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates.ExchangeRatesDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase.ConvertCurrencyUseCase
import com.barissemerci.currencyexchanger.exchanger.presentation.ExchangerViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

val exchangerModule = module {

    single { HttpClientFactory.create(CIO.create()) }

    //single {RemoteCoinDataSource(get())} bind CoinDataSource::class  *same as below*
    singleOf(::RemoteExchangeRatesDataSource).bind<ExchangeRatesDataSource>()

    viewModelOf(::ExchangerViewModel)

    single<DataStore<Preferences>> {
        androidContext().dataStore
    }
    singleOf(::DataStoreExchangeCountDataSource).bind<ExchangeCountDataSource>()
    singleOf(::RoomAvailableBalanceDataSource).bind<AvailableBalanceDataSource>()

    singleOf(::ConvertCurrencyUseCase)


}




