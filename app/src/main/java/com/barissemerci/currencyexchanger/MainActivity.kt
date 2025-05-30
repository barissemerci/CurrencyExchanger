package com.barissemerci.currencyexchanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.ExchangerScreenRoot
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.ExchangerViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyExchangerTheme {
                val viewModel = koinViewModel<ExchangerViewModel>()
                ExchangerScreenRoot(
                    viewModel = viewModel
                )

            }
        }
    }
}

