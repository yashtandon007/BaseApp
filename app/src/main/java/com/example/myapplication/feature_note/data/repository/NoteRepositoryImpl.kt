package com.example.myapplication.feature_note.data.repository

import com.example.myapplication.feature_note.data.data_source.cache.CacheMapper
import com.example.myapplication.feature_note.data.data_source.cache.NoteDao
import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao, private val noteCacheMapper: CacheMapper
) : NoteRepository {

    override suspend fun getNote(id: Int): Note? {
        return noteDao.getNote(id)?.let {
            noteCacheMapper.mapFromEntity(it)
        }
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map { noteEntities ->
            noteEntities.map {
                noteCacheMapper.mapFromEntity(it)
            }
        }
    }

    override suspend fun insertNote(note: Note) {
        val n = noteCacheMapper.mapToEntity(note)
        println("inserting not $n")
        noteDao.insetNote(n)
    }

    override suspend fun deleteNote(id: Int) {
        noteDao.deleteNote(id)
    }
}