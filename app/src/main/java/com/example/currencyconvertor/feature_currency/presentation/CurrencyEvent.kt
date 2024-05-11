package com.example.currencyconvertor.feature_currency.presentation

import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel

sealed interface CurrencyEvent {

    data object GetCurrencies : CurrencyEvent
    data class OnItemSelected(
        val currencyModel: CurrencyModel?
    ) : CurrencyEvent

    data class AmountChanged(
        val amount: String
    ) : CurrencyEvent

}
