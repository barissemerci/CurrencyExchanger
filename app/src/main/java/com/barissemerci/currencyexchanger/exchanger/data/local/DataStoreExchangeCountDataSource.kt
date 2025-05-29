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

    override val remainingFreeConversions: Flow<Int> = dataStore.data
        .map { prefs ->
            prefs[PreferenceKeys.FREE_CONVERSION_COUNT] ?: 5
        }

    override suspend fun decrementFreeConversion() {
        dataStore.edit { prefs ->
            val current = prefs[PreferenceKeys.FREE_CONVERSION_COUNT] ?: 5
            if (current > 0) {
                prefs[PreferenceKeys.FREE_CONVERSION_COUNT] = current - 1
            }
        }
    }


}