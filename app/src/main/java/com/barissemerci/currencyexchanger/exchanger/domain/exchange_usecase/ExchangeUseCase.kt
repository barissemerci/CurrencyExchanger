package com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase

import com.barissemerci.currencyexchanger.core.domain.util.ExchangeError
import com.barissemerci.currencyexchanger.core.domain.util.Result
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.commission_rule.CommissionRuleManager
import com.barissemerci.currencyexchanger.exchanger.domain.commission_rule.CommissionRuleType
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_count.ExchangeCountDataSource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.coroutines.cancellation.CancellationException

class ExchangeCurrencyUseCase(
    private val exchangeCountDataSource: ExchangeCountDataSource,
    private val availableBalanceDataSource: AvailableBalanceDataSource
) {
    private val mutex = Mutex()

    suspend operator fun invoke(
        exchangeRate: BigDecimal,
        fromCurrency: String,
        toCurrency: String,
        amount: BigDecimal,
        ruleType: CommissionRuleType
    ): Result<ConversionResult, ExchangeError> = mutex.withLock {
        try {
            val fromBalance =
                availableBalanceDataSource.getBalance(fromCurrency)?.currencyAmount
                    ?: BigDecimal.ZERO
            val toBalance =
                availableBalanceDataSource.getBalance(toCurrency)?.currencyAmount ?: BigDecimal.ZERO

            val ruleManager = CommissionRuleManager()
            val exchangeCount = exchangeCountDataSource.exchangeCount.first()

            val commission = ruleManager.calculateCommission(
                ruleType = ruleType,
                conversionCount = exchangeCount + 1,
                amount = amount
            ).setScale(2, RoundingMode.HALF_EVEN)

            val totalCost = amount.add(commission)

            if (fromBalance < totalCost) {
                return Result.Error(ExchangeError.NOT_ENOUGH_BALANCE)
            }

            val convertedAmount = amount.multiply(exchangeRate)

            withContext(NonCancellable) {
                availableBalanceDataSource.updateBalance(
                    fromCurrency,
                    fromBalance.subtract(totalCost)
                )
                availableBalanceDataSource.updateBalance(toCurrency, toBalance.add(convertedAmount))
                exchangeCountDataSource.incrementExchangeCount()
            }

            return Result.Success(
                ConversionResult(
                    fromCurrency = "EUR",
                    toCurrency = toCurrency,
                    sellAmount = amount,
                    buyAmount = convertedAmount,
                    rate = exchangeRate,
                    commissionFee = commission,
                    totalDeducted = totalCost
                )
            )
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            return Result.Error(ExchangeError.UNKNOWN)
        }

    }
}
