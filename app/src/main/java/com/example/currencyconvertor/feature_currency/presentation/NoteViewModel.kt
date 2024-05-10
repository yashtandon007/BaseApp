package com.example.currencyconvertor.feature_currency.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvertor.feature_currency.data.data_source.DataState
import com.example.currencyconvertor.feature_currency.domain.use_case.CurrencyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val currencyUseCases: CurrencyUseCases
) : ViewModel() {

    var state by mutableStateOf(CurrencyUIState())
        private set
    private var getCurrenciesJob: Job? = null
    private var convertCurrenyJob: Job? = null

    init {
        getNotes()
    }

    private fun getNotes() {
        getCurrenciesJob?.cancel()
        getCurrenciesJob = currencyUseCases.getCurrenciesUseCase().onEach {
            when (it) {
                is DataState.Error -> {

                }

                is DataState.Success -> {
                    state = state.copy(
                        currencyModels = it.data, isLoading = false
                    )
                    onEvent(CurrencyEvent.OnItemSelected(it.data.getOrNull(0)))
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun convertCurrency() {
        convertCurrenyJob?.cancel()
        val amount = state.amount
        val currencyCode = state.currencyCode

        Log.e(
            "yashtandon",
            "convertCurrency amount : ${state.amount}  convertCurrency:currencyCode : ${state.currencyCode} ",
        )
        if (amount.isNotBlank() && currencyCode.isNotBlank()) {
            Log.e(
                "yashtandon",
                "convertCurrency amount : ${state.amount}  convertCurrency:currencyCode : ${state.currencyCode} ",
            )
            convertCurrenyJob =
                currencyUseCases.getCurrencyRateUseCase(amount.toDouble(), currencyCode).onEach {
                    when (it) {
                        is DataState.Error -> {

                        }

                        is DataState.Success -> {
                            state = state.copy(
                                currencyRateModel = it.data
                            )
                        }
                    }
                }.launchIn(viewModelScope)

        }
    }

    fun onEvent(event: CurrencyEvent) {
        Log.e("yashtandon", "onEvent: $event ")
        when (event) {
            is CurrencyEvent.AmountChange -> {
                state = state.copy(
                    amount = event.amount
                )
            }

            is CurrencyEvent.OnItemSelected -> {
                event.currencyModel?.let {
                   state = state.copy(
                        currencyCode = it.code
                    )
                }
            }
        }
        Log.e("yashtandon", "convertCurrency: ")
        convertCurrency()
    }
}


