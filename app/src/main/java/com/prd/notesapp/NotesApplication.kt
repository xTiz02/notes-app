package com.prd.notesapp

import android.app.Application
import com.prd.notesapp.data.AppContainer
import com.prd.notesapp.data.DefaultAppContainer

class NotesApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}