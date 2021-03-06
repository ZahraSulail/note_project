package com.barmej.notesapp.data.database;

import android.content.Context;

import com.barmej.notesapp.data.database.dao.NoteCheckItemDao;
import com.barmej.notesapp.data.database.dao.NoteDao;
import com.barmej.notesapp.data.database.dao.NotePhotoItemDao;
import com.barmej.notesapp.data.entities.NoteCheckItem;
import com.barmej.notesapp.data.entities.NotePhotoItem;
import com.barmej.notesapp.data.entities.TextNote;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = { TextNote.class, NoteCheckItem.class, NotePhotoItem.class}, version = 3, exportSchema = false)
@TypeConverters( {UriConverter.class} )
public abstract class AppDatabase extends RoomDatabase {


    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;
    private static final String DATABASE_NAME = "note_db";

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                if(sInstance == null){
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME) // TODO: remove on production
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract NoteDao noteDao();
    public abstract NoteCheckItemDao noteCheckItemDao();
    public abstract NotePhotoItemDao notePhotoItemDao();
}
