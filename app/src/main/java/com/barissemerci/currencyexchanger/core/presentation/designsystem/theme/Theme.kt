package com.barissemerci.currencyexchanger.core.presentation.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable


private val DarkColorScheme = darkColorScheme(
    primary = Green,
    secondary = Gray,
    tertiary = LightGray,
    background = Black,
    surface = DarkGray,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = White,
    onSurface = White,
)


@Composable
fun CurrencyExchangerTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}