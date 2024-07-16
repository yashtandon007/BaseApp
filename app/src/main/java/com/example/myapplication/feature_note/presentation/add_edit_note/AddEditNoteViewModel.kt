package com.example.myapplication.feature_note.presentation.add_edit_note
//top
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.feature_note.domain.model.Note
import com.example.myapplication.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    var state by mutableStateOf(
        AddEditNotesState(
            title = NoteTextFieldState(hint = "Title"),
            content = NoteTextFieldState(hint = "Note"),
            color = Note.noteColors.random().toArgb()
        )
    )
        private set

    fun onEvent(event: AddEditNotesEvent) {
        when (event) {
            is AddEditNotesEvent.ChangeColor -> {
                state = state.copy(
                    color = event.color
                )
            }

            is AddEditNotesEvent.EnterContent -> {
                state = state.copy(
                    content = state.content.copy(
                        text = event.content, isHintVisible = event.content.isBlank()
                    )
                )
            }

            is AddEditNotesEvent.EnterTitle -> {
                state = state.copy(
                    title = state.title.copy(
                        text = event.title, isHintVisible = event.title.isBlank()
                    )
                )
            }

            AddEditNotesEvent.SaveNote -> {

                viewModelScope.launch {
                    noteUseCases.insertNoteUseCase(
                        Note(
                            title = state.title.text,
                            content = state.content.text,
                            timeStamp = System.currentTimeMillis(),
                            color = state.color,
                            id = null
                        )
                    )
                }
            }
        }
    }



}


/**
 * 1,2,3,4,5,6,7
 * 7,6,5,4,3,2,1
 *5,6,7  1,2,3,4
 */
fun main() {

    val RedOrange = Color(0xffffab91)
    val RedPink = Color(0xfff48fb1)
    println(RedOrange.toArgb())
    println(RedPink.toArgb())
}




