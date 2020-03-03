package com.barmej.notesapp.data;


//NoteCheckItem sub class iherts from Note super class
public class NoteCheckItem extends Note {
    // boolaen variable
    private boolean isCheced;

    //NoteCheckItem constructor
    public NoteCheckItem(String text, int viewType, boolean isCheced) {
        super(text, viewType);
        this.isCheced = isCheced;
    }

    //setter and getter methods
    public void setCheced(boolean checed) {
        isCheced = checed;
    }

    public boolean isCheced() {
        return isCheced;
    }


}

