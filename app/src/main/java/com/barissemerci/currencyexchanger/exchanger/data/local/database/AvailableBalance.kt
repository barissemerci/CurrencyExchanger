package com.barissemerci.currencyexchanger.exchanger.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "available_balances")
data class AvailableBalance(
    @PrimaryKey val currencyCode: String,
    val amount: BigDecimal
)