package com.barmej.notesapp.data;


import java.io.Serializable;
//Super class Note
public class Note implements Serializable {

    //variables
    private String text;
    private int viewType;

    //Note constructor
    public Note(String text, int viewType) {
        this.text = text;
        this.viewType = viewType;
    }


//setter and getter methods
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
}
