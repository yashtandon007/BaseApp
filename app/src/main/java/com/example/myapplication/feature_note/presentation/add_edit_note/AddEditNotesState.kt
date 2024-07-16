package com.example.myapplication.feature_note.presentation.add_edit_note

//a2
data class AddEditNotesState(
    val title: NoteTextFieldState,
    val content: NoteTextFieldState,
    val color: Int,
    val currentNoteId: Int? = null
)

