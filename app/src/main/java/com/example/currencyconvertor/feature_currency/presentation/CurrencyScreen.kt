package com.example.currencyconvertor.feature_currency.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconvertor.R
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import com.example.currencyconvertor.feature_currency.presentation.components.CurrencyConvertorItem
import com.example.currencyconvertor.feature_currency.presentation.components.CurrencySelector

@Composable
fun CurrencyScreen(
    state: CurrencyUIState, onEvent: (CurrencyEvent) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(70.dp)
                    .align(Center)
            )
        } else if (!state.error.isNullOrBlank()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = state.error, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(6.dp))
                Button(onClick = {
                    onEvent(CurrencyEvent.GetCurrencies)
                }) {
                    Text(stringResource(R.string.reload))
                }
            }

        } else {
             Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    value = state.amount,
                    onValueChange = {
                        if (!isValidAmount(it)) {
                            return@TextField
                        }
                        onEvent(CurrencyEvent.AmountChanged(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    textStyle = MaterialTheme.typography.titleLarge,
                    label = { Text(stringResource(R.string.amount)) },
                    shape = RoundedCornerShape(6.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                CurrencySelector(
                    modifier = Modifier, state = state
                ) {
                    onEvent(CurrencyEvent.SelectCurrencyCode(it))
                }

                Spacer(modifier = Modifier.height(6.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(state.convertedRates) { currencyRateModel ->
                        CurrencyConvertorItem(
                            modifier = Modifier.fillMaxSize(), currencyRateModel = currencyRateModel
                        )
                    }
                }
            }
        }
    }
}

/**
 * Checks if the amount is valid double, accepts empty.
 */
private fun isValidAmount(amount: String): Boolean {
    if (amount.isEmpty()) {
        return true
    }
    val regex = Regex("[0-9]{1,13}(\\.[0-9]*)?")
    return regex.matches(amount)
}

@Composable
@Preview(showBackground = true)
fun CurrencyScreenPreview() {
    CurrencyScreen(
        state = CurrencyUIState(
            error = "asassa",
            currencies = listOf(
                CurrencyModel(
                    code = "Code", name = "USD"
                )
            ),
            convertedRates = listOf(
                CurrencyRateModel(
                    rate = 1.1, code = "USD"
                ),
                CurrencyRateModel(
                    rate = 5.1, code = "PKR"
                ),
                CurrencyRateModel(
                    rate = 1.5, code = "INR"
                ),
                CurrencyRateModel(
                    rate = 2.1, code = "AUD"
                ),
            ), selectedCurrency = CurrencyModel(
                code = "USD", name = "Dollar"
            ),
            isLoading = false,
        )
    ) {

    }
}