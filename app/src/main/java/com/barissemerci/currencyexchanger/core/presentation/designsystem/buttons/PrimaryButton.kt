package com.barissemerci.currencyexchanger.core.presentation.designsystem.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.buttonGradient

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.background(
            brush = if (enabled) {
                MaterialTheme.colorScheme.buttonGradient
            } else {
                SolidColor(
                    Color.Gray
                )
            },
            shape = CircleShape,
        )
    ) {
        Text(
            text = text
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    CurrencyExchangerTheme {
        PrimaryButton(
            text = "Primary Button",
            onClick = {},
        )
    }
}