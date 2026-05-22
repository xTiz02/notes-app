package com.prd.notesapp.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prd.notesapp.data.model.Note
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle

@Composable
fun NoteDetailScreen(
    detailViewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory),
) {
    val detailUiState by detailViewModel.detailState.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        HorizontalDivider()
        OutlinedTextField(
            value = detailUiState.title,
            onValueChange = { detailViewModel.updateTitle(it) },
            placeholder = { Text("Title", style = TextStyle(fontSize = 35.sp)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 35.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = detailUiState.content,
            onValueChange = { detailViewModel.updateContent(it) },
            placeholder = { Text("Write your note...") },
            modifier = Modifier.fillMaxSize(),
            maxLines = Int.MAX_VALUE,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 20.sp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailTopBar(
    onBack: () -> Unit = { },
) {
//    val detailViewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory)

    TopAppBar(
        title = { },
        navigationIcon = {
            val scope = rememberCoroutineScope()
            IconButton(onClick = {
                scope.launch {
                    onBack()
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            val scope = rememberCoroutineScope()

            IconButton(onClick = {
                scope.launch {
//                    detailViewModel.updateNote()
                    delay(800)
                    onBack()
                }
            }) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
            IconButton(onClick = {
//                detailViewModel.deleteNote()
                onBack()
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        },
        windowInsets = WindowInsets.statusBars
    )
}