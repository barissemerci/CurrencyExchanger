package com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barissemerci.currencyexchanger.R
import com.barissemerci.currencyexchanger.core.presentation.designsystem.buttons.PrimaryButton
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.core.presentation.util.ObserveAsEvents
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components.AvailableBalancesCard
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components.ConversionResultDialog
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.components.ConverterCard
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.utils.formatAmount
import org.koin.androidx.compose.koinViewModel
import java.math.BigDecimal

@Composable

fun ExchangerScreenRoot(
    viewModel: ExchangerViewModel = koinViewModel(),
    onNavigateToCurrencyList: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is ExchangerEvent.ShowTransactionError -> {
                Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
            }

            is ExchangerEvent.ShowFetchingCurrencyError -> {
                Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }
    ExchangerScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ExchangerAction.OnClickChangeBuyCurrency -> {
                    onNavigateToCurrencyList()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

private fun ExchangerScreen(
    state: ExchangerState,
    onAction: (ExchangerAction) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.currency_exchanger)) }
            )
        }

    ) { innerPadding ->

        if (state.showConversionResultDialog) {
            ConversionResultDialog(
                onDismiss = {
                    onAction(ExchangerAction.OnDismissConversionResultDialog)
                },
                sellAmount = state.conversionResult?.sellAmount?.formatAmount() ?: "",
                buyAmount = state.conversionResult?.buyAmount?.formatAmount() ?: "",
                fromCurrency = state.conversionResult?.fromCurrency ?: "",
                toCurrency = state.conversionResult?.toCurrency ?: "",
                commissionFee = state.conversionResult?.commissionFee ?: BigDecimal.ZERO,
                totalDeducted = state.conversionResult?.totalDeducted ?: BigDecimal.ZERO,
                exchangeRate = state.conversionResult?.rate ?: BigDecimal.ZERO
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                }
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            AvailableBalancesCard(
                balances = state.availableBalances
            )

            ConverterCard(
                selectedBuyCurrency = state.selectedBuyCurrency,
                selectedSellCurrency = state.selectedSellCurrency,
                onCurrencySelect = {
                    onAction(ExchangerAction.OnClickChangeBuyCurrency)
                },
                sellAmount = state.sellAmountText,
                onSellAmountChange = {
                    onAction(ExchangerAction.OnChangeSellAmount(it))
                },
                buyAmount = state.buyAmount,
                onDone = {
                    onAction(ExchangerAction.OnSubmit)
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(
                text = stringResource(R.string.submit),
                onClick = { onAction(ExchangerAction.OnSubmit) },
                modifier = Modifier.padding(horizontal = 20.dp),
                enabled = state.isSubmitButtonEnabled
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