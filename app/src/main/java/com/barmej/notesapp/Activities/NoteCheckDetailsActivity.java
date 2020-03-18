package com.barmej.notesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.CheckBox;
import android.widget.EditText;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NoteCheckItem;

public class NoteCheckDetailsActivity extends AppCompatActivity {
    int position;

    //EditText variable
    private EditText mNoteCheckEditText;

    //CheckBox variable
    private CheckBox mNoteCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_note_check_details );

        //find views by id's
        mNoteCheckEditText = findViewById( R.id.checkNoteEditText);
        mNoteCheckBox = findViewById( R.id.checkNoteCheckBox);

        //Intent to receive notes that need to edit
        Intent intent = getIntent();
        Note note = (NoteCheckItem) intent.getSerializableExtra( "note_check_details");
        position = intent.getIntExtra( "note_check_position_key", 0 );
        String text = mNoteCheckEditText.getText().toString();
        mNoteCheckEditText.setText( note.getText() );
        boolean isChecked = mNoteCheckBox.isChecked();
    }

    // press back button to send results after editting
    public void onBackPressed() {
        Note note;
        String text = mNoteCheckEditText.getText().toString();
        boolean isChecked = mNoteCheckBox.isChecked();
        note = new NoteCheckItem(text, Constants.NOTE_CHECK_VIEW_TYPE, isChecked );
        Intent intent = new Intent();
        intent.putExtra(Constants.NOTE, note);
        intent.putExtra( "note_check_position_key", position );
        setResult(RESULT_OK, intent);
        finish();
    }
}
