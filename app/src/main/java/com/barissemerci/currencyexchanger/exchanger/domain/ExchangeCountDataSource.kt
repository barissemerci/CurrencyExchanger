package com.barissemerci.currencyexchanger.exchanger.domain

import kotlinx.coroutines.flow.Flow

interface ExchangeCountDataSource {
   val remainingFreeConversions: Flow<Int>
   suspend fun decrementFreeConversion()
}
