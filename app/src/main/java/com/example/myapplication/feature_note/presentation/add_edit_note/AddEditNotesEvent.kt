package com.example.myapplication.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState


sealed class AddEditNotesEvent{
    data object SaveNote:AddEditNotesEvent()
    data class EnterTitle(val title: String):AddEditNotesEvent()
    data class EnterContent(val content: String):AddEditNotesEvent()
    data class ChangeColor(val color: Int):AddEditNotesEvent()
}
//par1