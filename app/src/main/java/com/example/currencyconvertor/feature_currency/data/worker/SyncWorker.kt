package com.example.currencyconvertor.feature_currency.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction.CurrencyCacheDataSource
import com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction.CurrencyNetworkDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val cacheDataSource: CurrencyCacheDataSource,
    private val networkDataSource: CurrencyNetworkDataSource,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val data = networkDataSource.getCurrencyRates()
            val networkTimeStamp = data.firstOrNull()?.timeStamp
            val cacheTimeStamp = cacheDataSource.getCurrenciesRates().firstOrNull()?.timeStamp

            if (cacheTimeStamp != networkTimeStamp) {
                cacheDataSource.deleteCurrencyRates()
                cacheDataSource.insertCurrencyRates(data)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
