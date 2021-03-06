package com.barmej.notesapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.adapter.NoteAdapter;
import com.barmej.notesapp.data.database.NoteViewModel;
import com.barmej.notesapp.data.entities.Note;
import com.barmej.notesapp.data.entities.NoteCheckItem;
import com.barmej.notesapp.data.entities.NotePhotoItem;
import com.barmej.notesapp.data.entities.TextNote;
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.barmej.notesapp.Constants.NOTE_CHECK_VIEW_TYPE;
import static com.barmej.notesapp.Constants.NOTE__PHOTO_VIEW_TYPE;

;

public class MainActivity extends AppCompatActivity {

    //FloatingActionButton to add new note
    private FloatingActionButton mFloatingActionButton;

    //RecyclerView to show notItems
    private RecyclerView mRecyclerView;

    //NoteAdapter
    private NoteAdapter mAdapter;

    // ArrayLists
    private ArrayList<Note> mItems;
    private List<Note> textNotes;
    private List<NotePhotoItem> photoNotes;
    private List<NoteCheckItem> checkNotes;

    //LayoutManagers to view items as a list or a grid
    RecyclerView.LayoutManager mListLayoutManager;
    RecyclerView.LayoutManager mGridtLayoutManager;

    // Note object
    private Note note;

    //menu
    Menu mMenu;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // Find views by id's
        mFloatingActionButton = findViewById( R.id.floating_button_add );
        mRecyclerView = findViewById( R.id.recycler_view_photos );

        //Create new ArrayLists object
        mItems = new ArrayList<Note>();
        textNotes = new ArrayList<>();
        photoNotes = new ArrayList<>();
        checkNotes = new ArrayList<>();
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );

        //Overriding itemClickListener
        mAdapter = new NoteAdapter( mItems, new ItemClickListener() {

            @Override
            public void onClickItem(int position) {
                Note note = mItems.get( position );
                Intent intent;
                switch (note.getViewType()) {
                    case Constants.NOTE_VIEW_TYPE:
                        intent = new Intent( MainActivity.this, NoteDetailsActivity.class );
                        intent.putExtra( "note_details", note );
                        intent.putExtra( "note_position_key", position );
                        startActivityForResult( intent, Constants.NOTE_DETAILS );
                        break;

                    case Constants.NOTE__PHOTO_VIEW_TYPE:
                        intent = new Intent( MainActivity.this, NotePhotoDetailsActivity.class );
                        intent.putExtra( "note_photo_details", note );
                        intent.putExtra( "note_photo_position_key", position );
                        startActivityForResult( intent, Constants.NOTE_PHOTO_DETAILS );
                        break;

                    case Constants.NOTE_CHECK_VIEW_TYPE:
                        intent = new Intent( MainActivity.this, NoteCheckDetailsActivity.class );
                        intent.putExtra( "note_check_details", note );
                        intent.putExtra( "note_check_position_key", position );
                        startActivityForResult( intent, Constants.NOTE_CHECK_DETAILS );
                        break;
                    default:
                        throw new IllegalStateException( "Unexpected value: " + note );
                }

            }
        }, new ItemLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                //deleteItem(position);
                Note note = mItems.get( position );
                NoteViewModel noteViewModel1;
                switch (note.getViewType()) {

                    case Constants.NOTE_VIEW_TYPE:
                        noteViewModel.deleteNote( (TextNote) note );
                        break;

                    case NOTE_CHECK_VIEW_TYPE:
                        noteViewModel.deleteNoteCheck( (NoteCheckItem) note );
                        break;

                    case NOTE__PHOTO_VIEW_TYPE:
                        noteViewModel.deleteNotePhoto( (NotePhotoItem) note );
                        break;


                    default:
                        throw new IllegalStateException( "Unexpected value: " + note.getViewType() );
                }
            }
        } );

        //Create ListLayoutManager object
        mListLayoutManager = new LinearLayoutManager( this );

        //Create GridLayoutManager object
        mGridtLayoutManager = new GridLayoutManager( this, 2 );

        //Set RecycelerView to ListLayoutManager as adefault
        mRecyclerView.setLayoutManager( mListLayoutManager );
        mRecyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );

        //set the adpter to recyclerview
        mRecyclerView.setAdapter( mAdapter );

        //Overriding setOnClickListener of floatingButton to start AddNewNoteActivity
        findViewById( R.id.floating_button_add ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNoteActivity();
            }
        } );

        requestNote();
        requestNoteCheckItem();
        requestNotePhotoItem();
    }

    //AddNewNoteActivity intent
    public void addNewNoteActivity() {
        Intent intent = new Intent( this, AddNewNoteActivity.class );
        startActivity( intent );
    }


    //overriding onCreateOptionMenu method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate( R.menu.menu, menu );
        return true;
    }

    //overriding onCreateOptionItemSelected method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_list) {
            mRecyclerView.setLayoutManager( mGridtLayoutManager );
            item.setVisible( false );
            mMenu.findItem( R.id.action_grid ).setVisible( true );
            return true;

        } else if (item.getItemId() == R.id.action_grid) {
            mRecyclerView.setLayoutManager( mListLayoutManager );
            item.setVisible( false );
            mMenu.findItem( R.id.action_view_list ).setVisible( true );
            return true;

        }
        return super.onOptionsItemSelected( item );
    }

    //Delete Item Dialog options
    private void deleteItem(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder( this ).setMessage( R.string.delete_confirmation ).
                setPositiveButton( R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mItems.remove( position );
                        mAdapter.notifyItemRemoved( position );

                    }
                } )
                .setNegativeButton( R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } ).create();
        alertDialog.show();

    }

    /*
    Request note data
   */
    private void requestNote() {
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.getAllNoteLiveData().observe( this, new Observer<List<TextNote>>() {

            @Override
            public void onChanged(List<TextNote> notes) {
                textNotes.clear();
                textNotes.addAll( notes );
                showData();
            }
        } );

    }

    /*
   Request noteCheckItem data
   */
    private void requestNoteCheckItem() {
        NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.getAllNoteCheckItemLiveData().observe( this, new Observer<List<NoteCheckItem>>() {
            @Override
            public void onChanged(List<NoteCheckItem> noteCheckItems) {
                checkNotes.clear();
                checkNotes.addAll( noteCheckItems );
                showData();
            }
        } );
    }

    /*
     Request notePhotItem data
  */
    private void requestNotePhotoItem() {
        NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.getAllNotePhotoItemLiveData().observe( this, new Observer<List<NotePhotoItem>>() {
            @Override
            public void onChanged(List<NotePhotoItem> notePhotoItems) {
                photoNotes.clear();
                photoNotes.addAll( notePhotoItems );
                showData();
            }
        } );
    }

    /*
     Add, sort notes, and notify the adapter for changes
     */
    private void showData() {
        mItems.clear();
        mItems.addAll( textNotes );
        mItems.addAll( photoNotes );
        mItems.addAll( checkNotes );
        Collections.sort( mItems, Collections.<Note>reverseOrder() );
        mAdapter.notifyDataSetChanged();
    }

}
