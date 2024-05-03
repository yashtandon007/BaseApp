package com.example.myapplication.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.feature_note.data.data_source.cache.CacheMapper
import com.example.myapplication.feature_note.data.data_source.cache.NoteDatabase
import com.example.myapplication.feature_note.data.repository.NoteRepositoryImpl
import com.example.myapplication.feature_note.domain.repository.NoteRepository
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
    fun provideNoteDatabase(
        app: Application
    ): NoteDatabase {
        return Room.databaseBuilder(
            app, NoteDatabase::class.java, NoteDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(
            db.noteDao(), CacheMapper()
        )
    }


}