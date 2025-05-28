package com.barissemerci.currencyexchanger.exchanger.domain

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRates(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)