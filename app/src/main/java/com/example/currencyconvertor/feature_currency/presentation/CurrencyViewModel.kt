package com.example.currencyconvertor.feature_currency.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvertor.feature_currency.data.util.DataState
import com.example.currencyconvertor.feature_currency.domain.repository.CurrencyRepository
import com.example.currencyconvertor.feature_currency.util.printLogD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    var state by mutableStateOf(CurrencyUIState())
        private set


    private var job: Job? = null

    init {
        onEvent(CurrencyEvent.GetCurrencies)
    }

    fun onEvent(event: CurrencyEvent) {
        job?.cancel()
        job = viewModelScope.launch {
            printLogD("viewModel", " onEvent, : $event")
            when (event) {
                is CurrencyEvent.GetCurrencies -> {
                    getCurrencies()
                }

                is CurrencyEvent.AmountChanged -> {
                    state = state.copy(
                        amount = event.amount
                    )
                    getCurrencyRates()
                }

                is CurrencyEvent.SelectCurrencyCode -> {
                    event.currencyModel?.let {
                        state = state.copy(
                            selectedCurrencyCode = it.code
                        )
                    }
                    getCurrencyRates()
                }
            }
        }
    }

    private suspend fun getCurrencies() {
        resetStateToDefault()
        currencyRepository.getCurrencies().collect { result ->
            printLogD("viewModel", " currencies result, : $result")
            when (result) {
                is DataState.Error -> {
                    state = state.copy(
                        isLoading = false, error = result.message
                    )
                }

                is DataState.Success -> {
                    state = state.copy(
                        currencies = result.data, isLoading = false
                    )
                    if (state.selectedCurrencyCode.isBlank()) {
                        onEvent(CurrencyEvent.SelectCurrencyCode(state.currencies.getOrNull(0)))
                    }
                }
            }
        }
    }

    private fun resetStateToDefault() {
        state = state.copy(
            isLoading = true, error = null
        )
    }

    private suspend fun getCurrencyRates() {
        val amount = state.amount.toDoubleOrNull() ?: 0.0
        val currencyCode = state.selectedCurrencyCode

        if (currencyCode.isNotBlank()) {
            currencyRepository.getCurrencyRates(amount, currencyCode).collect { result ->
                printLogD("viewModel", " currency rates result, : $result")
                when (result) {
                    is DataState.Error -> {
                        state = state.copy(
                            error = result.message
                        )
                    }

                    is DataState.Success -> {
                        state = state.copy(
                            convertedRates = result.data
                        )
                    }
                }
            }
        }
    }

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
    }
}


