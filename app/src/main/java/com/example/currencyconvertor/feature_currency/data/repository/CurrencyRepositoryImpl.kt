package com.example.currencyconvertor.feature_currency.data.repository

import com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction.CurrencyCacheDataSource
import com.example.currencyconvertor.feature_currency.data.data_source.network.NetworkResult
import com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction.CurrencyNetworkDataSource
import com.example.currencyconvertor.feature_currency.data.util.DataState
import com.example.currencyconvertor.feature_currency.data.util.safeApiCall
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import com.example.currencyconvertor.feature_currency.domain.repository.CurrencyRepository
import com.example.currencyconvertor.feature_currency.util.printLogD
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyCacheDataSource: CurrencyCacheDataSource,
    private val currencyNetworkDataSource: CurrencyNetworkDataSource
) : CurrencyRepository {

    override fun getCurrencies() = flow {
        printLogD("repo", " getCurrencies...")
        if (currencyCacheDataSource.getCurrencies().isEmpty()) {
            val networkResult = safeApiCall {
                currencyNetworkDataSource.getCurrencies()
            }
            when (networkResult) {
                is NetworkResult.Success -> {
                    currencyCacheDataSource.insetCurrencies(networkResult.data.sortedBy { it.name })
                    emit(DataState.Success(currencyCacheDataSource.getCurrencies()))
                }

                is NetworkResult.GenericError -> {
                    printLogD("repository", "n/w error $networkResult")
                    emit(DataState.Error(networkResult.errorMessage))
                }
            }
        } else {
            val currencies = currencyCacheDataSource.getCurrencies()
            printLogD("repository", "currencies success , data size:  ${currencies.size}")
            emit(DataState.Success(currencies))
        }
    }.catch {
        printLogD("repository", " error, msg: ${it.message}")
        emit(DataState.Error(it.message.toString()))
    }

    override fun getCurrencyRates(
        amount: Double, currencyCode: String
    ) = flow {

        printLogD("repo", " getCurrencyRates...")
        if (currencyCacheDataSource.getCurrenciesRates().isEmpty()) {

            val networkResult = safeApiCall {
                currencyNetworkDataSource.getCurrencyRates()
            }
            when (networkResult) {
                is NetworkResult.Success -> {
                    currencyCacheDataSource.insertCurrencyRates(networkResult.data)
                    val convertedRates = getConvertedRates(
                        amount, currencyCode, currencyCacheDataSource.getCurrenciesRates()
                    )
                    emit(DataState.Success(convertedRates))
                }

                is NetworkResult.GenericError -> {
                    emit(DataState.Error(networkResult.errorMessage))
                }
            }
        } else {
            val convertedRates = getConvertedRates(
                amount, currencyCode, currencyCacheDataSource.getCurrenciesRates()
            )
            emit(DataState.Success(convertedRates))
        }

    }.catch {
        emit(DataState.Error(it.message.toString()))
    }

    private fun getConvertedRates(
        amount: Double, currencyCode: String, currencyRates: List<CurrencyRateModel>
    ): List<CurrencyRateModel> {

        val conversionRate = 1.0.div(currencyRates.first {
            it.code == currencyCode
        }.rate)
        return currencyRates.map {
            CurrencyRateModel(
                rate = it.rate.times(conversionRate).times(amount),
                code = it.code
            )
        }
    }

}