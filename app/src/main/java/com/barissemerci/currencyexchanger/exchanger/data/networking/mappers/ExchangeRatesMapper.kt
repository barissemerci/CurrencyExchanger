package com.barissemerci.currencyexchanger.exchanger.data.networking.mappers

import com.barissemerci.currencyexchanger.exchanger.data.networking.dto.ExchangeRatesDto
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_rates.ExchangeRates

fun ExchangeRatesDto.toExchangeRates(): ExchangeRates {
    return ExchangeRates(
        base = base,
        date = date,
        rates = this.rates.mapValues { (_, value) -> value.toBigDecimal() }
    )
}

