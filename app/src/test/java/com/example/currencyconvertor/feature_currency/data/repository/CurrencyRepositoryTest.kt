package com.example.currencyconvertor.feature_currency.data.repository

import com.example.currencyconvertor.feature_currency.data.data_source.cache.FakeCurrencyCacheDataSourceImpl
import com.example.currencyconvertor.feature_currency.data.data_source.network.FakeCurrencyNetworkDataSourceImpl
import com.example.currencyconvertor.feature_currency.data.util.DataState
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.NETWORK_ERROR_UNKNOWN
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import com.example.currencyconvertor.feature_currency.util.MainDispatcherRule
import com.example.currencyconvertor.feature_currency.util.UnitTestRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyRepositoryTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @get:Rule
    val unitTestRule = UnitTestRule()

    private lateinit var currencyRepositoryImpl: CurrencyRepositoryImpl
    private lateinit var currencyCacheDataSource: FakeCurrencyCacheDataSourceImpl
    private lateinit var currencyNetworkDataSource: FakeCurrencyNetworkDataSourceImpl

    @Before
    fun setup() {
        currencyNetworkDataSource = FakeCurrencyNetworkDataSourceImpl()
        currencyCacheDataSource = FakeCurrencyCacheDataSourceImpl()
        currencyRepositoryImpl = CurrencyRepositoryImpl(
            currencyCacheDataSource, currencyNetworkDataSource
        )
    }

    @Test
    fun getCurrencies_emptyCache_success() = runTest {

        currencyCacheDataSource.insetCurrencies(emptyList())

        val result = currencyRepositoryImpl.getCurrencies().first()

        assertTrue(result is DataState.Success)
        assertTrue(currencyNetworkDataSource.getCurrencies().isNotEmpty())
        assertEquals(
            (result as DataState.Success).data.size, currencyNetworkDataSource.getCurrencies().size
        )
    }

    @Test
    fun getCurrencies_emptyCache_networkError() = runTest {

        currencyCacheDataSource.insetCurrencies(emptyList())
        currencyNetworkDataSource.setError()

        val result = currencyRepositoryImpl.getCurrencies().first()

        assertTrue(result is DataState.Error)
        assertEquals((result as DataState.Error).message, NETWORK_ERROR_UNKNOWN)
        assertTrue(currencyCacheDataSource.getCurrencies().isEmpty())
    }

    @Test
    fun getCurrencyRates_emptyCache_success() = runTest {

        currencyCacheDataSource.insertCurrencyRates(emptyList())

        val result = currencyRepositoryImpl.getCurrencyRates(
            amount = 80.0, currencyCode = "INR"
        ).first()

        assertTrue(result is DataState.Success)

        val inrRate = (result as DataState.Success).data.find { it.code == "INR" }?.rate

        assertEquals(inrRate, 80.0)

        val usdRate = result.data.find { it.code == "USD" }?.rate

        assertEquals(usdRate, 1.0)

    }

    @Test
    fun getCurrencyRates_emptyCache_networkError() = runTest {

        currencyCacheDataSource.insertCurrencyRates(emptyList())
        currencyNetworkDataSource.setError()

        val result = currencyRepositoryImpl.getCurrencyRates(
            amount = 80.0, currencyCode = "INR"
        ).first()

        assertTrue(result is DataState.Error)
        assertEquals((result as DataState.Error).message, NETWORK_ERROR_UNKNOWN)
        assertTrue(currencyCacheDataSource.getCurrencies().isEmpty())

    }

    @Test
    fun getCurrencies_nonEmptyCache_success() = runTest {

        currencyCacheDataSource.insetCurrencies(listOf(
            CurrencyModel(code = "USD", name ="Dollar")
        ))

        val result = currencyRepositoryImpl.getCurrencies().first()

        assertTrue(result is DataState.Success)

        assertEquals((result as DataState.Success).data.first().code, "USD")

    }

    @Test
    fun getCurrencyRates_nonEmptyCache_success() = runTest {

        currencyCacheDataSource.insertCurrencyRates(emptyList())

        val result = currencyRepositoryImpl.getCurrencyRates(
            amount = 80.0, currencyCode = "INR"
        ).first()

        assertTrue(result is DataState.Success)

        val inrRate = (result as DataState.Success).data.find { it.code == "INR" }?.rate

        assertEquals(inrRate, 80.0)

        val usdRate = result.data.find { it.code == "USD" }?.rate

        assertEquals(usdRate, 1.0)

    }

    @Test
    fun getCurrencyRates_cacheError() = runTest {

        currencyCacheDataSource.insertCurrencyRates(emptyList())
        currencyCacheDataSource.setError()

        val result = currencyRepositoryImpl.getCurrencyRates(
            amount = 80.0, currencyCode = "INR"
        ).first()

        assertTrue(result is DataState.Error)
        assertEquals((result as DataState.Error).message, NETWORK_ERROR_UNKNOWN)
    }

    @Test
    fun getCurrencies_cacheError() = runTest {

        currencyCacheDataSource.insertCurrencyRates(emptyList())
        currencyCacheDataSource.setError()

        val result = currencyRepositoryImpl.getCurrencies().first()

        assertTrue(result is DataState.Error)
        assertEquals((result as DataState.Error).message, NETWORK_ERROR_UNKNOWN)
    }
}