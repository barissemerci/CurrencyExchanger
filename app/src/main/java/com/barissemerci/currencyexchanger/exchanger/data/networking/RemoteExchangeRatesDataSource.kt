package com.barissemerci.currencyexchanger.exchanger.data.networking

import com.barissemerci.currencyexchanger.core.data.networking.safeCall
import com.barissemerci.currencyexchanger.core.domain.util.NetworkError
import com.barissemerci.currencyexchanger.core.domain.util.Result
import com.barissemerci.currencyexchanger.core.domain.util.map
import com.barissemerci.currencyexchanger.exchanger.data.networking.dto.ExchangeRatesDto
import com.barissemerci.currencyexchanger.exchanger.domain.ExchangeRates
import com.barissemerci.currencyexchanger.exchanger.domain.ExchangeRatesDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import com.barissemerci.currencyexchanger.BuildConfig
import com.barissemerci.currencyexchanger.exchanger.data.networking.mappers.toExchangeRates

class RemoteExchangeRatesDataSource(
    private val httpClient: HttpClient
) : ExchangeRatesDataSource {
    private val baseUrl = BuildConfig.BASE_URL

    override suspend fun getExchangeRates(): Result<ExchangeRates, NetworkError> {

        return safeCall<ExchangeRatesDto> {
            httpClient.get(
                urlString = baseUrl
            )
        }.map { response ->
            response.toExchangeRates()
        }
    }
}