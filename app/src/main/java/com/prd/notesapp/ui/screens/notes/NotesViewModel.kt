package com.prd.notesapp.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prd.notesapp.NotesApplication
import com.prd.notesapp.data.model.Note
import com.prd.notesapp.data.service.NotesService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(
    private val notesService: NotesService
) : ViewModel() {

    val notes: StateFlow<List<Note>> = notesService.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as NotesApplication)
                val notesService = application.container.notesService
                NotesViewModel(notesService = notesService)
            }
        }
    }
}