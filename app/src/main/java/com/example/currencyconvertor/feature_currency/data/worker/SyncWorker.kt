package com.example.currencyconvertor.feature_currency.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyDao
import com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction.CurrencyCacheDataSource
import com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction.CurrencyNetworkDataSource
import com.example.currencyconvertor.feature_currency.util.printLogD
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.multibindings.IntKey
import javax.inject.Inject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val networkDataSource: CurrencyNetworkDataSource,
    private val cacheDataSource: CurrencyCacheDataSource
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            printLogD("worker", "syncing data...")

            val data = networkDataSource.getCurrencyRates()
            printLogD("worker", "Data:before dele ${cacheDataSource.getCurrencies().size}...")

            val networkTimeStamp = data.firstOrNull()?.timeStamp
            val cacheTimeStamp = cacheDataSource.getCurrencyRates().firstOrNull()?.timeStamp
            printLogD("worker", "networkTimeStamp: ${networkTimeStamp}.  and cacheTimeStamp: ${cacheTimeStamp}..")

            if (cacheTimeStamp != networkTimeStamp) {
                cacheDataSource.deleteCurrencyRates()
                printLogD("worker", "Data:after dele ${cacheDataSource.getCurrencies().size}...")
                cacheDataSource.insertCurrencyRates(data)
                printLogD("worker", "Data:after inser ${cacheDataSource.getCurrencies().size}...")
            }
            Result.success()
        } catch (e: Exception) {
            printLogD("worker", "syncing data failed : Cause ${e.message}...")
            Result.failure()
        }
    }


    companion object {
        /**
         * Expedited one time work to sync data on app startup
         */
        fun startUpSyncWork(context: Context) {
//            val constraints = Constraints.Builder()
//                .setRequiresCharging(true)
//                .build()
            val work = OneTimeWorkRequestBuilder<SyncWorker>()
                .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(work)
            printLogD("worker", "sync db request enqueed...")
        }
    }

}
