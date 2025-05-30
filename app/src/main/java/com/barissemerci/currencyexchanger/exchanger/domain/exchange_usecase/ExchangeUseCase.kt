package com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase

import com.barissemerci.currencyexchanger.core.domain.util.ExchangeError
import com.barissemerci.currencyexchanger.core.domain.util.Result
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_count.ExchangeCountDataSource
import kotlinx.coroutines.flow.first
import java.math.BigDecimal
import java.math.RoundingMode

class ConvertCurrencyUseCase(
    private val exchangeCountDataSource: ExchangeCountDataSource,
    private val availableBalanceDataSource: AvailableBalanceDataSource
) {


    suspend operator fun invoke(
        exchangeRate: BigDecimal,
        fromCurrency: String,
        toCurrency: String,
        amount: BigDecimal,

    ): Result<ConversionResult, ExchangeError> {
        val commissionRate = BigDecimal("0.007")

        val fromBalance =
            availableBalanceDataSource.getBalance(fromCurrency)?.currencyAmount ?: BigDecimal.ZERO
        val toBalance = availableBalanceDataSource.getBalance(toCurrency)?.currencyAmount ?: BigDecimal.ZERO

        val remainingConversions = exchangeCountDataSource.remainingFreeConversions.first()

        val commission = if (remainingConversions > 0) {
            BigDecimal.ZERO
        } else {
            amount.multiply(commissionRate).setScale(2, RoundingMode.HALF_EVEN)
        }
        val totalCost = amount.add(commission)

        if (fromBalance < totalCost) {
            return Result.Error(ExchangeError.NOT_ENOUGH_BALANCE)
        }


        val convertedAmount = amount.multiply(exchangeRate)

        availableBalanceDataSource.updateBalance(fromCurrency, fromBalance.subtract(totalCost))
        availableBalanceDataSource.updateBalance(toCurrency, toBalance.add(convertedAmount))

        if (remainingConversions > 0) {
            exchangeCountDataSource.decrementFreeConversion()
        }
        return Result.Success(
            ConversionResult(
                fromCurrency = "EUR",
                toCurrency = toCurrency,
                sellAmount = amount,
                buyAmount = convertedAmount,
                rate = exchangeRate,
                commissionFee = commission
            )
        )
    }
}
