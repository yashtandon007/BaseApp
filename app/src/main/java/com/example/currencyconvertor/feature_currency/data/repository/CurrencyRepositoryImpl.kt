package com.example.currencyconvertor.feature_currency.data.repository

import com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction.CurrencyCacheDataSource
import com.example.currencyconvertor.feature_currency.data.data_source.network.NetworkResult
import com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction.CurrencyNetworkDataSource
import com.example.currencyconvertor.feature_currency.data.util.DataState
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.ERROR_UNKNOWN
import com.example.currencyconvertor.feature_currency.data.util.safeApiCall
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import com.example.currencyconvertor.feature_currency.domain.repository.CurrencyRepository
import com.example.currencyconvertor.feature_currency.util.printLogD
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of the CurrencyRepository interface.
 *
 * @param currencyCacheDataSource The data source for cached currencies.
 * @param currencyNetworkDataSource The data source for network currencies.
 */
class CurrencyRepositoryImpl @Inject constructor(
    private val currencyCacheDataSource: CurrencyCacheDataSource,
    private val currencyNetworkDataSource: CurrencyNetworkDataSource
) : CurrencyRepository {

    /**
     *
     * Retrieves a list of currencies.
     *
     * @return A flow that emits the list of currencies.
     *
     */
    override fun getCurrencies() = flow {
        printLogD("repository", "loading currencies...")

        val cachedCurrencies = currencyCacheDataSource.getCurrencies()

        if (cachedCurrencies.isNotEmpty()) {
            emit(DataState.Success(cachedCurrencies))
            return@flow
        }

        val networkResult = safeApiCall {
            currencyNetworkDataSource.getCurrencies()
        }
        when (networkResult) {
            is NetworkResult.Success -> {
                currencyCacheDataSource.insetCurrencies(networkResult.data.sortedBy { it.name })
                emit(DataState.Success(currencyCacheDataSource.getCurrencies()))
            }

            is NetworkResult.Error -> {
                printLogD("repository", "Network error: ${networkResult.errorMessage}")
                emit(DataState.Error(networkResult.errorMessage))
            }
        }
    }.catch {
        printLogD("repository", "Error: ${it.message}")
        emit(DataState.Error(it.message ?: ERROR_UNKNOWN))
    }

    /**
     * Retrieves currency rates for a given amount and currency code.
     * @param amount The amount to convert.
     * @param currencyCode The currency code to convert to.
     *
     * @return A flow that emits the converted currency rates.
     */
    override fun getCurrencyRates(
        amount: Double, currencyCode: String
    ) = flow {

        printLogD("repository", "loading currency rates...")
        val cachedRates = currencyCacheDataSource.getCurrencyRates()

        if (cachedRates.isNotEmpty()) {
            val convertedRates = convertCurrencyRates(amount, currencyCode, cachedRates)
            emit(DataState.Success(convertedRates))
            return@flow
        }

        val networkResult = safeApiCall {
            currencyNetworkDataSource.getCurrencyRates()
        }
        when (networkResult) {
            is NetworkResult.Success -> {
                currencyCacheDataSource.insertCurrencyRates(networkResult.data)
                val convertedRates = convertCurrencyRates(
                    amount, currencyCode, currencyCacheDataSource.getCurrencyRates()
                )
                emit(DataState.Success(convertedRates))
            }

            is NetworkResult.Error -> {
                emit(DataState.Error(networkResult.errorMessage))
            }
        }

    }.catch {
        printLogD("repository", "Error: ${it.message}")
        emit(DataState.Error(it.message ?: ERROR_UNKNOWN))
    }

    /**
     * Retrieves a list of converted currency rates for a given amount and currency code.
     *
     * @param amount The amount to convert.
     * @param currencyCode The currency code to convert to.
     *
     * @return A list of converted currency rates.
     */
    private fun convertCurrencyRates(
        amount: Double, currencyCode: String, currencyRates: List<CurrencyRateModel>
    ): List<CurrencyRateModel> {

        // Find the conversion rate for the given currency code
        val conversionRate = currencyRates.find { it.code == currencyCode }?.rate ?: 1.0

        // Apply conversion rate to each currency rate and multiply by amount
        return currencyRates.map { currencyRate ->
            val convertedRate = currencyRate.rate * (1 / conversionRate) * amount
            CurrencyRateModel(rate = convertedRate, code = currencyRate.code)
        }
    }
}