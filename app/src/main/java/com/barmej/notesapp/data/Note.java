package com.barmej.notesapp.data;


import java.io.Serializable;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
//Super class Note

@Entity(tableName = "note")
public class Note extends LiveData<Note> implements Serializable, Comparable {
    /*
    Create a primary key for not table
     */
   @PrimaryKey(autoGenerate = true)
    private int id;

    private String text;

    private int viewType;

    private int backgroundColor;

public Note(){

}

    //Note constructor
    @Ignore
    public Note(String text, int i, int type, int viewType) {
        this.text = text;
        this.viewType = viewType;
        this.id = id;
    }

    public Note(String text, int noteViewType){
        this.text = text;
        this.viewType = viewType;
    }


//setter and getter methods

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
