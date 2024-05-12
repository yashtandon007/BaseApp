package com.example.currencyconvertor.feature_currency.presentation

import com.example.currencyconvertor.feature_currency.data.util.FakeCurrencyRepository
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.util.MainDispatcherRule
import com.example.currencyconvertor.feature_currency.util.UnitTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @get:Rule
    val unitTestRule = UnitTestRule()

    private val currencyRepository = FakeCurrencyRepository()
    private lateinit var currencyViewModel: CurrencyViewModel


    @Before
    fun setup() {
        currencyViewModel = CurrencyViewModel(currencyRepository)

    }

    @Test
    fun getCurrencies_success() = runTest {

        currencyViewModel.onEvent(CurrencyEvent.GetCurrencies)
        val currencies = currencyViewModel.state.currencies

        assertEquals("Dollar",currencies[0].name )
    }

    @Test
    fun selectCurrencyCode_success() = runTest {

        currencyViewModel.onEvent(CurrencyEvent.GetCurrencies)
        currencyViewModel.onEvent(
            CurrencyEvent.SelectCurrencyCode(
                CurrencyModel(
                    code = "INR", name = "Rupee"
                )
            )
        )
        val selectedCurrencyCode = currencyViewModel.state.selectedCurrency?.code

        assertEquals("INR", selectedCurrencyCode)
    }

    @Test
    fun amountChanged_success() = runTest {

        currencyViewModel.onEvent(CurrencyEvent.GetCurrencies)
        currencyViewModel.onEvent(CurrencyEvent.AmountChanged(amount = "10.0"))
       val amountSelected = currencyViewModel.state.amount

        assertEquals("10.0", amountSelected)
    }

    @Test
    fun currencyConvertedRates_success() = runTest {

        currencyViewModel.onEvent(CurrencyEvent.GetCurrencies)
        currencyViewModel.onEvent(CurrencyEvent.AmountChanged(amount = "10.0"))
        currencyViewModel.onEvent(
            CurrencyEvent.SelectCurrencyCode(
                CurrencyModel(
                    code = "INR", name = "Rupee"
                )
            )
        )
        val convertedRateInr = currencyViewModel.state.convertedRates.find { it.code == "INR" }?.rate

        assertEquals(80.0, convertedRateInr)
    }

}