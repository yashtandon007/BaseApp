package com.example.myapplication.feature_note.domain.repository

import com.example.myapplication.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getNote(id: Int): Note?
    fun getAllNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(id: Int)

}