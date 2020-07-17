package com.barmej.notesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NoteCheckItem;
import com.barmej.notesapp.data.NotePhotoItem;
import com.barmej.notesapp.data.NoteViewModel;
import com.barmej.notesapp.databinding.ActivityNotePhotoDetailsBinding;

import java.util.List;

import static com.barmej.notesapp.Activities.AddNewNoteActivity.index;
import static com.barmej.notesapp.Activities.AddNewNoteActivity.photos;

public class NotePhotoDetailsActivity extends AppCompatActivity {

    ActivityNotePhotoDetailsBinding binding;
    Note note;
    int position ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding =  binding = DataBindingUtil.setContentView(this, R.layout.activity_note_photo_details);

        //Intent to receive notes that need to edit
         Intent intent =  getIntent();
         note = (NotePhotoItem) intent.getSerializableExtra( "note_photo_details");
         position = intent.getIntExtra( "note_photo_position_key", 0 );
         binding.setNotePhoto( (NotePhotoItem) note );

         requestNotePhotoItem(note.getId());
    }


    // press back button to send results after editting
    @Override
    public void onBackPressed() {
        String text = binding.photoNoteEditText.getText().toString();
        note.setText( text);
        Drawable imageResId = binding.photoImageView.getDrawable();
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get(NoteViewModel.class);
        noteViewModel.updateNotePhoto((NotePhotoItem) note);
        finish();
        super.onBackPressed();
    }

    /*
     Request notePhotoItem data
  */
    private void requestNotePhotoItem(int id){
        NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.getNotePhotoItem(id).observe( this, new Observer<NotePhotoItem>() {
            @Override
            public void onChanged(NotePhotoItem notePhotoItem) {
                NotePhotoDetailsActivity.this.note = notePhotoItem;
                binding.setNotePhoto(notePhotoItem);

            }
        } );
    }

}
