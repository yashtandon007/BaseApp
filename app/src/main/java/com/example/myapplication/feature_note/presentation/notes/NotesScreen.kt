package com.example.myapplication.feature_note.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.presentation.components.NoteItem

@Composable
fun NotesScreen(
    state: NotesState, onEvent: (NotesEvent) -> Unit
) {
    Scaffold(

        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(NotesEvent.NavigateToAddScreen)
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Save note")
            }
        }, content = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(70.dp)
                            .align(Center)
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize()

                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(state.notes) { note ->
                                NoteItem(note)
                            }
                        }
                    }
                }
            }

        })

}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NotesScreenPreview() {
    NotesScreen(
        state = NotesState(
            notes = listOf(
                Note(null, "Note 1", "content", 1, -749647),
                Note(null, "Note 2", "content", 1, -21615)
            ), isLoading = false
        )
    ) {

    }
}