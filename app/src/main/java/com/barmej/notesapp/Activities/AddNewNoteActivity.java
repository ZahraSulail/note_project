package com.barmej.notesapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NoteCheckItem;
import com.barmej.notesapp.data.NotePhotoItem;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class AddNewNoteActivity extends AppCompatActivity {

    //ImageView variable
    ImageView mImageView;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_new_note );

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
        mImageView = findViewById( R.id.photoImageView);
        mNoteChecBox = findViewById( R.id.checkNoteCheckBox );
        mNoteEditText = findViewById( R.id.noteEditText );
        mNoteCheckEditText = findViewById( R.id.checkNoteEditText );
        mPhotNoteEditText = findViewById( R.id.photoNoteEditText );
        MaterialButton mAddButton = findViewById( R.id.button_submit );

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
                    mPhotoCardView.setCardBackgroundColor( Color.YELLOW );
                    mNoteCardView.setCardBackgroundColor( Color.YELLOW);
                    mNoteCheckCardView.setCardBackgroundColor( Color.YELLOW );
                }
            }
        } );

        //addButton setOnClickListener to sendBack data from AddNewNoteActivity to MainActivity
        mAddButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Note note;
                if(noteRadioButton.isChecked()) {
                    String text = mNoteEditText.getText().toString();
                    note = new Note(text, Constants.NOTE_VIEW_TYPE);

                }else if(notePhotoRadioButton.isChecked()) {

                    String text = mPhotNoteEditText.getText().toString();
                    note = new NotePhotoItem( text, Constants.NOTE__PHOTO_VIEW_TYPE, photos[index] );

                }else{
                    String text = mNoteCheckEditText.getText().toString();
                    boolean isChecked = mNoteChecBox.isChecked();
                    note = new NoteCheckItem(text, Constants.NOTE_CHECK_VIEW_TYPE, isChecked);

                }
                intent.putExtra( Constants.NOTE, note );
                setResult( RESULT_OK, intent );
                finish();
            }
        } );
    }

    //select image method
     public void selectImage(View view){
        Random mRandom = new Random();
        index = mRandom.nextInt(photos.length);
        imageDrawable();
     }

     //set image drawable
    public void imageDrawable(){

      Drawable imageResId = ContextCompat.getDrawable(this, photos[index]);
      mImageView.setImageDrawable( imageResId);
    }


}















