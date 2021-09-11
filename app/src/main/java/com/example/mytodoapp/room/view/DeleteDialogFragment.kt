package com.example.mytodoapp.room.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mytodoapp.MainActivity
import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.FragmentDeleteDialogBinding
import com.example.mytodoapp.databinding.FragmentSimpleDialogBinding
import com.example.mytodoapp.room.NoteApplication
import com.example.mytodoapp.room.model.db.Note
import com.example.mytodoapp.room.viewmodel.NoteViewModel
import com.example.mytodoapp.room.viewmodel.NoteViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val LOG_TAG = "DeleteDialogFragment"
private const val NOTE = "NOTE"
private const val TITLE = "TITLE"
private const val CONTENT = "CONTENT"
private const val TIME = "TIME"

private const val DELETE = "DELETE"


class DeleteDialogFragment : DialogFragment() {

    private val noteViewModel: NoteViewModel by viewModels {
        //This code will only be called, when the fragment is already attached to the activity.
        //therefore we can safely call requireActivity()
        val application: NoteApplication = requireActivity().application as NoteApplication
        NoteViewModelFactory(application.repository)
    }
    private var noteItem:String? = null
    private var noteTitle:String? = null
    private var noteContent:String? = null
    private var noteTime:String? = null


    private var _binding: FragmentDeleteDialogBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        Log.d(LOG_TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(LOG_TAG, "onCreateDialog")

        val builder = androidx.appcompat.app.AlertDialog.Builder(requireActivity())

        _binding = FragmentDeleteDialogBinding.inflate(requireActivity().layoutInflater)
        val mainActivityView = (activity as MainActivity)
        mainActivityView.supportFragmentManager
            .setFragmentResultListener(DELETE, this) { requestKey, bundle ->

                noteItem = bundle.getString(NOTE).toString()
                noteTitle = bundle.getString(TITLE).toString()
                noteContent = bundle.getString(CONTENT).toString()
                noteTime = bundle.getString(TIME).toString()

            }

        //Create the builder, which builds the parts of the dialog.
        //requireActivity() to apply the activity theme to the dialog

        //Add the needed parts of the dialog
        builder
            .setTitle(R.string.delete_note)
            .setView(binding.root)
            .setMessage(R.string.delete_message)
            .setPositiveButton(R.string.dialog_fragment_positive_text) { dialog, id ->
                Log.d(LOG_TAG, "Positive button clicked")
                noteItem?.let {
                    Note(
                        it.toLong(),
                        noteTitle.toString(),
                        noteContent.toString(),
                        noteTime.toString())
                }?.let { noteViewModel.delete(it) }
                Log.d(LOG_TAG, noteItem.toString())
                val mainActivityView = (activity as MainActivity)
                mainActivityView.supportFragmentManager.popBackStack()

            }
            .setNegativeButton(R.string.dialog_fragment_negative_text) { dialog, id ->
                Log.d(LOG_TAG, "Negative button clicked")

            }

        //Create the dialog with all the chosen parts
        //Will be returned since this is the last statement
        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}