package com.barissemerci.currencyexchanger.exchanger.domain.exchange_count

import kotlinx.coroutines.flow.Flow

interface ExchangeCountDataSource {
   val exchangeCount: Flow<Int>
   suspend fun incrementExchangeCount()
}
