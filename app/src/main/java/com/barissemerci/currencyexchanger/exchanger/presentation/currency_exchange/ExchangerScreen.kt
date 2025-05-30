package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barissemerci.currencyexchanger.core.presentation.designsystem.buttons.PrimaryButton
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.core.presentation.util.ObserveAsEvents
import com.barissemerci.currencyexchanger.exchanger.presentation.components.CurrencyExchangeRow
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.utils.formatAmount
import org.koin.androidx.compose.koinViewModel

@Composable

fun ExchangerScreenRoot(
    viewModel: ExchangerViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is ExchangerEvent.ShowTransactionError -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }

            ExchangerEvent.ShowTransactionInfo -> {

            }
        }
    }
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


        if (state.showConversionResultDialog) {
            BasicAlertDialog(
                onDismissRequest = { }
            ) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text("Title", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(16.dp))
                        Text("You converted ${state.conversionResult?.sellAmount} ${state.conversionResult?.fromCurrency} to ${state.conversionResult?.buyAmount} ${state.conversionResult?.toCurrency}. Comission Fee: ${state.conversionResult?.commissionFee} ${state.conversionResult?.fromCurrency} ")
                        Spacer(Modifier.height(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            TextButton(onClick = {
                                onAction(ExchangerAction.OnDismissConversionResultDialog)
                            }) {
                                Text("OK")
                            }
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),

            ) {
            Text("My Balances")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                state.availableBalances.forEach { item ->
                    Text(item.currencyType + " " + item.currencyAmount.formatAmount())
                }
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
                isExpanded = state.showBuyCurrencyList,
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
            Button(
                onClick = {
                    onAction(ExchangerAction.Load1000EuroToWallet)
                }
            ) {
                Text("Load 1000 EUR to Wallet")
            }
            Button(
                onClick = {
                    onAction(ExchangerAction.IncreaseFreeExchangeCount)
                }
            ) {
                Text("Update Free Conversion Count to 5")
            }
            Text("Remaining Free Conversion Number ${state.remainingFreeConversions}")
            PrimaryButton(
                text = "SUBMIT",
                onClick = {
                    onAction(ExchangerAction.OnSubmit)
                },
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