package com.example.currencyconvertor.feature_currency.data.data_source.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CurrencyCacheEntity::class,CurrencyRateEntity::class], version = 1, exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        const val DB_NAME = "currency_db"
    }
}
