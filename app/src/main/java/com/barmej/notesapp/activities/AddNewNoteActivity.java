package com.barmej.notesapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.entities.NoteCheckItem;
import com.barmej.notesapp.data.entities.NotePhotoItem;
import com.barmej.notesapp.data.database.NoteViewModel;
import com.barmej.notesapp.data.entities.Note;
import com.barmej.notesapp.data.entities.TextNote;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

public class AddNewNoteActivity extends AppCompatActivity {

    private ConstraintLayout mConstraintLayout;
    //ImageView variable
    ImageView mPhotoImageView;
    int color;
    Uri mSelectedPhotoUri;

    private boolean mReadStoragePermissionGranted;

    //CheckBox variable
    private CheckBox mNoteChecBox;

    //EditText Variables
    private EditText mNoteEditText;
    private EditText mNoteCheckEditText;
    private EditText mPhotNoteEditText;

    //RadioButton variables
    private RadioButton noteRadioButton;
    private RadioButton notePhotoRadioButton;
    private RadioButton noteCheckRadioButton;
    private  RadioButton redRadioButton;
    private RadioButton blueRadioButton;
    private RadioButton yellowRadioButton;

    //CardView variables
    CardView mNoteCardView;
    CardView mNoteCheckCardView;
    CardView mPhotoCardView;

    //ArrayList variable
    private ArrayList<Note> mItems;

    //integer Photo ArrayList
    public static final int [] photos = {
            R.drawable.note,
            R.drawable.note_1,
            R.drawable.note_2,
            R.drawable.note_3,
            R.drawable.note_4,
   };

    //int index
    public static int index = -1;
    private Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_add_new_note);


        //FindVies by Id's
        noteRadioButton = findViewById( R.id.note_radioButton );
        noteCheckRadioButton = findViewById( R.id.note_chek_radioButton );
        notePhotoRadioButton = findViewById( R.id.note_photo_radioButton );
        redRadioButton = findViewById( R.id.radioButton_red );
        blueRadioButton = findViewById( R.id.radioButton_blue );
        yellowRadioButton = findViewById( R.id.radioButton_yellow );


        mNoteCardView = findViewById( R.id.cardViewNote );
        mNoteCheckCardView = findViewById( R.id.cardViewCheckNote );
        mPhotoCardView = findViewById( R.id.cardViewPhoto );
        mPhotoImageView = findViewById( R.id.photoImageView);
        mNoteChecBox = findViewById( R.id.checkNoteCheckBox );
        mNoteEditText = findViewById( R.id.noteEditText );
        mNoteCheckEditText = findViewById( R.id.checkNoteEditText );
        mPhotNoteEditText = findViewById( R.id.photoNoteEditText );
        MaterialButton mAddButton = findViewById( R.id.button_submit );
        mConstraintLayout = findViewById( R.id.add_note_constraint_layout );

        /*
          click on image to pick image from device gallery
         */
        mPhotoImageView.setOnClickListener( new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 requestStoragePermission();
             }
         } );

        //noteRadioButton setOnChangeListener method to view noteCrdView
        noteRadioButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mNoteCardView.setVisibility( View.VISIBLE );
                    mNoteCheckCardView.setVisibility( View.INVISIBLE );
                    mPhotoCardView.setVisibility( View.INVISIBLE );
                }
            }
        }
        );

        //noteCheckRadioButton setOnChangeListener method to view noteCheckCardView
        noteCheckRadioButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mNoteCheckCardView.setVisibility( View.VISIBLE );
                    mPhotoCardView.setVisibility( View.INVISIBLE );
                    mNoteCardView.setVisibility( View.INVISIBLE );
                }
            }
                                                         }
        );

        //notePhotoRadioButton setOnChangeListener method to view notePhotoCardView
        notePhotoRadioButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPhotoCardView.setVisibility( View.VISIBLE );
                    mNoteCardView.setVisibility( View.INVISIBLE );
                    mNoteCheckCardView.setVisibility( View.INVISIBLE );
                }
            }
        }

        );

        //redRadioButton setOnChangeListener method to chang cardView color to red color
        redRadioButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    color = Color.RED;

                    mPhotoCardView.setCardBackgroundColor( Color.RED );
                    mNoteCardView.setCardBackgroundColor( Color.RED );
                    mNoteCheckCardView.setCardBackgroundColor( Color.RED );
                }
            }
        } );

        //blueRadioButton setOnChangeListener method to chang cardView color to blue color
        blueRadioButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    color = Color.BLUE;
                    mPhotoCardView.setCardBackgroundColor( Color.BLUE);
                    mNoteCardView.setCardBackgroundColor( Color.BLUE);
                    mNoteCheckCardView.setCardBackgroundColor( Color.BLUE );
                }
            }
        } );

        //yellowRadioButton setOnChangeListener method to chang cardView color to yellow color
        yellowRadioButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    color = Color.YELLOW;
                    mPhotoCardView.setCardBackgroundColor( Color.YELLOW );
                    mNoteCardView.setCardBackgroundColor( Color.YELLOW);
                    mNoteCheckCardView.setCardBackgroundColor( Color.YELLOW );
                }
            }
        } );

        /*
          addButton setOnClickListener to sendBack data from AddNewNoteActivity to MainActivity
         */
        mAddButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               Note note;

                if(noteRadioButton.isChecked()) {
                    String text = mNoteEditText.getText().toString();
                    note = new TextNote(text);
                    note.setBackgroundColor(color);
                    NoteViewModel noteViewModel = ViewModelProviders.of( AddNewNoteActivity.this ).get( NoteViewModel.class );
                    noteViewModel.addNote( (TextNote) note );

                }else if(notePhotoRadioButton.isChecked()) {

                    String text = mPhotNoteEditText.getText().toString();

                    note = new NotePhotoItem( text, mSelectedPhotoUri );
                    note.setBackgroundColor(color);
                    NoteViewModel noteViewModel = ViewModelProviders.of( AddNewNoteActivity.this ).get( NoteViewModel.class );
                    noteViewModel.addNotePhoto((NotePhotoItem) note );



                }else{
                    String text = mNoteCheckEditText.getText().toString();
                    boolean isChecked = mNoteChecBox.isChecked();
                    note = new NoteCheckItem(text, isChecked);
                    note.setBackgroundColor(color);
                    NoteViewModel noteViewModel = ViewModelProviders.of( AddNewNoteActivity.this ).get( NoteViewModel.class );
                    noteViewModel.addNoteCheck((NoteCheckItem) note );

                }

                finish();
            }
        } );
    }


   private void requestStoragePermission(){
        mReadStoragePermissionGranted = false;
       if (ContextCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {
           mReadStoragePermissionGranted = true;
           selectPhotoIntent();
       } else {
           ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISSION_REQUEST_READ_STORAGE );

       }
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if(requestCode == Constants.PERMISSION_REQUEST_READ_STORAGE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectPhotoIntent();
            }else{
                Toast.makeText(this, R.string.read_permission_neede_to_access_files, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == Constants.REQUEST_GET_PHOTO){
            if(resultCode == RESULT_OK ){
                try{
                    mSelectedPhotoUri = data.getData();
                    mPhotoImageView.setImageURI( mSelectedPhotoUri );

                }catch (Exception e){
                    Snackbar.make( mConstraintLayout, R.string.photo_selected_error, Snackbar.LENGTH_LONG ).show();
                }


            }
        }
    }

    private void selectPhotoIntent(){
        Intent intent = new Intent( Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory( Intent.CATEGORY_OPENABLE);
        intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION );
        intent.setType("image/*");
        startActivityForResult( Intent.createChooser( intent,  getString( R.string.choose_photo)), Constants.REQUEST_GET_PHOTO );
    }
}





















