package com.example.currencyconvertor.di

import android.app.Application
import androidx.room.Room
import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyDatabase
import com.example.currencyconvertor.feature_currency.data.data_source.network.ApiService
import com.example.currencyconvertor.feature_currency.data.repository.CurrencyRepositoryImpl
import com.example.currencyconvertor.feature_currency.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCurrencyDatabase(
        app: Application
    ): CurrencyDatabase {
        return Room.databaseBuilder(
            app, CurrencyDatabase::class.java, CurrencyDatabase.DB_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideCurrencyRepository(
        db: CurrencyDatabase, apiService: ApiService
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            db.currencyDao(), apiService
        )
    }


}