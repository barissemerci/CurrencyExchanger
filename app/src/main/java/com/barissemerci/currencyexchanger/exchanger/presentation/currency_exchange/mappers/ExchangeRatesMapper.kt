package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.mappers

import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.Selectable
import java.math.BigDecimal

fun Map<String, BigDecimal>.toSelectableList(
    selectedCurrency: String
): List<Selectable> {
    return this.map {
        Selectable(
            currency = it.key,
            isSelected = it.key == selectedCurrency
        )
    }
}