package com.example.currencyconvertor.feature_currency.presentation

import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel

sealed class CurrencyEvent{
    data class OnItemSelected(
        val currencyModel: CurrencyModel?
    ): CurrencyEvent()
    data class AmountChange(
        val amount: String
    ): CurrencyEvent()
}
