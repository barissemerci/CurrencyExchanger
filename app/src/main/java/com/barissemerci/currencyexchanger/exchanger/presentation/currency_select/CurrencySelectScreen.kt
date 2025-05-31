package com.barissemerci.currencyexchanger.exchanger.presentation.currency_select

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.presentation.designsystem.dropdowns.Selectable
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Black
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.DarkGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Gray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.Green
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.LightGray
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.White
import com.barissemerci.currencyexchanger.core.presentation.util.getDrawableIdForCurrency

@Composable

fun CurrencySelectScreenRoot(
    onCurrencySelected: (Int) -> Unit,
    onBackClick: () -> Unit,
    currencies: List<Selectable>

) {

    CurrencySelectScreen(
        onCurrencySelected = onCurrencySelected,
        onBackClick = onBackClick,
        currencies = currencies,
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencySelectScreen(
    onCurrencySelected: (Int) -> Unit,
    onBackClick: () -> Unit,
    currencies: List<Selectable>,
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredCurrencies = remember(searchQuery) {
        currencies.filter { currency ->
            currency.currency.contains(searchQuery, ignoreCase = true)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Currency") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {


                // Search Bar
                SearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it }
                )

                // Currency List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredCurrencies) { currency ->
                        SelectCurrencyItem(
                            onCurrencySelected = onCurrencySelected,
                            currency = currency,
                            currencies = currencies,
                        )
                    }
                }
            }
        }
    }


}

@Composable
private fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(Gray, RoundedCornerShape(8.dp)),
        placeholder = {
            Text(
                text = stringResource(R.string.search_for_a_currency),
                color = LightGray
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(R.string.search),
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

}

@Composable
private fun SelectCurrencyItem(
    onCurrencySelected: (Int) -> Unit,
    currency: Selectable,
    currencies: List<Selectable>
) {
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
                    painter = painterResource(id = getDrawableIdForCurrency(currency.currency)),
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
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.selected),
                    tint = Green,
                    modifier = Modifier.size(24.dp)
                )
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

        )

    }

}