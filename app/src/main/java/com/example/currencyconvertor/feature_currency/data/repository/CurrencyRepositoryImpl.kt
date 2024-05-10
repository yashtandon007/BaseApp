package com.example.currencyconvertor.feature_currency.data.repository

import android.util.Log
import com.example.currencyconvertor.feature_currency.data.data_source.DataState
import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyDao
import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyRateEntity
import com.example.currencyconvertor.feature_currency.data.data_source.network.ApiService
import com.example.currencyconvertor.feature_currency.data.data_source.network.CurrencyNetworkEntity
import com.example.currencyconvertor.feature_currency.data.data_source.toCacheEntity
import com.example.currencyconvertor.feature_currency.data.data_source.toCurrencyModel
import com.example.currencyconvertor.feature_currency.data.data_source.toCurrencyRateModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import com.example.currencyconvertor.feature_currency.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CurrencyRepositoryImpl(
    private val currencyDao: CurrencyDao, private val apiService: ApiService

) : CurrencyRepository {

    override fun getCurrencies(): Flow<DataState<List<CurrencyModel>>> =
        flow<DataState<List<CurrencyModel>>> {
            if (currencyDao.getCurrencies().isEmpty()) {
                apiService.getCurrencies().body()?.let { currency ->
                    val currencyList = currency.map { model ->
                        CurrencyNetworkEntity(
                            code = model.key,
                            name = model.value,
                            timeStamp = System.currentTimeMillis()
                        )
                    }
                    currencyDao.insetCurrencies(currencyList.sortedBy { it.name }.toCacheEntity())
                    emit(DataState.Success(currencyDao.getCurrencies().toCurrencyModel()))
                }
            } else {
                emit(DataState.Success(currencyDao.getCurrencies().toCurrencyModel()))
            }
        }.catch {
            emit(DataState.Error(it.message.toString()))
        }

    override fun getCurrencyRates(
        amount: Double, currencyCode: String
    ): Flow<DataState<List<CurrencyRateModel>>> = flow<DataState<List<CurrencyRateModel>>> {

        if (currencyDao.getCurrenciesRates().isEmpty()) {

            apiService.getCurrencyRates().body()?.let {
                Log.e("yashtandon", "response: $it")
                currencyDao.insertCurrencyRates(it.toCacheEntity())
                val currencyRates =
                    getCurrencyRates(amount, currencyCode, currencyDao.getCurrenciesRates())
                emit(DataState.Success(currencyRates))
            }

        } else {
            val currencyRates =
                getCurrencyRates(amount, currencyCode, currencyDao.getCurrenciesRates())
            emit(DataState.Success(currencyRates))
        }

    }.catch {
        Log.e("yashtandon", "err:${it.message.toString()} ")
        emit(DataState.Error(it.message.toString()))
    }

    private fun getCurrencyRates(
        amount: Double, currencyCode: String, currencyRates: List<CurrencyRateEntity>
    ): List<CurrencyRateModel> {

        val conversionRate = currencyRates.first {
            it.currencyCode == currencyCode
        }.amount
        return currencyRates.toCurrencyRateModel(amount,conversionRate)
    }

}