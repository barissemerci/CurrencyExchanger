package com.barissemerci.currencyexchanger.exchanger.domain.exchange_count

import kotlinx.coroutines.flow.Flow

interface ExchangeCountDataSource {
   val remainingFreeConversions: Flow<Int>
   suspend fun decrementFreeConversion()

   //TODO DELETE BEFORE PUSHING
   suspend fun incrementFreeConversion()
}
