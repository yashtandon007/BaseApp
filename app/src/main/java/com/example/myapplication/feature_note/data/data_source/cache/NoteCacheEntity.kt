package com.example.myapplication.feature_note.data.data_source.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.feature_note.domain.model.Note

@Entity(tableName = "notes")
data class NoteCacheEntity(

    @PrimaryKey val id: Int?,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int
)
