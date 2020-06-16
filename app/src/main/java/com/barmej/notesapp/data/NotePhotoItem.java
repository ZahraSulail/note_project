package com.barmej.notesapp.data;


import com.barmej.notesapp.Constants;

import androidx.room.Entity;

//NotePhotoItem sub class inherts from Note super class
@Entity(tableName = "note_photo_item")
public class NotePhotoItem extends Note {

    //int varaible to get photo resource id
     private int imageResId;

     public NotePhotoItem(String text, int note_photoViewType, int photo){

         super( text, Constants.NOTE_VIEW_TYPE );
     }

     //NotePhotoItem constructor
    public NotePhotoItem(String text, int viewType, int imageResId, int id) {
        super(text, viewType, viewType, id);
        this.imageResId = imageResId;

    }

    //setter and getter method
    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
