package com.barmej.notesapp.data.entities;

import android.os.Parcel;

import androidx.room.Entity;

@Entity(tableName = "note")
public class TextNote extends Note {

    public static final Creator<TextNote> CREATOR = new Creator<TextNote>() {
        @Override
        public TextNote createFromParcel(Parcel in) {
            return new TextNote( in );
        }

        @Override
        public TextNote[] newArray(int size) {
            return new TextNote[size];
        }
    };

    public TextNote(Parcel in) {
        super(in);
    }

    public TextNote() {
        super();
    }

    public TextNote(String text){
        super(text);
    }

}
