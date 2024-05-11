package com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction

import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel

interface CurrencyNetworkDataSource {

    suspend fun getCurrencies(): List<CurrencyModel>

    suspend fun getCurrencyRates(): List<CurrencyRateModel>

}