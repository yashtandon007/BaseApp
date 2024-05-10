package com.example.currencyconvertor.feature_currency.domain.use_case

import com.example.currencyconvertor.feature_currency.data.data_source.DataState
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke(): Flow<DataState<List<CurrencyModel>>> {
        return repository.getCurrencies()
    }
}