package com.example.myapplication.feature_note.presentation.add_edit_note

import com.google.android.gms.common.internal.Objects

sealed class AddEditNotesEvent{
    data object SaveNote:AddEditNotesEvent()
    data object RestoreNote:AddEditNotesEvent()
}
