package com.barissemerci.currencyexchanger.exchanger.data.local.mappers

import com.barissemerci.currencyexchanger.exchanger.data.local.database.AvailableBalance

fun AvailableBalance.toAvailableBalance(): com.barissemerci.currencyexchanger.exchanger.domain.available_balance.Balance {
    return com.barissemerci.currencyexchanger.exchanger.domain.available_balance.Balance(
        currencyType = currencyCode,
        currencyAmount = amount
    )
}