//lcckpackage com.example.myapplication.feature_note.presentation.add_edit_note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.presentation.components.TransparentHintTextField

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
                .background(Color(state.color))
                .padding(it)
                .padding(16.dp)
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    Box(modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = 3.dp,
                            color = if (state.color == color.toArgb()) {
                            Color.Black
                            } else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            onEvent(AddEditNotesEvent.ChangeColor(color.toArgb()))
                        }
                    ) {

                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.title.text,
                hint = state.title.hint,
                isHintVisible = state.title.isHintVisible,
                textStyle = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    onEvent(AddEditNotesEvent.EnterTitle(it))
                })
            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.content.text,
                hint = state.content.hint,
                isHintVisible = state.content.isHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                onValueChange = {
                    onEvent(AddEditNotesEvent.EnterContent(it))
                })
        }
    })

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun AddEditNoteScreenPreview() {

    AddEditNoteScreen(
        AddEditNotesState(
            title = NoteTextFieldState(hint = "Title"),
            content = NoteTextFieldState(hint = "Note"),
            color = Note.noteColors[0].toArgb()
        )
    ) {

    }

}