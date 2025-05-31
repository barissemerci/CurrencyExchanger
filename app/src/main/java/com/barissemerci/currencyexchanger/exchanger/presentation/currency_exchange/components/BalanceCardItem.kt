package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Black
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White
import com.barissemerci.currencyexchanger.core.presentation.util.getDrawableIdForCurrency
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.Balance
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.utils.formatAmount

@Composable
fun BalanceCardItem(
    balance : Balance,
    modifier:  Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Black,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = getDrawableIdForCurrency(balance.currencyType)),
                    contentDescription = balance.currencyType,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = balance.currencyType,
                    style = MaterialTheme.typography.bodyLarge,
                    color = White
                )
            }
            Text(
                text = balance.currencyAmount.formatAmount(),
                style = MaterialTheme.typography.titleMedium,
                color = White
            )
        }
    }
}