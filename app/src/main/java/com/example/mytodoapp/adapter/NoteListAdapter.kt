package com.example.mytodoapp.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.R
import com.example.mytodoapp.room.model.db.Note
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val LOG_TAG = "NoteListAdapter"

class NoteListAdapter(private val clickListener: (Note) -> Unit) :
    ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffCallback) {


    class NoteViewHolder(
        itemView: View,
        private val clickListener: (Note) -> Unit,
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var currentNote: Note? = null
        private var noteText: TextView = itemView.findViewById(R.id.list_item_text)


        init {
            itemView.setOnClickListener(this)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun bind(note: Note) {
            currentNote = note
            noteText.text = note.noteTitle
        }

        override fun onClick(view: View) {
            currentNote?.let {
                clickListener(it)
            }
        }
        //Just for the print
        fun getNoteId(): Long {
            return currentNote?.id ?: -1L
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        Log.d(LOG_TAG, "Creating view holder")
        val listItemRoot: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return NoteViewHolder(listItemRoot, clickListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        Log.d(LOG_TAG, "Binding view holder")
        if (holder.getNoteId() != -1L) {
            Log.d(LOG_TAG, "Recycling holder, which was used for note #${holder.getNoteId()}, to show now note #${getItem(position).id}")
        }
        holder.bind(getItem(position))
    }
}

object NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}