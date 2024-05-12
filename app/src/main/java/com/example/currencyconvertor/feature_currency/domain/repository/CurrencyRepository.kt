package com.example.currencyconvertor.feature_currency.domain.repository

import com.example.currencyconvertor.feature_currency.data.util.DataState
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import kotlinx.coroutines.flow.Flow

/**
 *
 * An interface that provides methods for accessing currency data.
 *
 */
interface CurrencyRepository {

    fun getCurrencies(): Flow<DataState<List<CurrencyModel>>>

    fun getCurrencyRates(
        amount: Double,
        currencyCode: String
    ): Flow<DataState<List<CurrencyRateModel>>>
}