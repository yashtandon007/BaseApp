package com.example.currencyconvertor.feature_currency.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel


@Composable
fun CurrencyConvertorItem(
    currencyRateModel: CurrencyRateModel, modifier: Modifier = Modifier
) {

    Box(
        modifier.clip(shape = RoundedCornerShape(12.dp))
    ) {

        Column(
            modifier = modifier
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = currencyRateModel.amount,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = currencyRateModel.currencyCode,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@Preview( showBackground = true)
@Composable
private fun CurrencyConvertorItemPreview() {
    CurrencyConvertorItem(
        currencyRateModel = CurrencyRateModel(
            currencyCode = "USD",
            amount = "1.1")
    )
}