package com.barmej.notesapp.data.database.dao;

import com.barmej.notesapp.data.entities.NoteCheckItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface NoteCheckItemDao {

   @Query( "SELECT * FROM not_check_item WHERE id == :id")
    LiveData<NoteCheckItem> getNoteCheckItem(int id);

 @Query( "SELECT * FROM not_check_item ")
   LiveData<List<NoteCheckItem>> getAllNoteCheckItem();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addNote(NoteCheckItem noteCheckItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(NoteCheckItem noteCheckItem);

    @Delete
    void deleteNote(NoteCheckItem noteCheckIte);
}
