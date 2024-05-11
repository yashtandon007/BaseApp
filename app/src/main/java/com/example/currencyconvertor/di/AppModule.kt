package com.example.currencyconvertor.di

import android.app.Application
import androidx.room.Room
import com.example.currencyconvertor.feature_currency.data.data_source.cache.abstraction.CurrencyCacheDataSource
import com.example.currencyconvertor.feature_currency.data.data_source.cache.implementation.CurrencyCacheDataSourceImpl
import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyDao
import com.example.currencyconvertor.feature_currency.data.data_source.cache.CurrencyDatabase
import com.example.currencyconvertor.feature_currency.data.data_source.network.ApiService
import com.example.currencyconvertor.feature_currency.data.data_source.network.abstraction.CurrencyNetworkDataSource
import com.example.currencyconvertor.feature_currency.data.data_source.network.implementation.CurrencyNetworkDataSourceImpl
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

    @Singleton
    @Provides
    fun providesCurrencyDao(currencyDao: CurrencyDatabase): CurrencyDao {
        return currencyDao.currencyDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyCacheDataSource(
        currencyDao: CurrencyDao
    ) : CurrencyCacheDataSource {
        return CurrencyCacheDataSourceImpl(currencyDao)
    }

    @Provides
    @Singleton
    fun provideCurrencyNetworkDataSource(
        apiService: ApiService
    ) : CurrencyNetworkDataSource {
        return CurrencyNetworkDataSourceImpl(apiService)
    }


    @Provides
    @Singleton
    fun provideCurrencyRepository(
        currencyCacheDataSource: CurrencyCacheDataSource,
        currencyNetworkDataSource: CurrencyNetworkDataSource
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            currencyCacheDataSource, currencyNetworkDataSource
        )
    }

}