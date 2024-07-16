package com.example.myapplication.feature_note.presentation.add_edit_note


data class AddEditNotesState(
    val title: NoteTextFieldState,
    val content: NoteTextFieldState,
    val color: Int,//f mext
    val currentNoteId: Int? = null
)
//dev2
