package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.DarkGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Green
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.utils.formatAmount
import java.math.BigDecimal

@Composable
fun ConversionResultDialog(
    sellAmount: String,
    buyAmount: String,
    fromCurrency: String,
    toCurrency: String,
    commissionFee: BigDecimal,
    exchangeRate: BigDecimal,
    totalDeducted: BigDecimal,
    onDismiss: () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(width = 1.dp, color = Green.copy(alpha = 0.2f)),
            color = DarkGray,
            modifier = Modifier.width(300.dp)
        ) {
            Column(
                modifier = Modifier.padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Transaction Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                DetailRow(
                    label = "Converted Amount",
                    value = "$sellAmount $fromCurrency",
                    isHighlighted = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailRow(
                    label = "Received Amount",
                    value = "$buyAmount $toCurrency",
                    isHighlighted = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailRow(
                    label = "Exchange Rate",
                    value = "1 %s = %s %s".format(
                        fromCurrency,
                        exchangeRate.formatAmount(4),
                        toCurrency
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailRow(
                    label = "Commission Fee",
                    value = commissionFee.formatAmount() + " " + fromCurrency,
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(
                    color = Color(0xFF2A3F2E),
                    thickness = 2.dp
                )

                Spacer(modifier = Modifier.height(9.dp))

                DetailRow(
                    label = "Total Deducted",
                    value = "%.2f %s".format(totalDeducted, fromCurrency),
                    isHighlighted = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CD964)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "OK",
                            color = Color(0xFF1E2F23),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    isHighlighted: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFD1D5DB)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
            color = Color.White
        )
    }
}