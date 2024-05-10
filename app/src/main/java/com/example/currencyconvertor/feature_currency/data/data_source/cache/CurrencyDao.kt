package com.example.currencyconvertor.feature_currency.data.data_source.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao{

    @Query("SELECT * FROM currency")
    suspend fun getCurrencies(): List<CurrencyCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetCurrencies(currencies: List<CurrencyCacheEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRates(currencyRate: List<CurrencyRateEntity>)

    @Query("SELECT * FROM currency_rate")
    suspend fun getCurrenciesRates(): List<CurrencyRateEntity>

}





