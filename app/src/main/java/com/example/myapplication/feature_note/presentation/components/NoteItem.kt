package com.example.myapplication.feature_note.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.feature_note.domain.model.Note


@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier
) {

    Box(
        modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(Color(note.color))
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@Preview
@Composable
private fun NoteItemPrev() {
    NoteItem(note = Note(
        title = "title",
        content = "content",
        color = -749647,
        timeStamp = 1,
        id = 1
    ))
}