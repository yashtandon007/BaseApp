package com.example.currencyconvertor.feature_currency.data.data_source.network.implementation

import com.example.currencyconvertor.feature_currency.data.data_source.network.ApiService
import com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction.CurrencyNetworkDataSource
import com.example.currencyconvertor.feature_currency.data.util.toCacheEntity
import com.example.currencyconvertor.feature_currency.data.util.toCurrencyRateModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import javax.inject.Inject

class CurrencyNetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyNetworkDataSource {

    override suspend fun getCurrencies(): List<CurrencyModel> {
        return apiService.getCurrencies().body()?.map { (code, name) ->
            CurrencyModel(
                code = code, name = name
            )
        }.orEmpty()
    }

    override suspend fun getCurrencyRates(): List<CurrencyRateModel> {

        return apiService.getCurrencyRates().body()?.toCurrencyRateModel()
            .orEmpty()
    }

}

