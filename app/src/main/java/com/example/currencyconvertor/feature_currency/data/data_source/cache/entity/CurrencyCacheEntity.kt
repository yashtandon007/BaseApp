package com.example.currencyconvertor.feature_currency.data.data_source.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a currency entity in the cache.
 * @property id The unique identifier of the currency.
 * @property code The currency code.
 * @property name The currency name.
 */
@Entity(tableName = CurrencyCacheEntity.TABLE_NAME)
data class CurrencyCacheEntity(

    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "currency_code") val code: String,

    @ColumnInfo(name = "currency_name") val name: String,

    ) {
    companion object {
        const val TABLE_NAME = "currency"
    }
}
