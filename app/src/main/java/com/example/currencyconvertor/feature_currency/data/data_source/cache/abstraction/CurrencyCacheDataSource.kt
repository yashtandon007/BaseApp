package com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction

import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel


interface CurrencyCacheDataSource {
    suspend fun getCurrencies(): List<CurrencyModel>

    suspend fun insetCurrencies(currencies: List<CurrencyModel>)

    suspend fun insertCurrencyRates(currencyRates: List<CurrencyRateModel>)

    suspend fun getCurrenciesRates(): List<CurrencyRateModel>

    suspend fun deleteCurrencyRates()
}