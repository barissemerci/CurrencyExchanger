package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Gray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.LighterGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White

@Composable
fun InfoCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent,
        shape = RoundedCornerShape(16.dp),
        border = ButtonDefaults.outlinedButtonBorder(enabled = true)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Includes fees",
                    style = MaterialTheme.typography.bodyLarge,
                    color = LighterGray
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_android_black_24dp),
                        contentDescription = "Info",
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "6.22 USD",
                        style = MaterialTheme.typography.bodyLarge,
                        color = White
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Gray
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Should arrive",
                    style = MaterialTheme.typography.bodyLarge,
                    color = LighterGray
                )
                Text(
                    text = "In seconds",
                    style = MaterialTheme.typography.bodyLarge,
                    color = White
                )
            }
        }
    }
}