package com.prd.notesapp.data.service

import com.prd.notesapp.data.model.Note
import com.prd.notesapp.data.repo.NoteRepository
import kotlinx.coroutines.flow.Flow

class NotesServiceImpl(val taskRepo : NoteRepository) : NotesService {
    override fun getAllNotes(): Flow<List<Note>> {
        return taskRepo.getAllNotes()
    }

    override fun getNoteById(id: Int): Flow<Note?> {
        return taskRepo.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        taskRepo.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        taskRepo.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        taskRepo.deleteNote(note)
    }
}