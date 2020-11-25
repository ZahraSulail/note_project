package com.barmej.notesapp.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.barmej.notesapp.Constants;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


public abstract class Note implements Parcelable, Comparable {
    /*
    Create a primary key for not table
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String text;

    private int viewType;

    private int backgroundColor;

    public Note() {

    }

    public Note(String text){
        this.text = text;
        this.viewType = Constants.NOTE_VIEW_TYPE;
    }

    @Ignore
    public Note(String text, int viewType){
        this.text = text;
        this.viewType = viewType;

    }


    //setter and getter methods

    protected Note(Parcel in) {
        id = in.readInt();
        text = in.readString();
        viewType = in.readInt();
        backgroundColor = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( id );
        dest.writeString( text );
        dest.writeInt( viewType );
        dest.writeInt( backgroundColor );
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public int compareTo(Object o) {
        return this.id > ((Note)o).getId() ? 1 : 0;
    }


}
