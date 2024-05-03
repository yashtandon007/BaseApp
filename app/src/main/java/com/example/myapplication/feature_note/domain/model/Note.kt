package com.example.myapplication.feature_note.domain.model

import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red


data class Note(
    val id:Int?,
    val title:String,
    val content:String,
    val timeStamp:Long,
    val color:Int
){
    companion object{
        val noteColors = listOf(Red, Green)
    }
}
class InValidNoteException(message:String) : Exception(message)