package com.barmej.notesapp.data.database.dao;

import com.barmej.notesapp.data.entities.TextNote;

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
    LiveData<TextNote> getNote(int id);

    @Query( "SELECT * FROM note " )
    LiveData<List<TextNote>> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addNote(TextNote note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(TextNote note);

    @Delete
    void deleteNote(TextNote note);



}
