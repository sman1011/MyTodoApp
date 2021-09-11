package com.example.mytodoapp.room.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.view.menu.MenuView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
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

private const val NOTE = "NOTE"
private const val TITLE = "TITLE"
private const val CONTENT = "CONTENT"
private const val TIME = "TIME"

private const val DELETE = "DELETE"
private const val REQUEST = "REQUEST"

private var noteItem:String? = null
private var noteTitle:String? = null
private var noteContent:String? = null
private var noteTime:String? = null

class ContentFragment: Fragment()  {
    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!

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
            .setFragmentResultListener(REQUEST, this) { requestKey, bundle ->

                noteItem = bundle.getString(NOTE).toString()
                noteTitle = bundle.getString(TITLE).toString()
                noteContent = bundle.getString(CONTENT).toString()
                noteTime = bundle.getString(TIME).toString()

                Log.d(LOG_TAG, "Result Title: $noteItem")
                binding.textInfo.text = noteTitle
                binding.textInfo1.text = noteContent
                binding.textTime.text = noteTime
                setFragmentResult(
                    DELETE,
                    bundleOf(NOTE to noteItem,
                        TITLE to noteTitle,
                        CONTENT to noteContent,
                        TIME to noteTime)
                )

            }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.delete_item -> {

                Log.d(LOG_TAG, "Delete item")
                val dialog = DeleteDialogFragment()
                dialog.show(childFragmentManager, "DELETE_DIALOG_FRAGMENT_TAG")
//                noteViewModel.delete(Note(noteItem.toLong(), noteTitle, noteContent, noteTime))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
