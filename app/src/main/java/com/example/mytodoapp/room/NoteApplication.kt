package com.example.mytodoapp.room

import android.app.Application
import com.example.mytodoapp.room.model.NoteRepository
import com.example.mytodoapp.room.model.db.NoteDatabase

/**
 * Base class for maintaining global application state.
 * Needs to be registered in the Manifest (android:name=".room.NoteApplication").
 * NoteApplication is instantiated before any other class when the process for your application/package is created.
 * Here we instantiate classes of which we only want to have one instance.
 * Normally you would use a dependency injection framework like Hilt for this, but that would be an overkill.
 */
class NoteApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}