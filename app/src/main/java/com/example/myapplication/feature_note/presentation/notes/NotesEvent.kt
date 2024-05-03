package com.example.myapplication.feature_note.presentation.notes

sealed class NotesEvent{
    data object NavigateToAddScreen:NotesEvent()
}
