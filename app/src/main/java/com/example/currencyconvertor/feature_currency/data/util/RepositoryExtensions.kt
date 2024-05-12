package com.example.currencyconvertor.feature_currency.data.util

import com.example.currencyconvertor.feature_currency.data.data_source.network.NetworkConstants.NETWORK_TIMEOUT
import com.example.currencyconvertor.feature_currency.data.data_source.network.NetworkResult
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.NETWORK_ERROR_NO_NETWORK
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.NETWORK_ERROR_TIMEOUT
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.NETWORK_ERROR_UNKNOWN
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import okio.IOException

/**
 *
 * A safe API call wrapper that handles exceptions and returns a [NetworkResult].
 *
 * @param apiCall The suspend function
 *
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): NetworkResult<T> {
    return try {
        withTimeout(NETWORK_TIMEOUT) {
            NetworkResult.Success(apiCall.invoke())
        }
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        when (throwable) {
            is TimeoutCancellationException -> {
                NetworkResult.Error(NETWORK_ERROR_TIMEOUT)
            }

            is IOException -> {
                NetworkResult.Error( NETWORK_ERROR_NO_NETWORK)
            }

            else -> {
                NetworkResult.Error(NETWORK_ERROR_UNKNOWN)
            }
        }
    }
}























