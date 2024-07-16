package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.myapplication.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.example.myapplication.feature_note.presentation.add_edit_note.AddEditNotesEvent
import com.example.myapplication.feature_note.presentation.notes.NoteViewModel
import com.example.myapplication.feature_note.presentation.notes.NotesEvent
import com.example.myapplication.feature_note.presentation.notes.NotesScreen
import com.example.myapplication.feature_note.presentation.util.Screen
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

\//god in activity
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteScreen.route
                    ) {
                        composable(Screen.NoteScreen.route) {
                            val noteVm = hiltViewModel<NoteViewModel>()
                            NotesScreen(noteVm.state) { event->
                                when(event){
                                    NotesEvent.NavigateToAddScreen -> {
                                        navController.navigate(Screen.AddEditNoteScreen.route)
                                    }
                                }

                            }
                        }
                        composable(Screen.AddEditNoteScreen.route) {
                            val addEditNoteVm = hiltViewModel<AddEditNoteViewModel>()
                            AddEditNoteScreen(addEditNoteVm.state){ event->
                                addEditNoteVm.onEvent(event)
                            }
                        }
                    }
                }
            }
        }
    }
}





