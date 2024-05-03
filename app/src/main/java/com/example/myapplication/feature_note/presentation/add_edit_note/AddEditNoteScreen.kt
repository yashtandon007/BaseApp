package com.example.myapplication.feature_note.presentation.add_edit_note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.feature_note.domain.model.Note

@Composable
fun AddEditNoteScreen(
    state: AddEditNotesState, onEvent: (AddEditNotesEvent) -> Unit
) {

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onEvent(AddEditNotesEvent.SaveNote)
        }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = "Save note")
        }
    }, content = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

        }
    })

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun AddEditNoteScreenPreview() {

    AddEditNoteScreen(
        AddEditNotesState(
            title = NoteTextFieldState(),
            content = NoteTextFieldState(),
            color = Note.noteColors.random().toArgb()
        )
    ) {

    }

}