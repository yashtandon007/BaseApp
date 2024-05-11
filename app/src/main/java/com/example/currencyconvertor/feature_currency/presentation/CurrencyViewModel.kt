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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    var state by mutableStateOf(CurrencyUIState())
        private set
    private var getCurrenciesJob: Job? = null
    private var convertCurrencyJob: Job? = null

    fun onEvent(event: CurrencyEvent) {
        printLogD("vm", " onEvent, : $event")
        when (event) {
            is CurrencyEvent.GetCurrencies -> {
                getCurrencies()
            }

            is CurrencyEvent.AmountChanged -> {
                state = state.copy(
                    amount = event.amount
                )
                convertCurrency()
            }

            is CurrencyEvent.OnItemSelected -> {
                event.currencyModel?.let {
                    state = state.copy(
                        selectedCurrencyCode = it.code
                    )
                }
                convertCurrency()
            }
        }
    }

    private fun getCurrencies() {
        getCurrenciesJob?.cancel()
        getCurrenciesJob = currencyRepository.getCurrencies().onEach {
            when (it) {
                is DataState.Error -> {

                }

                is DataState.Success -> {
                    state = state.copy(
                        currencies = it.data, isLoading = false
                    )
                    onEvent(CurrencyEvent.OnItemSelected(it.data.getOrNull(0)))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun convertCurrency() {
        convertCurrencyJob?.cancel()
        val amount = state.amount.ifBlank { "0.0" }
        val currencyCode = state.selectedCurrencyCode

        if (currencyCode.isNotBlank()) {
            convertCurrencyJob =
                currencyRepository.getCurrencyRates(amount.toDouble(), currencyCode).onEach {
                    when (it) {
                        is DataState.Error -> {

                        }

                        is DataState.Success -> {
                            state = state.copy(
                                convertedRates = it.data
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

}


