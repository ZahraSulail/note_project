package com.barmej.notesapp.data.database;

import android.content.Context;

import com.barmej.notesapp.data.entities.NoteCheckItem;
import com.barmej.notesapp.data.entities.NotePhotoItem;
import com.barmej.notesapp.data.entities.TextNote;

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
        public LiveData<TextNote> getNote(int id){
            // Get LiveData object from database using Room
            final LiveData<TextNote> noteLiveData = mAppDatatbase.noteDao().getNote(id);
            return noteLiveData;
        }

    public LiveData<List<TextNote>> getAllNote(){
        // Get LiveData object from database using Room
        final LiveData<List<TextNote>> allNotesLiveData = mAppDatatbase.noteDao().getAllNotes();
        return allNotesLiveData;
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
        return mAppDatatbase.notePhotoItemDao().getNotePhotoItem(id);
    }

   // Add Note to Room
    public void addNote(TextNote note) {
        mAppDatatbase.noteDao().addNote(note);
    }

    // Update Note
    public void updateNote(TextNote note){
        mAppDatatbase.noteDao().updateNote(note);
    }

    //Delete Note from Room
    public void deleteNote(TextNote note){
        mAppDatatbase.noteDao().deleteNote(note);

    }

    // Add NoteCheck to Room
    public void addNoteCheck(NoteCheckItem noteCheckItem){
        mAppDatatbase.noteCheckItemDao().addNote(noteCheckItem);
    }

    // Update NoteCheck
    public void updateNoteCheck(NoteCheckItem noteCheckItem){
        mAppDatatbase.noteCheckItemDao().updateNote(noteCheckItem);
    }

    //Delete NoteCheck from Room
    public void deleteNoteCheck(NoteCheckItem noteCheckItem){
        mAppDatatbase.noteCheckItemDao().deleteNote(noteCheckItem);
    }

    // Add NotePhoto to Room
    public void addNotePhoto(NotePhotoItem notePhotItemm){
        mAppDatatbase.notePhotoItemDao().addNote(notePhotItemm);
    }

    // Updatee NotePhoto
    public void updateNotePhoto(NotePhotoItem notePhotoItem){
        mAppDatatbase.notePhotoItemDao().updateNote(notePhotoItem);
    }

    //Delete NotePhoto from Room
    public void deleteNotePhoto(NotePhotoItem notePhotoItem){
        mAppDatatbase.notePhotoItemDao().deleteNote(notePhotoItem);
    }
}


