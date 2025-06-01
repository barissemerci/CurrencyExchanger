package com.barissemerci.currencyexchanger.core.domain

import kotlinx.coroutines.flow.Flow

interface FirstLaunchDataSource {
    val isFirstLaunch: Flow<Boolean>
    suspend fun setFirstLaunchDone()
}