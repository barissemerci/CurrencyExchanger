package com.barissemerci.currencyexchanger.exchanger.presentation.mappers

import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.Selectable
import java.math.BigDecimal

fun Map<String, BigDecimal>.toSelectableList(): List<Selectable> {

    return this.map {
        Selectable(
            currency = it.key,
            isSelected = false
        )
    }

}