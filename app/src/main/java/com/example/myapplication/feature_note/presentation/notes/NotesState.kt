package com.example.myapplication.feature_note.presentation.notes

import com.example.myapplication.feature_note.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true
)
