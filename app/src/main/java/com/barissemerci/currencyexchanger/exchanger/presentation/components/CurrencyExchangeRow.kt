package com.barissemerci.currencyexchanger.exchanger.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme

@Composable
fun CurrencyExchangeRow(
    icon: @Composable () -> Unit,
    title: String,
    amount: String,
    currency: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            icon.invoke()
            Text(title)
        }
        Row {
            Text(amount)
            Text(currency)
        }


        /*  DropDownOptionsMenu(
              modifier = Modifier.fillMaxWidth(0.5f),
              items = listOf(Selectable("EUR", false)),
              onDismiss = {},
              onItemClick = {}
          )*/
    }
}

@Preview
@Composable
private fun CurrencyExchangeRowPreview() {
    CurrencyExchangerTheme {
        CurrencyExchangeRow(
            icon = { Icon(Icons.Default.ArrowDropDown, contentDescription = "Sell") },
            title = "My Title",
            amount = "100",
            currency = "USD",
            modifier = Modifier
        )
    }
}