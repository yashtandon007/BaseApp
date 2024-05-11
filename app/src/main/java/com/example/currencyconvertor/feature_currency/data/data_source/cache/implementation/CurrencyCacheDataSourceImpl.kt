package com.example.currencyconvertor.feature_currency.data.data_source.cache.implementation

import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyDao
import com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction.CurrencyCacheDataSource
import com.example.currencyconvertor.feature_currency.data.util.toCurrencyCacheEntity
import com.example.currencyconvertor.feature_currency.data.util.toCurrencyModel
import com.example.currencyconvertor.feature_currency.data.util.toCurrencyRateEntity
import com.example.currencyconvertor.feature_currency.data.util.toCurrencyRateModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyModel
import com.example.currencyconvertor.feature_currency.domain.model.CurrencyRateModel
import javax.inject.Inject


class CurrencyCacheDataSourceImpl @Inject constructor(
    private val currencyDao: CurrencyDao
) : CurrencyCacheDataSource {

    override suspend fun getCurrencies(): List<CurrencyModel> =
        currencyDao.getCurrencies().toCurrencyModel()

    override suspend fun insetCurrencies(currencies: List<CurrencyModel>) =
        currencyDao.insetCurrencies(currencies.toCurrencyCacheEntity())

    override suspend fun insertCurrencyRates(currencyRates: List<CurrencyRateModel>) =
        currencyDao.insertCurrencyRates(currencyRates.toCurrencyRateEntity())

    override suspend fun getCurrenciesRates(): List<CurrencyRateModel> =
        currencyDao.getCurrenciesRates().toCurrencyRateModel()

}