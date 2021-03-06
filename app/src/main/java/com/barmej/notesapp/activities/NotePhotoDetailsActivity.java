package com.barmej.notesapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.database.NoteViewModel;
import com.barmej.notesapp.data.entities.NotePhotoItem;
import com.barmej.notesapp.databinding.ActivityNotePhotoDetailsBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NotePhotoDetailsActivity extends AppCompatActivity {

    /*
      NotePhotoDetailsBinding
     */
    ActivityNotePhotoDetailsBinding binding;

    /*
     photoUri variable
     */
    private Uri photoUri;

    /*
      Image view
     */
    private ImageView mPhotoImageView;

    /*
     note color varaible
     */
    int notePhotoColor;

    /*
      read storage permission granted
     */
    private boolean mReadStoragePermissionGranted;

    /*
      RelativeLayout
     */
    private RelativeLayout mRelativeLayout;

    /*
     NotePhotoItem object
     */
    NotePhotoItem note;

    /*
     Position varailble to get note position
     */
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /*
         Bind NoteDetaisActivity data
         */
        binding = DataBindingUtil.setContentView( this, R.layout.activity_note_photo_details );

        //Intent to receive notes that need to edit
        Intent intent = getIntent();
        note = intent.getParcelableExtra( "note_photo_details" );
        photoUri = note.getPhoto();
        position = intent.getIntExtra( "note_photo_position_key", 0 );

        binding.setNotePhoto( (NotePhotoItem) note );
        requestNotePhotoItem( note.getId() );
        mRelativeLayout = findViewById( R.id.note_photo_detais_layout );

        mPhotoImageView = findViewById( R.id.photoImageView );

         /*
          Click on mPhotoImageView to pick photo from gallery
          */
        mPhotoImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
            }
        } );
    }


    // press back button to send results after editting
    @Override
    public void onBackPressed() {
        String text = binding.photoNoteEditText.getText().toString();
        note.setText( text );
        note.setPhoto( photoUri );
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.updateNotePhoto( (NotePhotoItem) note );
        finish();
        super.onBackPressed();
    }

    /*
     Request notePhotoItem data
  */
    private void requestNotePhotoItem(int id) {
        NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.getNotePhotoItem( id ).observe( this, new Observer<NotePhotoItem>() {
            @Override
            public void onChanged(NotePhotoItem notePhotoItem) {
                NotePhotoDetailsActivity.this.note = notePhotoItem;
                binding.setNotePhoto( notePhotoItem );

            }
        } );
    }

    /*
     Request permission to lunch Android gallery
     */
    private void requestStoragePermission() {
        mReadStoragePermissionGranted = false;
        if (ContextCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {
            mReadStoragePermissionGranted = true;
            selectPhotoIntent();
        } else {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISSION_REQUEST_READ_STORAGE );

        }
    }

    /*
      onRequestPermissionResult method
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == Constants.PERMISSION_REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPhotoIntent();
            } else {
                Toast.makeText( this, R.string.read_permission_neede_to_access_files, Toast.LENGTH_SHORT ).show();
            }

        }
    }

    /*
     onActivityResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == Constants.REQUEST_GET_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    photoUri = data.getData();
                    mPhotoImageView.setImageURI( photoUri );
                    int flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    getContentResolver().takePersistableUriPermission( photoUri, flags );

                } catch (Exception e) {
                    Snackbar.make( mRelativeLayout, R.string.photo_selected_error, Snackbar.LENGTH_LONG ).show();
                }


            }
        }
    }

    /*
      Intent to select phpoto from gallery
     */
    private void selectPhotoIntent() {
        Intent intent = new Intent( Intent.ACTION_OPEN_DOCUMENT );
        intent.addCategory( Intent.CATEGORY_OPENABLE );
        intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION );
        intent.setType( "image/*" );
        startActivityForResult( Intent.createChooser( intent, getString( R.string.choose_photo ) ), Constants.REQUEST_GET_PHOTO );
    }

}
