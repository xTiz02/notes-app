package com.prd.notesapp.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.prd.notesapp.ui.screens.BottomNavBar
import com.prd.notesapp.ui.screens.add.AddNoteScreen
import com.prd.notesapp.ui.screens.add.AddNoteTopBar
import com.prd.notesapp.ui.screens.detail.NoteDetailScreen
import com.prd.notesapp.ui.screens.detail.NoteDetailTopBar
import com.prd.notesapp.ui.screens.notes.NotesScreen
import com.prd.notesapp.ui.screens.notes.NotesTopBar
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prd.notesapp.ui.screens.add.AddNoteViewModel
import com.prd.notesapp.ui.screens.notes.NotesViewModel

sealed class Screen(val route: String, val title: String = "") {
    object Notes : Screen("notes", "Notes")
    object AddNote : Screen("add_note")
    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }

    object Todo : Screen("todo", "Todo")
}

@Composable
fun AppNavigation(
    notesViewModel: NotesViewModel = viewModel(factory = NotesViewModel.Factory),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    var showAboutDialog by remember { mutableStateOf(false) }

    val addNoteViewModel : AddNoteViewModel = viewModel(factory = AddNoteViewModel.Factory)

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        topBar = {
            when (currentRoute) {
                Screen.Notes.route -> NotesTopBar({}, { showAboutDialog = true })
                Screen.NoteDetail.route -> NoteDetailTopBar(onBack = { navController.popBackStack() })
                Screen.AddNote.route -> AddNoteTopBar(
                    addNoteViewModel = addNoteViewModel,
                    onBack = { navController.popBackStack() })
            }
        },
        floatingActionButton = {
            if (currentRoute == Screen.Notes.route) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddNote.route) },
                    shape = CircleShape,
                    contentColor = Color.White,
                    containerColor = Color(0xFFE3B529),
                    modifier = Modifier.size(65.dp)
                ) {
                    Text(
                        text = "+",
                        style = TextStyle(
                            fontSize = 55.sp,
                            fontWeight = FontWeight.Thin
                        )
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = modifier.padding(it)
        ) {
            composable(Screen.Notes.route) {
                val notes by notesViewModel.notes.collectAsState()

                NotesScreen(
                    notes = notes,
                    onNoteClick = { note ->
                        navController.navigate(
                            Screen.NoteDetail.createRoute(
                                note.id
                            )
                        )
                    }
                )
            }
            composable(Screen.AddNote.route) {
                AddNoteScreen(addNoteViewModel = addNoteViewModel)
            }
            composable(
                Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
                NoteDetailScreen()
            }
            composable(Screen.Todo.route) {
                Text("Todo Screen")
            }
        }
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("About app") },
            text = { Text("ComposeNotes v1.0.0\nDeveloped by ~ TheGenZDev") }
        )
    }


}