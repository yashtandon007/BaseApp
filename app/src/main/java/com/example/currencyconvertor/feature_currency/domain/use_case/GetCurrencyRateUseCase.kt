package com.example.currencyconvertor.feature_currency.domain.use_case

import com.example.currencyconvertor.feature_currency.data.data_source.DataState
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import com.example.currencyconvertor.feature_currency.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyRateUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke(
        amount: Double, currencyCode: String
    ): Flow<DataState<List<CurrencyRateModel>>> {
        return repository.getCurrencyRates(
            amount, currencyCode
        )
    }
}