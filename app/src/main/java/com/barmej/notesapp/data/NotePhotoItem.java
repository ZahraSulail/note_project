package com.barmej.notesapp.data;


import android.graphics.drawable.Drawable;

//NotePhotoItem sub class inherts from Note super class
public class NotePhotoItem extends Note {

    //int varaible to get photo resource id
    private int imageResId;

    //NotePhotoItem constructor
    public NotePhotoItem(String text, int viewType, int imageResId) {
        super(text, viewType);
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
