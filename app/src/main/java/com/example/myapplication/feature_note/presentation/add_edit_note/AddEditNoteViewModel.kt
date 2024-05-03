package com.example.myapplication.feature_note.presentation.add_edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    var state by mutableStateOf(
        AddEditNotesState(
            title = NoteTextFieldState(),
            content = NoteTextFieldState(),
            color = Note.noteColors.random().toArgb()
        )
    )
        private set


}

