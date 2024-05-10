package com.example.currencyconvertor.feature_currency.data.data_source.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyNetworkEntity(
    val code: String, val name: String, val timeStamp: Long = 0
) : Parcelable