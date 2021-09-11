package com.example.mytodoapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.mytodoapp.databinding.ActivityMainBinding
import com.example.mytodoapp.room.NoteApplication
import com.example.mytodoapp.room.view.NotesFragment
import com.example.mytodoapp.room.view.SimpleDialogFragment
import com.example.mytodoapp.room.viewmodel.NoteViewModel
import com.example.mytodoapp.room.viewmodel.NoteViewModelFactory

private const val LOG_TAG = "MainActivity"
private const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportFragmentManager.findFragmentByTag(MAIN_FRAGMENT_TAG) == null) {
            Log.d(LOG_TAG, "room_fragment")
            supportFragmentManager.beginTransaction()
                .add(R.id.room_fragment, NotesFragment()).commit()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inform the system, that we want to show a menu
        menuInflater.inflate(R.menu.fragment_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_item -> {

                Log.d(LOG_TAG, "Add item")
                //Return true, if you handled the menu click
                val dialog = SimpleDialogFragment()
                dialog.show(supportFragmentManager, "SIMPLE_DIALOG_FRAGMENT_TAG")
                supportFragmentManager.popBackStack()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}