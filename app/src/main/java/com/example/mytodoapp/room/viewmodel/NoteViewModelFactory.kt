package com.example.mytodoapp.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodoapp.room.model.NoteRepository

/**
 * A ViewModelFactory is needed, when we want to pass arguments to a ViewModel.
 * Like with other components (e.g. Activities), by default the system instantiates the ViewModel,
 * thus it would need an empty constructor. If the ViewModel has arguments, we need to tell
 * the system how to instantiate the ViewModel. We do this vie the factory.
 */
class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}