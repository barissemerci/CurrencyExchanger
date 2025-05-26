package com.barissemerci.currencyexchanger.core.presentation.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF459BD8)
val PrimaryGradientStart = Color(0xFF3B82C1)
val PrimaryGradientEnd = Color(0xFF3B99D8)

val SellRed = Color(0xFFDF5D58)
val ReceiveGreen = Color(0xFF499D72)

val ColorScheme.buttonGradient: Brush
    get() = Brush.linearGradient(
        colors = listOf(PrimaryGradientStart, PrimaryGradientEnd)
    )