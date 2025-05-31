package com.barissemerci.currencyexchanger.exchanger.presentation.currency_select

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Gray
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.Selectable
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Black
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.DarkGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Green
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.LightGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White

@Composable

fun CurrencySelectScreenRoot(
    onCurrencySelected: (Int) -> Unit,
    onBackClick: () -> Unit,
    currencies: List<Selectable>
    //  viewModel:  = koinViewModel()

) {

    CurrencySelectScreen(
        onCurrencySelected = onCurrencySelected,
        onBackClick = onBackClick,
        currencies = currencies,

        //   state = viewModel.state,

        //    onAction = viewModel::onAction

    )

}


@Composable
private fun CurrencySelectScreen(
    onCurrencySelected: (Int) -> Unit,
    onBackClick: () -> Unit,
    currencies: List<Selectable>,
    // state: ,
    //  onAction: () -> Unit

) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredCurrencies = remember(searchQuery) {
        currencies.filter { currency ->
            currency.currency.contains(searchQuery, ignoreCase = true)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 64.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Gray, CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_android_black_24dp),
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Choose a currency",
                    style = MaterialTheme.typography.headlineLarge,
                    color = White
                )

                // Spacer for alignment
                Spacer(modifier = Modifier.size(48.dp))
            }

            // Search Bar
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(Gray, RoundedCornerShape(8.dp)),
                placeholder = {
                    Text(
                        text = "Search for a currency",
                        color = LightGray
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_android_black_24dp),
                        contentDescription = "Search",
                        tint = LightGray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Gray,
                    unfocusedContainerColor = Gray,
                    disabledContainerColor = Gray,
                    focusedIndicatorColor = Gray,
                    unfocusedIndicatorColor = Gray,
                ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = White)
            )

            // Currency List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredCurrencies) { currency ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCurrencySelected(currencies.indexOf(currency)) }
                            .then(
                                if (currency.isSelected) {
                                    Modifier.border(
                                        width = 1.dp,
                                        color = Green,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                } else {
                                    Modifier
                                }
                            ),
                        color = if (currency.isSelected) DarkGray else Gray,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_android_black_24dp),
                                    contentDescription = currency.currency,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                )

                                Column(
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                ) {
                                    Text(
                                        text = currency.currency,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = White
                                    )

                                }
                            }

                            if (currency.isSelected) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_android_black_24dp),
                                    contentDescription = "Selected",
                                    tint = Green,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview

@Composable

private fun CurrencySelectScreenPreview() {

    CurrencyExchangerTheme {

        CurrencySelectScreen(
            onCurrencySelected = { },
            onBackClick = { },
            currencies = listOf(
                Selectable(currency = "USD", isSelected = true),
                Selectable(currency = "EUR", isSelected = false),
                Selectable(currency = "GBP", isSelected = false),
            ),

            //     state = (),

            //    onAction = {}

        )

    }

}