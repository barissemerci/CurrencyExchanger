package com.barissemerci.currencyexchanger.exchanger.data.local.database

import androidx.room.TypeConverter
import java.math.BigDecimal

class BalanceConverters {
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal): String = value.toPlainString()

    @TypeConverter
    fun toBigDecimal(value: String): BigDecimal = BigDecimal(value)
}
