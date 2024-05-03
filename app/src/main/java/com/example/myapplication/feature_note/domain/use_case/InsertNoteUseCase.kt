package com.example.myapplication.feature_note.domain.use_case

import com.example.myapplication.feature_note.domain.model.InValidNoteException
import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(private val repository: NoteRepository) {

    @Throws(InValidNoteException::class)
    suspend operator fun invoke(note: Note) {
        println("inserting invoked..")

        if (note.title.isBlank()) {
            throw InValidNoteException("Title of note cant be empty")
        }
        if (note.content.isBlank()) {
            throw InValidNoteException("Content of note cant be empty")
        }
        println("inserting invoked 2..")
        repository.insertNote(note)
    }

}