package com.barmej.notesapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.barmej.notesapp.R;
import com.barmej.notesapp.data.database.NoteViewModel;
import com.barmej.notesapp.data.entities.TextNote;
import com.barmej.notesapp.databinding.ActivityNoteDetailsBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NoteDetailsActivity extends AppCompatActivity {

    /*
      NoteDetailsBinding
     */
    private ActivityNoteDetailsBinding binding;

    /*
      TextNote variable
     */
    TextNote note;

    /*
     Position variable to get note position
     */
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_note_details );
        binding.setLifecycleOwner( this );

        //Intent to receive notes that need to edit
        Intent intent = getIntent();
        note = intent.getParcelableExtra( "note_details" );
        position = intent.getIntExtra( "note_position_key", 0 );
        binding.setNote( note );
        requestNote( note.getId() );
    }

    /*
     Press back button to send results after editting
     */
    @Override
    public void onBackPressed() {
        String text = binding.noteEditText.getText().toString();
        note.setText( text );
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.updateNote( note );
        finish();
        super.onBackPressed();
    }

    /*
   Request note data
   */
    private void requestNote(int id) {
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.getNote( id ).observe( this, new Observer<TextNote>() {
            @Override
            public void onChanged(TextNote notes) {
                NoteDetailsActivity.this.note = notes;
                binding.setNote( notes );
            }
        } );
    }
}
