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
            printLogD("vm", " onEvent, : $event")
            when (event) {
                is CurrencyEvent.GetCurrencies -> {
                    getCurrencies()
                    onEvent(CurrencyEvent.SelectCurrencyCode(state.currencies.getOrNull(0)))
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

        currencyRepository.getCurrencies().collect {
            when (it) {
                is DataState.Error -> {

                }

                is DataState.Success -> {
                    state = state.copy(
                        currencies = it.data, isLoading = false
                    )
                }
            }
        }
    }

    private suspend fun getCurrencyRates() {
        val amount = state.amount.ifBlank { "0.0" }
        val currencyCode = state.selectedCurrencyCode

        if (currencyCode.isNotBlank()) {
            currencyRepository.getCurrencyRates(amount.toDouble(), currencyCode).collect {
                when (it) {
                    is DataState.Error -> {

                    }

                    is DataState.Success -> {
                        state = state.copy(
                            convertedRates = it.data
                        )
                    }
                }
            }
        }
    }

}


