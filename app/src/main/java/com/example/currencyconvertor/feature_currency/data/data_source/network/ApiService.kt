package com.example.currencyconvertor.feature_currency.data.data_source.network

import com.example.currencyconvertor.feature_currency.data.data_source.network.entity.CurrencyRatesNetworkEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("currencies.json")
    suspend fun getCurrencies(@Query("app_id") appId: String = APP_ID): Response<Map<String, String>>

    @GET("latest.json")
    suspend fun getCurrencyRates(@Query("app_id") appId: String = APP_ID): Response<CurrencyRatesNetworkEntity>


    companion object {
        const val BASE_API_URL = "https://openexchangerates.org/api/"
        const val APP_ID = "0ba292a7c6d2434fb99ed629b9f08696"
    }

}