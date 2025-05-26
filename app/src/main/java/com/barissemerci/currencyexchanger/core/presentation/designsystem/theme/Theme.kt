package com.barissemerci.currencyexchanger.core.presentation.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable



private val LightColorScheme = lightColorScheme(
    primary = Primary
)

@Composable
fun CurrencyExchangerTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}