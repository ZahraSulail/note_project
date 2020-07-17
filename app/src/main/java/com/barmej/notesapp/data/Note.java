package com.barmej.notesapp.data;


import com.barmej.notesapp.Constants;

import java.io.Serializable;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
//Super class Note

@Entity(tableName = "note")
public class Note implements Serializable, Comparable {
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
