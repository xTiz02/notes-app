package com.prd.notesapp.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prd.notesapp.NotesApplication
import com.prd.notesapp.data.model.Note
import com.prd.notesapp.data.service.NotesService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val title: String = "",
    val content: String = "",
    val isLocked: Boolean = false,
    val errorMessage: String? = null
)

class DetailViewModel(
    private val notesService: NotesService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Int = checkNotNull(savedStateHandle["noteId"])

    private val _detailState = MutableStateFlow(DetailUiState())
    val detailState: StateFlow<DetailUiState> = _detailState.asStateFlow()

    init {
        // Carga la nota y rellena el UiState
        viewModelScope.launch {
            notesService.getNoteById(noteId).collect { note ->
                note?.let {
                    _detailState.update { state ->
                        state.copy(
                            title = it.title,
                            content = it.content,
                            isLocked = it.isLocked
                        )
                    }
                }
            }
        }
    }

    fun updateNote() {
        if (!validateInput()) {
            return
        }

        viewModelScope.launch {
            notesService.updateNote(
                Note(
                    id = noteId,
                    title = _detailState.value.title,
                    content = _detailState.value.content,
                    isLocked = _detailState.value.isLocked
                )
            )
        }
    }

    fun updateTitle(title: String) {
        _detailState.update { it.copy(title = title, errorMessage = null) }
    }

    fun updateContent(content: String) {
        _detailState.update { it.copy(content = content, errorMessage = null) }
    }

    fun deleteNote() {
        viewModelScope.launch {
            notesService.deleteNote(
                Note(
                    id = noteId,
                    title = _detailState.value.title,
                    content = _detailState.value.content,
                    isLocked = _detailState.value.isLocked
                )
            )
        }
    }

    private fun validateInput(): Boolean {
        val current = _detailState.value

        if (current.title.isBlank()) {
            _detailState.update { it.copy(errorMessage = "Title cannot be empty") }
            return false
        }
        if (current.content.isBlank()) {
            _detailState.update { it.copy(errorMessage = "Content cannot be empty") }
            return false
        }
        return true
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotesApplication)
                val notesService = application.container.notesService
                val savedStateHandle = createSavedStateHandle()
                DetailViewModel(
                    notesService = notesService,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}