package com.example.myapplication.feature_note.domain.use_case

import com.example.myapplication.feature_note.domain.model.InValidNoteException
import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNoteUseCase @Inject constructor(private val repository: NoteRepository) {

    @Throws(InValidNoteException::class)
    operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotes()
    }
}