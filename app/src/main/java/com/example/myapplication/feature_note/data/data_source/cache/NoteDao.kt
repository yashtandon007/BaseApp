package com.example.myapplication.feature_note.data.data_source.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{


    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteCacheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetNote(note: NoteCacheEntity)

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNote(id:Int): NoteCacheEntity?

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Int)

}