package com.prd.notesapp.data

import android.content.Context
import com.prd.notesapp.data.database.NoteDatabase
import com.prd.notesapp.data.service.NotesService
import com.prd.notesapp.data.service.NotesServiceImpl


interface AppContainer {
    val notesService : NotesService
}
class DefaultAppContainer(private val context: Context) : AppContainer  {
    override val notesService: NotesService by lazy {
        NotesServiceImpl(NoteDatabase.getDatabase(context).notesDao())
    }
}