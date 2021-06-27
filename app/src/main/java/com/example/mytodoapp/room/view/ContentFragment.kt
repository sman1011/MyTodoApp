package com.example.mytodoapp.room.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.mytodoapp.MainActivity

import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.FragmentContentBinding
import com.example.mytodoapp.room.NoteApplication
import com.example.mytodoapp.room.model.db.Note
import com.example.mytodoapp.room.viewmodel.NoteViewModel
import com.example.mytodoapp.room.viewmodel.NoteViewModelFactory

private const val LOG_TAG = "ContentFragment"
private const val CONTENT_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
private var noteItem = "0"
private var noteTitle = ""
private var noteContent = ""
private var noteTime = ""

class ContentFragment: Fragment()  {
    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!


    private val noteViewModel: NoteViewModel by viewModels {
        //This code will only be called, when the fragment is already attached to the activity.
        //therefore we can safely call requireActivity()
        val application: NoteApplication = requireActivity().application as NoteApplication
        NoteViewModelFactory(application.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LOG_TAG, "Fragment Content: onCreate")
        //Fragment wants to be notified when Menu items have been clicked
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(LOG_TAG, "Fragment Notes: onCreateView")
        _binding = FragmentContentBinding.inflate(inflater, container, false)

        val mainActivityView = (activity as MainActivity)
        mainActivityView.supportFragmentManager
            .setFragmentResultListener("REQUEST", this) { requestKey, bundle ->

                noteItem = bundle.getString("NOTE").toString()
                noteTitle = bundle.getString("TITLE").toString()
                noteContent = bundle.getString("CONTENT").toString()
                noteTime = bundle.getString("TIME").toString()

                Log.d(LOG_TAG, "Result Title: $noteItem")
 //               Log.d(LOG_TAG, "Result Content: $resultContent")
                binding.textInfo.text = noteTitle
                binding.textInfo1.text = noteContent
                binding.textTime.text = noteTime

            }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.delete_item -> {

                Log.d(LOG_TAG, "Delete item")
                noteViewModel.delete(Note(noteItem.toLong(), noteTitle, noteContent, noteTime))
                val mainActivityView = (activity as MainActivity)
                mainActivityView.supportFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
    }

}
