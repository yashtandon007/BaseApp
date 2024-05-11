package com.example.currencyconvertor.feature_currency.data.util

import com.example.currencyconvertor.feature_currency.data.data_source.cache.entity.CurrencyCacheEntity
import com.example.currencyconvertor.feature_currency.data.data_source.cache.entity.CurrencyRateEntity
import com.example.currencyconvertor.feature_currency.data.data_source.network.entity.CurrencyNetworkEntity
import com.example.currencyconvertor.feature_currency.data.data_source.network.entity.CurrencyRatesNetworkEntity
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel

fun List<CurrencyCacheEntity>.toCurrencyModel(): List<CurrencyModel> {
    return map {
        CurrencyModel(
            name = it.name, code = it.code
        )
    }
}

fun List<CurrencyModel>.toCurrencyCacheEntity(): List<CurrencyCacheEntity> {
    return map {
        CurrencyCacheEntity(
           name = it.name, code = it.code
        )
    }
}

fun List<CurrencyNetworkEntity>.toCacheEntity(): List<CurrencyCacheEntity> {
    return map {
        CurrencyCacheEntity(
            name = it.rate, code = it.code
        )
    }
}

fun CurrencyRatesNetworkEntity.toCacheEntity(): List<CurrencyRateEntity> {
    return rates.map {
        CurrencyRateEntity(
            code = it.key, rate = it.value
        )
    }
}

fun List<CurrencyRateEntity>.toCurrencyRateModel(): List<CurrencyRateModel> {
    return map {
        CurrencyRateModel(
            rate = it.rate, code = it.code
        )
    }
}

fun List<CurrencyRateModel>.toCurrencyRateEntity(): List<CurrencyRateEntity> {
    return map {
        CurrencyRateEntity(
            rate = it.rate, code = it.code
        )
    }
}

