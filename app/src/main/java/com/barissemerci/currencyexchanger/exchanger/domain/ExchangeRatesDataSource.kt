package com.barissemerci.currencyexchanger.exchanger.domain

import com.barissemerci.currencyexchanger.core.domain.util.NetworkError
import com.barissemerci.currencyexchanger.core.domain.util.Result

interface ExchangeRatesDataSource {
    suspend fun getExchangeRates(): Result<ExchangeRates?, NetworkError>
}