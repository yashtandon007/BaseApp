package com.example.currencyconvertor.feature_currency.data.data_source.network

sealed class NetworkResult<out T> {

    data class Success<out T>(val data: T): NetworkResult<T>()

    data class GenericError(
        val code: Int? = null,
        val errorMessage: String
    ): NetworkResult<Nothing>()

}
