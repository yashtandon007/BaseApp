package com.example.currencyconvertor.feature_currency.data.data_source.network.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyRatesNetworkEntity(
    val rates: Map<String,Double>, val timeStamp: Long = 0
) : Parcelable