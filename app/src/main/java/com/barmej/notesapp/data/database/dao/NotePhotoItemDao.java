package com.barmej.notesapp.data.database.dao;

import com.barmej.notesapp.data.entities.NotePhotoItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface NotePhotoItemDao {

    @Query( "SELECT * FROM note_photo_item WHERE id == :id" )
    LiveData<NotePhotoItem> getNotePhotoItem(int id);

    @Query( "SELECT * FROM note_photo_item " )
    LiveData<List<NotePhotoItem>> getAllNotePhotoItem();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addNote(NotePhotoItem notePhotoItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(NotePhotoItem notePhotoItem);

    @Delete
    void deleteNote(NotePhotoItem notePhotoItem);
}
