package com.example.myapplication.feature_note.data.data_source.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NoteCacheEntity::class], version = 2
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DB_NAME = "note_db"
    }
}