package com.barissemerci.currencyexchanger.exchanger.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barissemerci.currencyexchanger.core.presentation.designsystem.buttons.PrimaryButton
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.exchanger.presentation.components.CurrencyExchangeRow
import org.koin.androidx.compose.koinViewModel

@Composable

fun ExchangerScreenRoot(
    viewModel: ExchangerViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ExchangerScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

private fun ExchangerScreen(
    state: ExchangerState,
    onAction: (ExchangerAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Currency Exchanger") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            ) {
            Text("My Balances")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("USD: 100")
                Text("EUR: 100")
            }
            Text("Exchange Rates")


            CurrencyExchangeRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                },
                onChangeAmount = { amount ->
                    onAction(
                        ExchangerAction.OnChangeSellAmount(amount)
                    )
                },
                title = "Sell",
                amount = state.sellAmountText,
                isExpanded = state.showSellCurrencyList,

                selectedCurrency = state.selectedSellCurrency,
                currencyList = state.exchangeCurrencyList,

            )

            HorizontalDivider()

            CurrencyExchangeRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
                },

                currencyList = state.exchangeCurrencyList,
                onClickCurrency = {
                    onAction(
                        ExchangerAction.OnChangeBuyCurrency(it)
                    )
                },
                showDropDown = {
                    onAction(
                        ExchangerAction.OnClickChangeBuyCurrency
                    )
                },
                dismissDropDown = {
                    onAction(
                        ExchangerAction.OnDismissBuyCurrencyList
                    )
                },
                title = "Receive",
                amount = state.buyAmount,
                selectedCurrency = state.selectedBuyCurrency
            )
            Spacer(Modifier.weight(1f))
            PrimaryButton(
                text = "SUBMIT",
                onClick = { },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }


    }


}

@Preview
@Composable
private fun ExchangerScreenPreview() {
    CurrencyExchangerTheme {
        ExchangerScreen(
            state = ExchangerState(

            ),
            onAction = {}
        )
    }
}