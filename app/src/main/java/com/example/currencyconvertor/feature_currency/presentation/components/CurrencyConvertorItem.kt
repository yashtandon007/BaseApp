package com.example.currencyconvertor.feature_currency.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
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
        modifier
            .background(MaterialTheme.colorScheme.secondary)
            .clip(shape = RoundedCornerShape(12.dp))
    ) {

        Column(
            modifier = modifier
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = currencyRateModel.rate.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = currencyRateModel.code,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun CurrencyConvertorItemPreview() {
    CurrencyConvertorItem(
        currencyRateModel = CurrencyRateModel(
            code = "USD",
            rate = 1.1)
    )
}