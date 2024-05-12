package com.example.currencyconvertor.feature_currency.data.data_source.network

/**
 * A generic class representing the result of a network request.
 *
 * @param T The type of the data returned by the
 */
sealed class NetworkResult<out T> {

    /**
     * Represents a successful response from the server.
     * @param data The data returned by the server.
     */
    data class Success<out T>(val data: T): NetworkResult<T>()

    /**
     * Represents an error response from the server.
     * @param code The HTTP status code.
     * @param errorMessage The error message returned by the server.
     */
    data class Error(
        val code: Int? = null,
        val errorMessage: String
    ): NetworkResult<Nothing>()

}
