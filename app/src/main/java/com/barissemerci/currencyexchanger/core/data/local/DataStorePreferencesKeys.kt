package com.barissemerci.currencyexchanger.core.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

object PreferenceKeys {
    val EXCHANGE_COUNT = intPreferencesKey("exchange_count")
    val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")

}