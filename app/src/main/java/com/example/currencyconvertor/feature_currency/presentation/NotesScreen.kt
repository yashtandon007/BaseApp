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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
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
fun NotesScreen(
    state: CurrencyUIState, onEvent: (CurrencyEvent) -> Unit
) {

    var amount by remember {
        mutableStateOf("")
    }
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
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    value = amount,
                    onValueChange = {
                        if (it.contains(",") || it.toCharArray().count { letter ->
                                letter == '.'
                            } > 1) {
                            return@TextField
                        }
                        amount = it
                        onEvent(CurrencyEvent.AmountChange(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    label = { Text(stringResource(R.string.amount)) },
                    shape = RoundedCornerShape(6.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                CurrencySelector(
                    modifier = Modifier, items = state.currencyModels
                ) {
                    onEvent(CurrencyEvent.OnItemSelected(it))
                }

                Spacer(modifier = Modifier.height(6.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),

                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(state.currencyRateModel) { currencyRateModel ->
                        CurrencyConvertorItem(
                            modifier = Modifier.fillMaxSize(), currencyRateModel = currencyRateModel
                        )
                    }
                }
            }
        }
    }


}


@Composable
@Preview(showBackground = true)
fun NotesScreenPreview() {
    NotesScreen(
        state = CurrencyUIState(
            currencyModels = listOf(
                CurrencyModel(
                    code = "Code", name = "USD"
                )
            ),
            currencyRateModel = listOf(
                CurrencyRateModel(
                    amount = "1.1", currencyCode = "USD"
                ),
                CurrencyRateModel(
                    amount = "5.1", currencyCode = "PKR"
                ),
                CurrencyRateModel(
                    amount = "1.5", currencyCode = "INR"
                ),
                CurrencyRateModel(
                    amount = "2.1", currencyCode = "AUD"
                ),
            ),
            isLoading = false,
        )
    ) {

    }
}