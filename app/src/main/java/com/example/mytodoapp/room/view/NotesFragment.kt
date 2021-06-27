package com.example.mytodoapp.room.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodoapp.MainActivity
import com.example.mytodoapp.R
import com.example.mytodoapp.adapter.NoteListAdapter
import com.example.mytodoapp.databinding.FragmentNotesBinding
import com.example.mytodoapp.room.NoteApplication
import com.example.mytodoapp.room.model.db.Note
import com.example.mytodoapp.room.viewmodel.NoteViewModel
import com.example.mytodoapp.room.viewmodel.NoteViewModelFactory


private const val LOG_TAG = "NotesFragment"
private const val NOTES_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"

class NotesFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels {
        //This code will only be called, when the fragment is already attached to the activity.
        //therefore we can safely call requireActivity()
        val application: NoteApplication = requireActivity().application as NoteApplication
        NoteViewModelFactory(application.repository)
    }

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val listAdapter = NoteListAdapter(::onClick)
    private var counter = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "Fragment Notes: onCreate")
        //Fragment wants to be notified when Menu items have been clicked
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(LOG_TAG, "Fragment Notes: onCreateView")
        _binding = FragmentNotesBinding.inflate(inflater)

        with(binding.notesList) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            //For performance optimization
            setHasFixedSize(true)
        }

        noteViewModel.allNotes.observe(viewLifecycleOwner) { list ->
            listAdapter.submitList(list.sortedByDescending {
                it.createTime
            })
            Log.d(LOG_TAG, "Fragment Notes: $list")
        }

        return binding.root
    }

    @SuppressLint("ResourceType")
    private fun onClick(note: Note) {
        Log.d(LOG_TAG, "Clicked note ${note.id}")
        setFragmentResult(
            "REQUEST",
            bundleOf("NOTE" to note.id.toString(),
            "TITLE" to note.noteTitle,
            "CONTENT" to note.noteContent,
            "TIME" to note.createTime)
        )

        val mainActivityView = (activity as MainActivity)
        mainActivityView.supportFragmentManager.commit{
            Log.d(LOG_TAG, "supportFragmentManager")
            replace(R.id.room_fragment, ContentFragment(), NOTES_FRAGMENT_TAG)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}
