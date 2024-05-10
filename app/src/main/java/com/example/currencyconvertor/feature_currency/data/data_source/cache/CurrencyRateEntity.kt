package com.example.currencyconvertor.feature_currency.data.data_source.cache

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
    val currencyCode: String,

    @ColumnInfo(name = "currency_value")
    val amount: Double,

    @ColumnInfo(name = "time_stamp")
    val timestamp: Int = 0

) : Parcelable {
    companion object {
        const val TABLE_NAME = "Currency_rate"
    }
}
