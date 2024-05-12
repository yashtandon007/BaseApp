package com.example.currencyconvertor.feature_currency.presentation.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.presentation.CurrencyUIState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelector(
    state: CurrencyUIState, modifier: Modifier = Modifier, onItemSelected: (CurrencyModel) -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }) {

        val currencyLabel = state.selectedCurrency?.let { "${it.code}  (${it.name})" } ?: ""

        TextField(modifier = Modifier.menuAnchor(),
            onValueChange = { },
            value = currencyLabel,
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            })
        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            state.currencies.forEachIndexed { index, item ->
                DropdownMenuItem(text = {
                    Text(text = item.name)
                }, onClick = {
                    isExpanded = false
                    onItemSelected(state.currencies[index])
                }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CurrencySelectorPreview() {
    CurrencySelector(state = CurrencyUIState(
        currencies = listOf(
            CurrencyModel(
                code = "USD", name = "United States Dollar"
            )
        ), selectedCurrency = CurrencyModel(
            code = "USD", name = "United States Dollar"
        )
    ), onItemSelected = {})
}