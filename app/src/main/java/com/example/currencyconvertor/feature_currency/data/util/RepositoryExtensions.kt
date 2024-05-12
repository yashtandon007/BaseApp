package com.example.currencyconvertor.feature_currency.data.util

import com.example.currencyconvertor.feature_currency.data.data_source.network.NetworkConstants.NETWORK_TIMEOUT
import com.example.currencyconvertor.feature_currency.data.data_source.network.NetworkResult
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.ERROR_UNKNOWN
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.NETWORK_ERROR_TIMEOUT
import com.example.currencyconvertor.feature_currency.data.util.GenericErrors.NETWORK_ERROR_UNKNOWN
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException

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
                    val code = 408 // timeout error code
                    NetworkResult.Error(code, NETWORK_ERROR_TIMEOUT)
                }

                is HttpException -> {
                   val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    //      cLog(errorResponse)
                    NetworkResult.Error(
                        code, errorResponse
                    )
                }

                else -> {
                    //     cLog(NETWORK_ERROR_UNKNOWN)
                    NetworkResult.Error(
                        null, NETWORK_ERROR_UNKNOWN
                    )
                }
            }
    }
}

private fun convertErrorBody(throwable: HttpException): String {
    return try {
        throwable.response()?.errorBody()?.string() ?: ERROR_UNKNOWN
    } catch (exception: Exception) {
        ERROR_UNKNOWN
    }
}























