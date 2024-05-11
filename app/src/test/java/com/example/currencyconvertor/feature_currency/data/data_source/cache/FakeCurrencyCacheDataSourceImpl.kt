package com.example.currencyconvertor.feature_currency.data.data_source.cache

import com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction.CurrencyCacheDataSource
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.NETWORK_ERROR_UNKNOWN
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import javax.inject.Inject


class FakeCurrencyCacheDataSourceImpl  : CurrencyCacheDataSource {

    private var isError =false

    private val currenciesData = mutableListOf<CurrencyModel>()
    private val currencyRatesData = mutableListOf<CurrencyRateModel>()

    override suspend fun getCurrencies(): List<CurrencyModel> = currenciesData

    override suspend fun insetCurrencies(currencies: List<CurrencyModel>) {
        if(isError){
            throw Exception(NETWORK_ERROR_UNKNOWN)
        }
        currenciesData.addAll(currencies)
    }

    override suspend fun insertCurrencyRates(currencyRates: List<CurrencyRateModel>) {
        if(isError){
            throw Exception(NETWORK_ERROR_UNKNOWN)
        }
        currencyRatesData.addAll(currencyRates)
    }

    override suspend fun getCurrenciesRates(): List<CurrencyRateModel> {
        if(isError){
            throw Exception(NETWORK_ERROR_UNKNOWN)
        }
        return currencyRatesData
    }

    fun setError(){
        isError = true
    }

}