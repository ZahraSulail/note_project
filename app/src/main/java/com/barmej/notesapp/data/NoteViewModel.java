package com.barmej.notesapp.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * ViewModel class that hold data requests and temporary that survive configuration changes
 */
public class NoteViewModel extends AndroidViewModel {

    /**
     * An instance of NoteDataRepository for all data related operations
     */
    private NoteDataRepository mRepository;

    /**
     * LiveData object to wrap Note  data
     */
    private static LiveData<List<Note>> mNoteLiveData;

    /**
     * LiveData object to wrap NoteCheckItem data
     */
    private LiveData<List<NoteCheckItem>> mNoteCheckItemLiveData;

    /**
     * LiveData object to wrap NotePhotoItem  data
     */
    private LiveData<List<NotePhotoItem>> mNotePhotoItemLiveData;

    /**
     * ViewModel Constructor
     *
     * @param application An instance of application class
     */

    public NoteViewModel(@NonNull Application application) {
        super(application);

        // Get instance of NoteDataRepository
        mRepository = NoteDataRepository.getInstance(getApplication());

        // Request note  data from the repository class
        mNoteLiveData = mRepository.getAllNote();

        // Request noteCheckItem data from the repository class
        mNoteCheckItemLiveData = mRepository.getNoteCheckItem();

        // Request notePhotoItem data from the repository class
        mNotePhotoItemLiveData = mRepository.getNotePhotItem();

    }

    public void addNote(Note note) {
        mRepository.addNote( note );
    }
    public void updateNote(Note note){
        mRepository.updateNote( note );
    }

    public void addNoteCheck(NoteCheckItem noteCheckItem){
        mRepository.addNoteCheck( noteCheckItem );
    }

    public void updateNoteCheck(NoteCheckItem noteCheckItem){
        mRepository.updateNoteCheck( noteCheckItem );
    }

    public void addNotePhoto(NotePhotoItem notePhotoItem){
        mRepository.addNotePhoto( notePhotoItem );
    }
    public void updateNotePhoto(NotePhotoItem notePhotoItem){
        mRepository.updateNotePhoto( notePhotoItem );
    }


    /**
     * Get a handle of note LiveData object
     *
     * @return A wrapper LiveData object contains the note
     */
    public LiveData<List<Note>> getAllNoteLiveData() {
        return mNoteLiveData;
    }

    /**
     * Get a handle of noteCheckItem LiveData object
     *
     * @return A wrapper LiveData object contains the noteCheckItem
     */
    public LiveData<List<NoteCheckItem>> getAllNoteCheckItemLiveData() {
        return mNoteCheckItemLiveData;
    }

    /**
     * Get a handle of notePhotoItem LiveData object
     *
     * @return A wrapper LiveData object contains the notePhotoItem
     */
    public LiveData<List<NotePhotoItem>> getAllNotePhotoItemLiveData() {
        return mNotePhotoItemLiveData;
    }

    /*
     Get a single note in NoteDetailsActivity
     */
    public LiveData<Note> getNote(int id){
        return mRepository.getNote( id);
    }

    /*
     Get a single notePhotoItem in NotePhotoDetailsActivity
     */
    public LiveData<NoteCheckItem> getNoteCheckItem(int id){
        return mRepository.getSingleCheckNoteItem(id);
    }

    /*
    Get a single noteCheckItem in NoteCheckDetailsActivity
    */
    public LiveData<NotePhotoItem> getNotePhotoItem(int id) {
        return mRepository.getSinglePhotoNoteItem(id);
    }




}

