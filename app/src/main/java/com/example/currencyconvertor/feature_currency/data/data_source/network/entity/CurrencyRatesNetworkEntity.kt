package com.example.currencyconvertor.feature_currency.data.data_source.network.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents the network entity for currency rates.
 *
 * @property rates A map of currency codes to their exchange rates.
 * @property timeStamp The timestamp when the rates were last updated.
 */
@Parcelize
data class CurrencyRatesNetworkEntity(
    val rates: Map<String,Double>,
    val timeStamp: Long = 0
) : Parcelable