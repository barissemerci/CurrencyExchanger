package com.barissemerci.currencyexchanger.exchanger.domain.exchange_usecase

import com.barissemerci.currencyexchanger.core.domain.util.ExchangeError
import com.barissemerci.currencyexchanger.core.domain.util.Result
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.AvailableBalanceDataSource
import com.barissemerci.currencyexchanger.exchanger.domain.available_balance.Balance
import com.barissemerci.currencyexchanger.exchanger.domain.commission_rule.CommissionRuleType
import com.barissemerci.currencyexchanger.exchanger.domain.exchange_count.ExchangeCountDataSource
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ExchangeCurrencyUseCaseTest {

 private val exchangeCountDataSource: ExchangeCountDataSource = mockk()
 private val availableBalanceDataSource: AvailableBalanceDataSource = mockk()

 private lateinit var exchangeCurrencyUseCase: ExchangeCurrencyUseCase

 @Before
 fun setUp() {
  clearAllMocks()
  exchangeCurrencyUseCase = ExchangeCurrencyUseCase(
   exchangeCountDataSource = exchangeCountDataSource,
   availableBalanceDataSource = availableBalanceDataSource
  )
 }

 @Test
 fun `successful exchange with sufficient balance and first 5 commissions free`() = runTest {
  // Given
  val fromCurrency = "EUR"
  val toCurrency = "USD"
  val amount = BigDecimal("100.00")
  val exchangeRate = BigDecimal("1.20")
  val ruleType = CommissionRuleType.FIRST_5_FREE
  val fromBalance = BigDecimal("500.00")
  val toBalance = BigDecimal("200.00")
  val exchangeCount = 1

  coEvery { availableBalanceDataSource.getBalance(fromCurrency) } returns
          Balance(fromCurrency, fromBalance)
  coEvery { availableBalanceDataSource.getBalance(toCurrency) } returns
          Balance(toCurrency, toBalance)
  every { exchangeCountDataSource.exchangeCount } returns flowOf(exchangeCount)

  coEvery { availableBalanceDataSource.updateBalance(any(), any()) } just Runs
  coEvery { exchangeCountDataSource.incrementExchangeCount() } just Runs
  // When
  val result = exchangeCurrencyUseCase(
   exchangeRate = exchangeRate,
   fromCurrency = fromCurrency,
   toCurrency = toCurrency,
   amount = amount,
   ruleType = ruleType
  )

  // Then
  assertTrue(result is Result.Success)
  val conversionResult = (result as Result.Success).data
  assertEquals(fromCurrency, conversionResult.fromCurrency)
  assertEquals(toCurrency, conversionResult.toCurrency)
  assertEquals(amount, conversionResult.sellAmount)
  assertEquals(0, BigDecimal("120.00").compareTo(conversionResult.buyAmount))
  assertEquals(exchangeRate, conversionResult.rate)
  assertEquals(BigDecimal("0.00"), conversionResult.commissionFee)
  assertEquals(amount, conversionResult.totalDeducted)
 }

 @Test
 fun `successful exchange with commission`() = runTest {
  // Given
  val fromCurrency = "EUR"
  val toCurrency = "USD"
  val amount = BigDecimal("100.00")
  val exchangeRate = BigDecimal("1.20")
  val ruleType = CommissionRuleType.FIRST_5_FREE // Assume this adds commission
  val fromBalance = BigDecimal("500.00")
  val toBalance = BigDecimal("200.00")
  val exchangeCount = 10 // Higher count to trigger commission

  coEvery { availableBalanceDataSource.getBalance(fromCurrency) } returns
          Balance(fromCurrency, fromBalance)
  coEvery { availableBalanceDataSource.getBalance(toCurrency) } returns
          Balance(toCurrency, toBalance)
  every { exchangeCountDataSource.exchangeCount } returns flowOf(exchangeCount)

  coEvery { availableBalanceDataSource.updateBalance(any(), any()) } just Runs
  coEvery { exchangeCountDataSource.incrementExchangeCount() } just Runs

  // When
  val result = exchangeCurrencyUseCase(
   exchangeRate = exchangeRate,
   fromCurrency = fromCurrency,
   toCurrency = toCurrency,
   amount = amount,
   ruleType = ruleType
  )

  // Then
  assertTrue(result is Result.Success)
  val conversionResult = (result as Result.Success).data
  assertTrue(conversionResult.commissionFee > BigDecimal.ZERO)
  assertTrue(conversionResult.totalDeducted > amount)
 }

 @Test
 fun `exchange fails when insufficient balance`() = runTest {
  // Given
  val fromCurrency = "EUR"
  val toCurrency = "USD"
  val amount = BigDecimal("100.00")
  val exchangeRate = BigDecimal("1.20")
  val ruleType = CommissionRuleType.FIRST_5_FREE
  val fromBalance = BigDecimal("50.00") // Less than required amount
  val toBalance = BigDecimal("200.00")
  val exchangeCount = 0

  coEvery { availableBalanceDataSource.getBalance(fromCurrency) } returns
          Balance(fromCurrency, fromBalance)
  coEvery { availableBalanceDataSource.getBalance(toCurrency) } returns
          Balance(toCurrency, toBalance)
  every { exchangeCountDataSource.exchangeCount } returns flowOf(exchangeCount)

  // When
  val result = exchangeCurrencyUseCase(
   exchangeRate = exchangeRate,
   fromCurrency = fromCurrency,
   toCurrency = toCurrency,
   amount = amount,
   ruleType = ruleType
  )

  // Then
  assertTrue(result is Result.Error)
  assertEquals(ExchangeError.NOT_ENOUGH_BALANCE, (result as Result.Error).error)

  // Verify no balance updates occurred
  coVerify(exactly = 0) { availableBalanceDataSource.updateBalance(any(), any()) }
  coVerify(exactly = 0) { exchangeCountDataSource.incrementExchangeCount() }
 }

}