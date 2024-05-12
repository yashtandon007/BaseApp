package com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction

import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel

/**
 * A network data source for currencies.
 */
interface CurrencyNetworkDataSource {

    suspend fun getCurrencies(): List<CurrencyModel>

    suspend fun getCurrencyRates(): List<CurrencyRateModel>

}