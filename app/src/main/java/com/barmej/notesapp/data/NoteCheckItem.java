package com.barmej.notesapp.data;



import com.barmej.notesapp.Constants;

import androidx.room.Entity;


//NoteCheckItem sub class iherts from Note super class
@Entity(tableName = "not_check_item")
public class NoteCheckItem extends Note {
    // boolaen variable

    private boolean isChecked;

    public NoteCheckItem(){

    }

    public NoteCheckItem(String text, boolean isChecked){
        super( text, Constants.NOTE_CHECK_VIEW_TYPE );
        this.isChecked = isChecked;
    }



    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

