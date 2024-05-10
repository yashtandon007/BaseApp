package com.example.currencyconvertor.feature_currency.data.data_source

import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyCacheEntity
import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyRateEntity
import com.example.currencyconvertor.feature_currency.data.data_source.network.CurrencyNetworkEntity
import com.example.currencyconvertor.feature_currency.data.data_source.network.CurrencyRatesNetworkEntity
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel

fun List<CurrencyCacheEntity>.toCurrencyModel(): List<CurrencyModel> {
    return map {
        CurrencyModel(
            id = it.id, name = it.name, code = it.code
        )
    }
}

fun List<CurrencyNetworkEntity>.toCacheEntity(): List<CurrencyCacheEntity> {
    return map {
        CurrencyCacheEntity(
            name = it.name, code = it.code, timeStamp = it.timeStamp
        )
    }
}

fun CurrencyRatesNetworkEntity.toCacheEntity(): List<CurrencyRateEntity> {
    return rates.map {
        CurrencyRateEntity(
            currencyCode = it.key, amount = it.value
        )
    }
}

fun List<CurrencyRateEntity>.toCurrencyRateModel(
    amount: Double, conversionRate: Double
): List<CurrencyRateModel> {
    return map {
        CurrencyRateModel(
            amount = it.amount.times(conversionRate).times(amount).toString(),
            currencyCode = it.currencyCode
        )
    }
}

