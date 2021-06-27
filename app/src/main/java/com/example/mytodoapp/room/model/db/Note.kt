package com.example.mytodoapp.room.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long,
    //Just to show how to use a different column name
    @ColumnInfo(name = "note_title") val noteTitle: String,
    @ColumnInfo(name = "content") val noteContent: String,
    @ColumnInfo(name = "time") val createTime: String
)