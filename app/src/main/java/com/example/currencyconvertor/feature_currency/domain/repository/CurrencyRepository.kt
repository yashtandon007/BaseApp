package com.example.currencyconvertor.feature_currency.domain.repository

import com.example.currencyconvertor.feature_currency.data.data_source.DataState
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getCurrencies(): Flow<DataState<List<CurrencyModel>>>

    fun getCurrencyRates(
        amount: Double,
        currencyCode: String
    ): Flow<DataState<List<CurrencyRateModel>>>
}