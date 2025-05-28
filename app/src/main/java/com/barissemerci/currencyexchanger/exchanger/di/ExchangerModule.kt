package com.barissemerci.currencyexchanger.exchanger.di

import com.barissemerci.currencyexchanger.core.data.networking.HttpClientFactory
import com.barissemerci.currencyexchanger.exchanger.data.networking.RemoteExchangeRatesDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.ExchangeRatesDataSource
import com.barissemerci.currencyexchanger.exchanger.presentation.ExchangerViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val exchangerModule = module {

    single { HttpClientFactory.create(CIO.create()) }

    //single {RemoteCoinDataSource(get())} bind CoinDataSource::class  *same as below*
    singleOf(::RemoteExchangeRatesDataSource).bind<ExchangeRatesDataSource>()

    viewModelOf(::ExchangerViewModel)
}