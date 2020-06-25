package com.barmej.notesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.CheckBox;
import android.widget.EditText;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NoteCheckItem;
import com.barmej.notesapp.data.NotePhotoItem;
import com.barmej.notesapp.data.NoteViewModel;
import com.barmej.notesapp.databinding.ActivityNoteCheckDetailsBinding;

import java.util.List;

public class NoteCheckDetailsActivity extends AppCompatActivity {
    int position;
    NoteCheckItem note;
   private ActivityNoteCheckDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_note_check_details );
        binding.setLifecycleOwner( this );
        binding.getRoot();


        //Intent to receive notes that need to edit
        Intent intent = getIntent();
         note = (NoteCheckItem) intent.getSerializableExtra( "note_check_details");
        //note = (Note) intent.getSerializableExtra( "note_check_detais_1" );
        position = intent.getIntExtra( "note_check_position_key", 0 );
        //binding.setNoteCheck( );

        requestNoteCheckItem(note.getId());

    }

    // press back button to send results after editting
    public void onBackPressed() {

        String text = binding.checkNoteEditText.getText().toString();
        boolean isChecked = binding.checkNoteCheckBox.isChecked();
        note.setText( text );
        note.setChecked( isChecked );
        Intent intent = new Intent();
        intent.putExtra(  Constants.NOTE ,  note);
        intent.putExtra( "note_check_position_key", position );
        setResult(RESULT_OK, intent);
        finish();
    }



    /*
Request noteCheckItem data
*/
    private void requestNoteCheckItem(int id){
        final NoteViewModel noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getNoteCheckItem(id).observe( this, new Observer<NoteCheckItem>() {
            @Override
            public void onChanged(NoteCheckItem noteCheckItems) {
                //mNoteCheckEditText.setText( noteCheckItems.getText() );
                //boolean isChecked = mNoteCheckBox.isChecked();
                // binding.setNoteCheck(noteCheckItems);

            }
        } );
    }



}
