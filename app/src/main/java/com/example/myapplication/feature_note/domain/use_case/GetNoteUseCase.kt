package com.example.myapplication.feature_note.domain.use_case

import com.example.myapplication.feature_note.domain.model.InValidNoteException
import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val repository: NoteRepository) {

    @Throws(InValidNoteException::class)
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNote(id)
    }

}