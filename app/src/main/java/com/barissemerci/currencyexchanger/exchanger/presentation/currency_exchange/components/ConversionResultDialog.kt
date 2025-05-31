package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionResultDialog(
    sellAmount: String,
    buyAmount: String,
    fromCurrency: String,
    toCurrency: String,
    commissionFee: BigDecimal,
    onDismiss: () -> Unit = {},
    ) {

    BasicAlertDialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text("Title", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(16.dp))
                Text("You converted $sellAmount $fromCurrency to $buyAmount ${toCurrency}. Comission Fee: $commissionFee ${fromCurrency} ")
                Spacer(Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = {
                        onDismiss()
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}