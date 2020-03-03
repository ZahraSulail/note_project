package com.barmej.notesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    //EditText variable
    private EditText mNoteEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_note_details );

        //find view by id
        mNoteEditText = findViewById( R.id.noteEditText );

        //Intent to receive notes that need to edit
        Intent intent = getIntent();
        Note note= (Note) intent.getSerializableExtra( "note_details" );
        String text = mNoteEditText.getText().toString();
        mNoteEditText.setText(note.getText());

    }
    // press back button to send results after editting
    public void onBackPressed() {
        Note note;
        String text = mNoteEditText.getText().toString();
        note = new Note( text, Constants.NOTE_DETAILS );
        Intent intent = new Intent();
        intent.putExtra(Constants.NOTE, note);
        setResult(RESULT_OK, intent);
        finish();
    }


}
