package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Black
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.DarkGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.LightGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White

@Composable
fun BalanceCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DarkGray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Available Balance",
                style = MaterialTheme.typography.bodyLarge,
                color = White
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(3) { index ->
                    val (currency, amount, icon) = when (index) {
                        0 -> Triple("USD", "2.450", R.drawable.ic_android_black_24dp)
                        1 -> Triple("EUR", "1.890", R.drawable.ic_android_black_24dp)
                        else -> Triple("BGN", "3.720", R.drawable.ic_android_black_24dp)
                    }

                    Surface(
                        modifier = Modifier.weight(1f),
                        color = Black,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = icon),
                                    contentDescription = currency,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = currency,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = White
                                )
                            }
                            Text(
                                text = amount,
                                style = MaterialTheme.typography.titleMedium,
                                color = White
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_android_black_24dp),
                    contentDescription = "Update",
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "Last updated: 2 min ago",
                    style = MaterialTheme.typography.bodyLarge,
                    color = LightGray
                )
            }
        }
    }
}