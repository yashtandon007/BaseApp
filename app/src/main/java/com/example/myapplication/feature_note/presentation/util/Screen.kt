package com.example.myapplication.feature_note.presentation.util

sealed class Screen(
    val route: String
) {
    data object NoteScreen : Screen("note_screen")
    data object AddEditNoteScreen : Screen("add_edit_note_screen")
}