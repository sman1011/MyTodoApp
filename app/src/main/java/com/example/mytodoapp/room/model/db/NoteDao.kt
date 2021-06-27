package com.example.mytodoapp.room.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Marks the class as a Data Access Object.
 * <p>
 * Data Access Objects are the main classes where you define your database interactions. They can
 * include a variety of query methods.
 * <p>
 * The class marked with {@code @Dao} should either be an interface or an abstract class. At compile
 * time, Room will generate an implementation of this class when it is referenced by a
 * {@link Database}.
 * <p>
 * An abstract {@code @Dao} class can optionally have a constructor that takes a {@link Database}
 * as its only parameter.
 * <p>
 * It is recommended to have multiple {@code Dao} classes in your codebase depending on the tables
 * they touch.
 *
 * @see Query
 * @see Delete
 * @see Insert
 */
@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>

    @Delete
    suspend fun delete(note: Note)
}

