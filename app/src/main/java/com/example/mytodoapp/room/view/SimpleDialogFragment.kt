package com.example.mytodoapp.room.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mytodoapp.R

import com.example.mytodoapp.databinding.FragmentSimpleDialogBinding
import com.example.mytodoapp.room.NoteApplication
import com.example.mytodoapp.room.model.db.Note

import com.example.mytodoapp.room.viewmodel.NoteViewModel
import com.example.mytodoapp.room.viewmodel.NoteViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val LOG_TAG = "SimpleDialogFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [SimpleDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimpleDialogFragment : DialogFragment() {
    //Hold binding class of layout fragment_complex_dialog.xml
    private val noteViewModel: NoteViewModel by viewModels {
        //This code will only be called, when the fragment is already attached to the activity.
        //therefore we can safely call requireActivity()
        val application: NoteApplication = requireActivity().application as NoteApplication
        NoteViewModelFactory(application.repository)
    }

    private var _binding: FragmentSimpleDialogBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        Log.d(LOG_TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate")
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(LOG_TAG, "onCreateDialog")

        //Create the builder, which builds the parts of the dialog.
        //requireActivity() to apply the activity theme to the dialog
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireActivity())

        //With View Binding
        _binding = FragmentSimpleDialogBinding.inflate(requireActivity().layoutInflater)

        binding.dialogTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(LOG_TAG, "After EditText text changed : $s")
            }
        })

        //Add the needed parts of the dialog
        builder
            .setTitle(R.string.new_note)
            .setView(binding.root) //Setting the custom layout instead of a message
            .setPositiveButton(R.string.dialog_fragment_positive_text) {

                    dialog, id ->
                Log.d(LOG_TAG, "Positive button clicked")
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date()).toString()
                addNote(Note(0, "${binding.dialogTitle.text}",
                    "${binding.dialogContent.text}",
                    currentDate
                ))
            }
            .setNegativeButton(R.string.dialog_fragment_negative_text) { dialog, id ->
                Log.d(LOG_TAG, "Negative button clicked")

            }

        //Create the dialog with all the chosen parts
        //Will be returned since this is the last statement
        return builder.create()
    }

    private fun addNote(note: Note) {
        Log.d(LOG_TAG, "Added note ${note.id}")
        noteViewModel.create(note)
    }

}
