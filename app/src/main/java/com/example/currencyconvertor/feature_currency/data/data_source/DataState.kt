package com.example.currencyconvertor.feature_currency.data.data_source

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val message: String) : DataState<T>()
}
