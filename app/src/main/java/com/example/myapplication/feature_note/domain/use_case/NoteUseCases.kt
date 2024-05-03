package com.example.myapplication.feature_note.domain.use_case

import javax.inject.Inject

data class NoteUseCases @Inject constructor(
    val insertNoteUseCase: InsertNoteUseCase,
    val deleteNote: DeleteNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val getAllNoteUseCase: GetAllNoteUseCase
)