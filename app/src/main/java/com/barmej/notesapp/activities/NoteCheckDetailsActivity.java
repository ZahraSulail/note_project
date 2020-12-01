package com.barmej.notesapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.barmej.notesapp.R;
import com.barmej.notesapp.data.entities.NoteCheckItem;
import com.barmej.notesapp.data.database.NoteViewModel;
import com.barmej.notesapp.data.entities.Note;
import com.barmej.notesapp.databinding.ActivityNoteCheckDetailsBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NoteCheckDetailsActivity extends AppCompatActivity {

    /*
     Position variable to get note position
     */
    int position;

    /*
     Note variable
   */
    Note note;

    /*
     integer note color variable
     */
    int noteCheckColor;


    /*
      NoteCheckDetailsBinding
    */
    private ActivityNoteCheckDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_note_check_details );

        //Intent to receive notes that need to edit
        Intent intent = getIntent();
        note = intent.getParcelableExtra( "note_check_details" );
        position = intent.getIntExtra( "note_check_position_key", 0 );
        //noteCheckColor = intent.getIntExtra( "note_check_color",0 );
        binding.setNoteCheck( (NoteCheckItem) note );

         /*
         Calling requestNoteCheckItemMethod
          */
        requestNoteCheckItem( note.getId() );
    }

    // press back button to send results after editting
    @Override
    public void onBackPressed() {
        String text = binding.checkNoteEditText.getText().toString();
        note.setText( text );
        boolean isChecked = binding.checkNoteCheckBox.isChecked();
        ((NoteCheckItem) note).setChecked( isChecked );
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.updateNoteCheck( (NoteCheckItem) note );
        finish();
        super.onBackPressed();
    }

    /*
Request noteCheckItem data
*/
    private void requestNoteCheckItem(int id) {
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.getNoteCheckItem( id ).observe( this, new Observer<NoteCheckItem>() {
            @Override
            public void onChanged(NoteCheckItem noteCheckItems) {
                NoteCheckDetailsActivity.this.note = noteCheckItems;
                binding.setNoteCheck( noteCheckItems );

            }
        } );
    }
}
