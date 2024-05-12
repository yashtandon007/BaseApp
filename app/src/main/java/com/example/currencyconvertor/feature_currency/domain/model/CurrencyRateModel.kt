package com.example.currencyconvertor.feature_currency.domain.model

/**
 *
 * Represents a currency rate.
 *
 * @property rate The exchange rate.
 * @property code The currency code.
 */
data class CurrencyRateModel(
    val rate: Double,
    val code: String,
    val timeStamp: Long? = 0
)
