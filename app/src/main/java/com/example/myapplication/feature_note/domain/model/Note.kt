package com.example.myapplication.feature_note.domain.model

import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import com.example.myapplication.ui.theme.Pink80
import com.example.myapplication.ui.theme.Purple80
import com.example.myapplication.ui.theme.*


data class Note(
    val id:Int?,
    val title:String,
    val content:String,
    val timeStamp:Long,
    val color:Int
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
class InValidNoteException(message:String) : Exception(message)