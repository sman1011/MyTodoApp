package com.example.mytodoapp.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.room.model.NoteRepository
import com.example.mytodoapp.room.model.db.Note
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val allNotes = noteRepository.allNotes.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun create(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }

    /**
     * Launching a new coroutine to delete the data in a non-blocking way
     */
    fun delete(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }

}

