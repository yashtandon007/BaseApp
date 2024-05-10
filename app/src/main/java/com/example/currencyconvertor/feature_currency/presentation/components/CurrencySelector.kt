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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelector(
    items: List<CurrencyModel>,
    modifier: Modifier = Modifier,
    onItemSelected: (CurrencyModel) -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf(items[0])
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }) {

        TextField(modifier = Modifier.menuAnchor(),
            onValueChange = { },
            value = selectedItem.code,
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            })
        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item.name)
                    },
                    onClick = {
                        selectedItem = items[index]
                        isExpanded = false
                        onItemSelected(items[index])
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CurrencySelectorPreview() {
    CurrencySelector(
        listOf(
            CurrencyModel(
                code = "USD", name = "United States Dollar"
            )
        ), onItemSelected = {}
    )
}