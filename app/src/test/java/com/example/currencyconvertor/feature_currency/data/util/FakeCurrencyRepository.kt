package com.example.currencyconvertor.feature_currency.data.util

import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import com.example.currencyconvertor.feature_currency.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCurrencyRepository : CurrencyRepository {
    override fun getCurrencies(): Flow<DataState<List<CurrencyModel>>> = flow {
        val list = listOf(
            CurrencyModel(code = "USD", name = "Dollar"),
            CurrencyModel(code = "INR", name = "Rupee")
        )
        emit(DataState.Success(list))
    }

    override fun getCurrencyRates(
        amount: Double, currencyCode: String
    ): Flow<DataState<List<CurrencyRateModel>>> = flow {
        val list = listOf(
            CurrencyRateModel(code = "USD", rate = 1.0), CurrencyRateModel(code = "INR", rate = 80.0)
        )
        emit(DataState.Success(list))
    }
}