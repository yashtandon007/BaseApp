package com.example.currencyconvertor.feature_currency.data.data_source.network.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyNetworkEntity(
    val code: String, val rate: String
) : Parcelable