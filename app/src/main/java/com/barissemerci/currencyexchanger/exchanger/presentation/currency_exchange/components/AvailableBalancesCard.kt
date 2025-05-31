package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.DarkGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.Balance

@Composable
fun AvailableBalancesCard(
    balances: List<Balance>
) {
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
                text = stringResource(R.string.available_balance),
                style = MaterialTheme.typography.bodyLarge,
                color = White
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(balances) { balance ->
                    BalanceCardItem(
                        balance = balance,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}