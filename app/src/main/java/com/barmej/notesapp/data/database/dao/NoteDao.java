package com.barmej.notesapp.data.database.dao;

import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NoteCheckItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Query( "SELECT * FROM note WHERE id == :id" )
    LiveData<Note> getNote(int id);

    @Query( "SELECT * FROM note " )
    LiveData<List<Note>> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addNote(Note note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);



}
