package com.example.currencyconvertor.feature_currency.presentation

import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel

data class CurrencyUIState(
    val currencies: List<CurrencyModel> = listOf(),
    val convertedRates: List<CurrencyRateModel> = listOf(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val amount: String = "",
    val selectedCurrency: CurrencyModel? = null
)
