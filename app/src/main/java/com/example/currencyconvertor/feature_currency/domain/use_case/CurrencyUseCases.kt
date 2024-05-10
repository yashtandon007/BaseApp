package com.example.currencyconvertor.feature_currency.domain.use_case

import javax.inject.Inject

data class CurrencyUseCases @Inject constructor(
    val getCurrenciesUseCase: GetCurrenciesUseCase,
    val getCurrencyRateUseCase: GetCurrencyRateUseCase
)