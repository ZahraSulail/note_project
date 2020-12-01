package com.barmej.notesapp.data.entities;


import android.os.Parcel;

import com.barmej.notesapp.Constants;

import androidx.room.Entity;


//NoteCheckItem sub class iherts from Note super class
@Entity(tableName = "not_check_item")
public class NoteCheckItem extends Note {
    // boolaen variable

    private boolean isChecked;

    public NoteCheckItem(){
        super();
    }

    public NoteCheckItem(String text, boolean isChecked){
        super( text, Constants.NOTE_CHECK_VIEW_TYPE );
        this.isChecked = isChecked;
    }

    protected NoteCheckItem(Parcel in) {
        super(in);
        isChecked = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel( dest, flags );
        dest.writeInt( isChecked ? 1: 0 );
    }

    public static final Creator<NoteCheckItem> CREATOR = new Creator<NoteCheckItem>() {
        @Override
        public NoteCheckItem createFromParcel(Parcel in) {
            return new NoteCheckItem( in );
        }

        @Override
        public NoteCheckItem[] newArray(int size) {
            return new NoteCheckItem[size];
        }
    };




    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}

