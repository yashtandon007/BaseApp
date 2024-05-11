package com.example.currencyconvertor.feature_currency.presentation.util

sealed class Screen(
    val route: String
) {
    data object CurrencyScreen : Screen("currency_screen")
}