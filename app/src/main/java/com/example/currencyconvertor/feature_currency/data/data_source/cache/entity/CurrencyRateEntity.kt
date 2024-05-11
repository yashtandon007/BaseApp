package com.example.currencyconvertor.feature_currency.data.data_source.cache.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = CurrencyRateEntity.TABLE_NAME)
@Parcelize
data class CurrencyRateEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "currency_code")
    val code: String,

    @ColumnInfo(name = "currency_value")
    val rate: Double

) : Parcelable {
    companion object {
        const val TABLE_NAME = "Currency_rate"
    }
}
