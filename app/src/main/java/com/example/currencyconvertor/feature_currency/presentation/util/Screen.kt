package com.example.currencyconvertor.feature_currency.presentation.util

sealed class Screen(
    val route: String
) {
    data object NoteScreen : Screen("note_screen")
}