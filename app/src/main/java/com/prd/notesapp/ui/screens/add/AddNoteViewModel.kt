package com.prd.notesapp.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prd.notesapp.NotesApplication
import com.prd.notesapp.data.model.Note
import com.prd.notesapp.data.service.NotesService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AddNoteUiState(
    val title: String = "",
    val content: String = "",
    val errorMessage: String? = null,
    val isLocked: Boolean = false
)
class AddNoteViewModel(
    private val notesService: NotesService
) : ViewModel() {

    private val _addNoteUiState = MutableStateFlow(AddNoteUiState())
    val addNoteUiState: StateFlow<AddNoteUiState> = _addNoteUiState.asStateFlow()

    fun addNote(note: Note) : Boolean {
        if(!validateInput()) {
            return false
        }
        viewModelScope.launch {
            notesService.insertNote(note)
        }
        return true
    }

    fun updateTitle(title: String) {
        _addNoteUiState.update { currentState ->
            currentState.copy(title = title, errorMessage = null)
        }
    }

    fun updateContent(content: String) {
        _addNoteUiState.update { currentState ->
            currentState.copy(content = content, errorMessage = null)
        }
    }

    fun validateInput(): Boolean {
        val currentState = addNoteUiState.value
        var isValid = true

        if (currentState.title.isBlank()) {
            _addNoteUiState.value = currentState.copy(errorMessage = "Title cannot be empty")
            isValid = false
        }

        if (currentState.content.isBlank()) {
            _addNoteUiState.value = currentState.copy(errorMessage = "Content cannot be empty")
            isValid = false
        }

        // Add validation

        return isValid
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)
                val notesService = application.container.notesService
                AddNoteViewModel(notesService = notesService)
            }
        }
    }
}

