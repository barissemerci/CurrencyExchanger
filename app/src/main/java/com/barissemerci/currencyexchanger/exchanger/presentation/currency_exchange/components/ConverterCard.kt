package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Black
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.DarkGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Gray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Green
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White


@Composable
fun ConverterCard(onCurrencySelect: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkGray,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {
                // From Currency Input
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .background(
                            Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "500",
                        style = MaterialTheme.typography.titleLarge,
                        color = White
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "USD",
                            style = MaterialTheme.typography.titleSmall,
                            color = Green
                        )
                    }
                }

                // To Currency Input
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .background(
                            Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "2799.83",
                        style = MaterialTheme.typography.titleLarge,
                        color = White
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onCurrencySelect)
                    ) {
                        Text(
                            text = "BRL",
                            style = MaterialTheme.typography.titleSmall,
                            color = Green
                        )
                    }
                }
            }
        }

        // Switch Button - Positioned to overlap both rows
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 0.dp)
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                color = Gray,
                shape = CircleShape
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Switch icon placeholder
                }
            }
        }
    }
}
