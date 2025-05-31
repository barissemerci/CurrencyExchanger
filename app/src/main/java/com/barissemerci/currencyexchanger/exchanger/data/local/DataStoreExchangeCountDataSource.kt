package com.barissemerci.currencyexchanger.exchanger.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.barissemerci.currencyexchanger.core.data.local.PreferenceKeys
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_count.ExchangeCountDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreExchangeCountDataSource(
    private val dataStore: DataStore<Preferences>
) : ExchangeCountDataSource {

    override val exchangeCount: Flow<Int> = dataStore.data
        .map { prefs ->
            prefs[PreferenceKeys.EXCHANGE_COUNT] ?: 0
        }


    override suspend fun incrementExchangeCount() {

        dataStore.edit { prefs ->
            val current = prefs[PreferenceKeys.EXCHANGE_COUNT] ?: 0

            prefs[PreferenceKeys.EXCHANGE_COUNT] = current + 1

        }
    }


}