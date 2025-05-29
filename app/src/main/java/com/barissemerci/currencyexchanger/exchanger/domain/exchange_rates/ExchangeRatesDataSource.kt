package com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates

import com.barissemerci.currencyexchanger.core.domain.util.NetworkError
import com.barissemerci.currencyexchanger.core.domain.util.Result

interface ExchangeRatesDataSource {
    suspend fun getExchangeRates(): Result<ExchangeRates?, NetworkError>
}