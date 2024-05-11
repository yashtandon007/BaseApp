package com.example.currencyconvertor.feature_currency.data.data_source.network

import com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction.CurrencyNetworkDataSource
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.NETWORK_ERROR_UNKNOWN
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel

class FakeCurrencyNetworkDataSourceImpl : CurrencyNetworkDataSource {

    private var isError =false

    private val currenciesData = mutableListOf(
        CurrencyModel(
            code = "USD", name = "United States"
        )
    )
    private val currencyRatesData = mutableListOf(
        CurrencyRateModel(
            rate = 1.0,
            code = "USD"
        ),
        CurrencyRateModel(
            rate = 80.0,
            code = "INR"
        )
    )

    override suspend fun getCurrencies(): List<CurrencyModel>{
        if(isError){
            throw android.net.http.HttpException(NETWORK_ERROR_UNKNOWN,Exception(""))
        }
        return currenciesData
    }

    override suspend fun getCurrencyRates(): List<CurrencyRateModel> {
        if(isError){
            throw android.net.http.HttpException(NETWORK_ERROR_UNKNOWN,Exception(""))
        }
        return currencyRatesData
    }

    fun setError(){
        isError = true
    }

}