package com.prd.notesapp.ui.screens.add

import android.R.attr.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.prd.notesapp.data.model.Note
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddNoteScreen(
    addNoteViewModel: AddNoteViewModel
) {
    val addNoteUiState by addNoteViewModel.addNoteUiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = addNoteUiState.title,
            onValueChange = { addNoteViewModel.updateTitle(it) },
            placeholder = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = addNoteUiState.content,
            onValueChange = { addNoteViewModel.updateContent(it) },
            placeholder = { Text("Write your note...") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxLines = Int.MAX_VALUE,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
//        LockToggle(isLocked = isLocked, onToggle = { isLocked = it })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteTopBar(
    addNoteViewModel: AddNoteViewModel,
    onBack: () -> Unit = { }
) {
    val addNoteUiState by addNoteViewModel.addNoteUiState.collectAsState()

    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(
                onClick = {
                    if (addNoteUiState.title.isNotBlank() || addNoteUiState.content.isNotBlank()) {
                        val newNote = Note(
                            title = addNoteUiState.title,
                            content = addNoteUiState.content,
                            isLocked = addNoteUiState.isLocked
                        )
                        val success = addNoteViewModel.addNote((newNote))
                        if (success) {
                            onBack()
                        }
                    }
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        }
    )
}