package com.barmej.notesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NotePhotoItem;

import static com.barmej.notesapp.Activities.AddNewNoteActivity.photos;

public class NotePhotoDetailsActivity extends AppCompatActivity {

    //ImageView variable
    private ImageView mNotePhotoImageView;

    //EditText variable
    private EditText mNotePhotoEditText;
    int position ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_note_photo_details );

        //find views by id's
        mNotePhotoImageView = findViewById( R.id.photoImageView );
        mNotePhotoEditText = findViewById( R.id.photoNoteEditText);

        //Intent to receive notes that need to edit
        Intent intent =  getIntent();
        Note note = (Note) intent.getSerializableExtra( "note_photo_details");
        position = intent.getIntExtra( "note_photo_position_key", 0 );
        Drawable imageResId = mNotePhotoImageView.getDrawable();
        String text = mNotePhotoEditText.getText().toString();
        mNotePhotoEditText.setText(note.getText());
    }

    // press back button to send results after editting
    public void onBackPressed() {
        Note note;
        String text = mNotePhotoEditText.getText().toString();
        note = new NotePhotoItem( text, Constants.NOTE__PHOTO_VIEW_TYPE,  photos[AddNewNoteActivity.index] );
        Intent intent = new Intent();
        intent.putExtra(Constants.NOTE, note);
        intent.putExtra( "note_photo_position_key", position );
        setResult(RESULT_OK, intent);
        finish();
    }
}
