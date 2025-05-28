package com.barissemerci.currencyexchanger.exchanger.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesDto(
    val base: String = "",
    val date: String = "",
    val rates: Map<String, Double> = emptyMap()
)
