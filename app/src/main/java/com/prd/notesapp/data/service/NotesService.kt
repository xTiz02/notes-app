package com.prd.notesapp.data.service

import com.prd.notesapp.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesService {
    fun getAllNotes() : Flow<List<Note>>

    fun getNoteById(id: Int) : Flow<Note?>

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)
}