package com.example.currencyconvertor.feature_currency.data.data_source.cache.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Represents a currency rate entity in the database.
 * @property id The unique identifier of the currency rate.
 * @property code The currency code.
 * @property rate The currency rate.
 * @property timeStamp The timestamp when the rates were last updated.
 */
@Entity(tableName = CurrencyRateEntity.TABLE_NAME)
@Parcelize
data class CurrencyRateEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "currency_code") val code: String,

    @ColumnInfo(name = "currency_value") val rate: Double,

    val timeStamp: Long = 0

) : Parcelable {
    companion object {
        const val TABLE_NAME = "Currency_rate"
    }
}
