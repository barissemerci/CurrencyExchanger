package com.barissemerci.currencyexchanger.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.barissemerci.currencyexchanger.core.data.local.PreferenceKeys
import com.barissemerci.currencyexchanger.core.domain.FirstLaunchDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreFirstLaunchDataSource(
    private val dataStore: DataStore<Preferences>
) : FirstLaunchDataSource {
    override val isFirstLaunch: Flow<Boolean> = dataStore.data
        .map { it[PreferenceKeys.IS_FIRST_LAUNCH] ?: true }
    override suspend fun setFirstLaunchDone() {
        dataStore.edit { it[PreferenceKeys.IS_FIRST_LAUNCH] = false }

    }
}