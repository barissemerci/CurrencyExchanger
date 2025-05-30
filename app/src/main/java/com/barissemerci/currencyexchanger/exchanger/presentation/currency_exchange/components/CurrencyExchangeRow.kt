package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.DropDownOptionsMenu
import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.Selectable
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme

@Composable
fun CurrencyExchangeRow(
    icon: @Composable () -> Unit,
    title: String,
    currencyList: List<Selectable>,
    modifier: Modifier = Modifier,
    amount: String = "",
    isExpanded: Boolean = false,
    showDropDown: () -> Unit = {},
    dismissDropDown: () -> Unit = {},
    selectedCurrency: String,
    onChangeAmount: (String) -> Unit = {},
    onClickCurrency: (Int) -> Unit = {},
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    onChangeAmount(it)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.width(100.dp),
            )
            DropDownOptionsMenu(
                items = currencyList,
                isExpanded = isExpanded,
                selectedItem = selectedCurrency,
                onDismiss = {
                    dismissDropDown()
                },
                onClick = {
                    showDropDown()
                },
                modifier = Modifier,
                onItemClick = {
                    onClickCurrency(it)
                }
            )
        }


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
            //  currency = "USD",
            onClickCurrency = {},
            modifier = Modifier,
            currencyList = listOf(Selectable("USD", false), Selectable("EUR", false)),
            isExpanded = false,
            showDropDown = {},
            selectedCurrency = "USD",
            dismissDropDown = {}
        )
    }
}