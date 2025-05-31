package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Black
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.DarkGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Gray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Green
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White
import com.barissemerci.currencyexchanger.core.presentation.util.getDrawableIdForCurrency


@Composable
fun ConverterCard(
    buyAmount: String,
    sellAmount: String,
    onCurrencySelect: () -> Unit,
    selectedSellCurrency: String,
    selectedBuyCurrency: String,
    onSellAmountChange: (String) -> Unit,
    onDone: () -> Unit
) {
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
                CurrencyTextField(
                    currency = selectedSellCurrency,
                    amount = sellAmount,
                    onSellAmountChange = onSellAmountChange,
                    onDone = {
                        onDone()
                    },
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .background(
                            Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                )

                Row(
                    modifier = Modifier
                        .height(100.dp)
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
                        text = buyAmount,
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.titleLarge,
                        color = White
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onCurrencySelect)
                    ) {
                        Image(
                            painter = painterResource(id = getDrawableIdForCurrency(selectedBuyCurrency)),
                            contentDescription = selectedBuyCurrency,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = selectedBuyCurrency,
                            style = MaterialTheme.typography.titleSmall,
                            color = Green
                        )
                        Icon(
                            Icons.Filled.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.sell_currency),
                            tint = White
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
                    Icon(
                        painter = painterResource(R.drawable.ic_swap_vert),
                        contentDescription = stringResource(R.string.switch_currency),
                    )
                }
            }
        }
    }
}
