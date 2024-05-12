package com.example.currencyconvertor

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.currencyconvertor.feature_currency.data.worker.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class CurrencyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        enqueueSyncDb()

    }

    private fun enqueueSyncDb() {
        val syncDbWorkRequest =
            PeriodicWorkRequestBuilder<SyncWorker>(30, TimeUnit.MINUTES).setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).build()
        WorkManager.getInstance(applicationContext).enqueue(syncDbWorkRequest)
    }
}
