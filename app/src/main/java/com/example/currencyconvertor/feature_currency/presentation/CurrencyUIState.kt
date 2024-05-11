package com.example.currencyconvertor.feature_currency.presentation

import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel

data class CurrencyUIState(
    val currencyModels: List<CurrencyModel> = listOf(),
    val currencyRateModel: List<CurrencyRateModel> = listOf(),
    val isLoading: Boolean = true,
    val amount: String = "",
    val selectedCurrencyCode: String = "",
)
