package com.barissemerci.currencyexchanger.core.presentation.designsystem.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Black
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Green

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(
        enabled = enabled,
        onClick = { onClick()},
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = Black
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
            enabled = true,
        )
    }
}