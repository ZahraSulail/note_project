package com.barmej.notesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NoteCheckItem;
import com.barmej.notesapp.data.NotePhotoItem;
import com.barmej.notesapp.data.NoteViewModel;
import com.barmej.notesapp.databinding.ActivityNoteDetailsBinding;

import java.util.List;

public class NoteDetailsActivity extends AppCompatActivity {

    private ActivityNoteDetailsBinding binding;
    int position ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_note_details );
        binding.setLifecycleOwner( this );
        binding.getRoot();

        //Intent to receive notes that need to edit
        Intent intent = getIntent();
        Note note= (Note) intent.getSerializableExtra( "note_details" );
        position = intent.getIntExtra( "note_position_key", 0 );
        binding.setNote( note );


        requestNote(note.getId());

    }

    // press back button to send results after editting
    public void onBackPressed() {
        Note note;
        String text = binding.noteEditText.getText().toString();
        note = new Note( text, Constants.NOTE_VIEW_TYPE );
        Intent intent = new Intent();
        intent.putExtra( Constants.NOTE, note);
        intent.putExtra( "note_position_key", position );
        setResult(RESULT_OK, intent);
        finish();
    }

    /*
Request note data
*/
    private void requestNote(int id){
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get(NoteViewModel.class);
        noteViewModel.getNote(id).observe( this, new Observer<Note>() {
            @Override
            public void onChanged(Note notes) {
               // binding.setNote( notes );
                binding.setNote( notes );
            }
        } );
    }
}
