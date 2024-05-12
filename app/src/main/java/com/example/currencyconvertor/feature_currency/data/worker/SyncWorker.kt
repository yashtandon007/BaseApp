package com.example.currencyconvertor.feature_currency.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction.CurrencyCacheDataSource
import com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction.CurrencyNetworkDataSource
import com.example.currencyconvertor.feature_currency.util.printLogD
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val networkDataSource: CurrencyNetworkDataSource,
    private val cacheDataSource: CurrencyCacheDataSource
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val data = networkDataSource.getCurrencyRates()
            val networkTimeStamp = data.firstOrNull()?.timeStamp ?: 0
            val cacheTimeStamp = cacheDataSource.getCurrencyRates().firstOrNull()?.timeStamp ?: 0

            // New data is available at server
            if (networkTimeStamp - cacheTimeStamp >= 0) {
                cacheDataSource.deleteCurrencyRates()
                cacheDataSource.insertCurrencyRates(data)
            }
            Result.success()
        } catch (e: Exception) {
            printLogD("worker", "syncing data failed : Cause ${e.message}")
            Result.failure()
        }
    }


    companion object {

        fun startUpSyncWork(context: Context) {
            CoroutineScope(Job()).launch {
                // delay added to let app and its dependencies initialize
                delay(3.seconds)
                val constraints =
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                val work =
                    PeriodicWorkRequestBuilder<SyncWorker>(30, TimeUnit.MINUTES).setConstraints(
                        constraints
                    ).build()
                WorkManager.getInstance(context).enqueue(work)
                printLogD("worker", "sync db request enqueed...")
            }
        }
    }

}
