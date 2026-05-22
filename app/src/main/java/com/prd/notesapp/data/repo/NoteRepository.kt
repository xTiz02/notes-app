package com.prd.notesapp.data.repo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.prd.notesapp.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteRepository {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int) : Flow<Note?>

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}