package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Green
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White
import com.barissemerci.currencyexchanger.core.presentation.util.getDrawableIdForCurrency

@Composable
fun CurrencyTextField(
    currency: String,
    amount: String,
    onSellAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDone: () -> Unit
) {

    OutlinedTextField(
        value = amount,
        onValueChange = onSellAmountChange,
        textStyle = TextStyle(fontSize = 24.sp),
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Green,
            unfocusedBorderColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number

        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        placeholder = { Text("0", fontSize = 24.sp, color = Color.Gray) },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Filled.KeyboardArrowDown,
                contentDescription = stringResource(id = R.string.sell_currency),
                tint = White
            )
        },
        suffix = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = getDrawableIdForCurrency(currency)),
                    contentDescription = currency,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = currency,
                    style = MaterialTheme.typography.titleSmall,
                    color = Green
                )
            }

        }
    )
}