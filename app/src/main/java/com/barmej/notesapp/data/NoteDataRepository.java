package com.barmej.notesapp.data;

import android.content.Context;

import com.barmej.notesapp.data.database.AppDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteDataRepository {
    private static final String TAG = NoteDataRepository.class.getSimpleName();

    /*
    Object used for the purpose of synchronize lock
     */
    private static final Object LOCK = new Object();

    /*
    Instance of this class for singlton
    */
    private static NoteDataRepository sInstance;

    /*
    Instanc of APPDatabase to perform datatbase operations
     */
    private AppDatabase mAppDatatbase;

    /**
     * @param context Context to use for some initializations
     */
    private NoteDataRepository(Context context){
        mAppDatatbase = AppDatabase.getInstance( context );
    }

        /**
         * Method used to get an instance of NoteDataRepository class
         *
         * @param context Context to use for some initializations
         * @return an instance of NoteDataRepository class
         */
        public static NoteDataRepository getInstance(Context context){
            if(sInstance == null){
                synchronized (LOCK){
                    if(sInstance == null)
                        sInstance = new NoteDataRepository(context.getApplicationContext());
                }
            }

            return sInstance;
        }

          /*
          @return LiveData object to be notified when data changed
           */
        public LiveData<Note> getNote(int id){
            // Get LiveData object from database using Room
            final LiveData<Note> noteLiveData = mAppDatatbase.noteDao().getNote(id);
            return noteLiveData;
        }

    public LiveData<List<Note>> getAllNote(){
        // Get LiveData object from database using Room
        final LiveData<List<Note>> noteLiveData = mAppDatatbase.noteDao().getAllNotes();
        return noteLiveData;
    }

        /*
        Empty note table and save new note to database
         */

        private void updateNote(final Note note){
            mAppDatatbase.noteDao().updateNote( note );
        }

         /*
          Get note_check_item data
         */
         public LiveData<List<NoteCheckItem>> getNoteCheckItem(){
             // Get LiveData object from database using Room
             final LiveData<List<NoteCheckItem>> noteCheckItemLiveData = mAppDatatbase.noteCheckItemDao().getAllNoteCheckItem();
             return noteCheckItemLiveData;
         }

      /*
       Get note_photo_item data
       */
     public LiveData<List<NotePhotoItem>> getNotePhotItem(){
        // Get LiveData object from database using Room
        final LiveData<List<NotePhotoItem>> notePhotoLiveData = mAppDatatbase.notePhotoItemDao().getAllNotePhotoItem();
        return notePhotoLiveData;
     }

        /*
        Empty note_check_item table and save new note_check_itemi to database
         */
        private void updateNoteCheckItem(final NoteCheckItem noteCheckItem){
            mAppDatatbase.noteCheckItemDao().updateNote( noteCheckItem);

        }

    /*
    Empty note_check_item table and save new note_check_itemi to database
     */
    private void updateNotePhotItem(final NotePhotoItem notePhotoItem){
        mAppDatatbase.notePhotoItemDao().updateNote( notePhotoItem );

    }

    public LiveData<NoteCheckItem> getSingleCheckNoteItem(int id){
        return mAppDatatbase.noteCheckItemDao().getNoteCheckItem( id );
    }

    public LiveData<NotePhotoItem> getSinglePhotoNoteItem(int id) {
        return mAppDatatbase.notePhotoItemDao().getNotePhotoItem( id );
    }
}


