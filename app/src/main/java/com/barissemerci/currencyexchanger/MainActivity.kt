package com.barissemerci.currencyexchanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.barissemerci.currencyexchanger.core.presentation.designsystem.theme.CurrencyExchangerTheme
import com.barissemerci.currencyexchanger.core.presentation.util.Route
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.ExchangerAction
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.ExchangerScreenRoot
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_exchange.ExchangerViewModel
import com.barissemerci.currencyexchanger.exchanger.presentation.currency_select.CurrencySelectScreenRoot
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyExchangerTheme {
                val viewModel = koinViewModel<ExchangerViewModel>()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.ExchangerScreen
                ) {
                    composable<Route.ExchangerScreen>(
                        exitTransition = {
                            slideOutHorizontally()
                        },
                        popEnterTransition = {
                            slideInHorizontally()
                        }
                    ) {
                        ExchangerScreenRoot(
                            viewModel = viewModel,
                            onNavigateToCurrencyList = { isSellCurrency ->
                                navController.navigate(Route.CurrencySelectScreen(isSellCurrency))
                            }
                        )
                    }
                    composable<Route.CurrencySelectScreen>(
                        enterTransition = {
                            slideInHorizontally { initialOffset ->
                                initialOffset
                            }
                        },
                        exitTransition = {
                            slideOutHorizontally { initialOffset ->
                                initialOffset
                            }
                        }
                    ) {
                        val args = it.toRoute<Route.CurrencySelectScreen>()

                        CurrencySelectScreenRoot(
                            onCurrencySelected = { currency ->
                                if (args.isSellCurrency) {
                                    viewModel.onAction(ExchangerAction.OnChangeSellCurrency(currency))

                                } else {
                                    viewModel.onAction(ExchangerAction.OnChangeBuyCurrency(currency))
                                }
                                navController.popBackStack()
                            },
                            currencies = if (args.isSellCurrency) viewModel.state.value.exchangeSellCurrencyList else viewModel.state.value.exchangeBuyCurrencyList,
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

