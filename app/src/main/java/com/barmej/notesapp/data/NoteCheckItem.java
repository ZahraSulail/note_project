package com.barmej.notesapp.data;



import com.barmej.notesapp.Constants;

import androidx.room.Entity;


//NoteCheckItem sub class iherts from Note super class
@Entity(tableName = "not_check_item")
public class NoteCheckItem extends Note {
    // boolaen variable

    private boolean isChecked;

    public NoteCheckItem(String text, int noteCheckViewType, boolean isChecked){

        super( text, Constants.NOTE_VIEW_TYPE );
    }

   //NoteCheckItem constructor
    public NoteCheckItem(String text, int viewType, boolean isChecked, int id) {
        super(text, viewType, viewType, id);
        this.setChecked( isChecked );


    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

