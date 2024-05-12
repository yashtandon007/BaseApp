package com.example.currencyconvertor.feature_currency.data.util

/**
 *
 * A sealed class representing different states of data.
 *
 * @param T The type of data.
 * @property Success The data is successfully loaded.
 * @property Error The data loading failed.
 */
sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val message: String) : DataState<T>()
}
