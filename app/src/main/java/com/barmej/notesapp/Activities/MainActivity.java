package com.barmej.notesapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.adapter.NoteAdapter;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NoteCheckItem;
import com.barmej.notesapp.data.NotePhotoItem;
import com.barmej.notesapp.data.NoteViewModel;
import com.barmej.notesapp.data.database.dao.NoteCheckItemDao;
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.barmej.notesapp.Constants.NOTE;
import static com.barmej.notesapp.Constants.NOTE_CHECK_VIEW_TYPE;
import static com.barmej.notesapp.Constants.NOTE__PHOTO_VIEW_TYPE;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    //FloatingActionButton to add new note
    private FloatingActionButton mFloatingActionButton;

    //RecyclerView to show notItems
    private RecyclerView mRecyclerView;

    //NoteAdapter
    private NoteAdapter mAdapter;

    // ArrayList
    private ArrayList<Note> mItems;

    private List<Note> textNotes;
    private List<NotePhotoItem> photoNotes;
    private List<NoteCheckItem> checkNotes;

    //LayoutManagers to view items as a list or a grid
    RecyclerView.LayoutManager mListLayoutManager;
    RecyclerView.LayoutManager mGridtLayoutManager;

    private Note note;

    //menu
    Menu mMenu;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        // Find views by id's
        mFloatingActionButton = findViewById(R.id.floating_button_add);
        mRecyclerView = findViewById(R.id.recycler_view_photos);
        //Create ArrayList object

        mItems = new ArrayList<Note>();
        textNotes = new ArrayList<>();
        photoNotes = new ArrayList<>();
        checkNotes = new ArrayList<>();

        //Overriding itemClickListener
        mAdapter = new NoteAdapter( mItems, new ItemClickListener() {

            @Override
            public void onClickItem(int position) {
                Note note = mItems.get( position );
                Intent intent;
             switch (note.getViewType()){
                 case Constants.NOTE_VIEW_TYPE:
                     intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
                     intent.putExtra( "note_details", note);
                     intent.putExtra( "note_position_key", position );
                     startActivityForResult(intent, Constants.NOTE_DETAILS);
                     break;
                     case Constants.NOTE__PHOTO_VIEW_TYPE:
                         intent = new Intent(MainActivity.this, NotePhotoDetailsActivity.class);
                         intent.putExtra( "note_photo_details", note  );
                         //intent.putExtra( "note_phot_detais_1", (NotePhotoItem) note );
                         intent.putExtra( "note_photo_position_key", position );
                         startActivityForResult(intent,Constants.NOTE_PHOTO_DETAILS);
                         break;
                         case Constants.NOTE_CHECK_VIEW_TYPE:
                             intent = new Intent(MainActivity.this, NoteCheckDetailsActivity.class);
                             intent.putExtra( "note_check_details",  note  );
                             //intent.putExtra( "note_check_detais_1", (NoteCheckItem) note );
                             intent.putExtra( "note_check_position_key", position );
                             startActivityForResult(intent, Constants.NOTE_CHECK_DETAILS);

                             break;
                 default:
                     throw new IllegalStateException( "Unexpected value: " + note);
             }
            }
        }, new ItemLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                deleteItem(position);

            }
        } );
        //Create ListLayoutManager object
        mListLayoutManager = new LinearLayoutManager( this);

        //Create GridLayoutManager object
        mGridtLayoutManager = new GridLayoutManager(this, 2);

        //Set RecycelerView to ListLayoutManager as adefault
        mRecyclerView.setLayoutManager( mListLayoutManager);

        //set the adpter to recyclerview
        mRecyclerView.setAdapter(mAdapter);

        //Overriding setOnClickListener of floatingButton to start AddNewNoteActivity
        findViewById( R.id.floating_button_add ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNoteActivity();
            }
        } );

    /*
     Call the observers functions
     */
      requestNote();
      requestNoteCheckItem();
      requestNotePhotoItem();
    }

    //AddNewNoteActivity intent
    public void addNewNoteActivity(){
        Intent intent = new Intent(this, AddNewNoteActivity.class );
        startActivityForResult( intent, Constants.ADD_NOTE);
    }

    //onActivityResult to receive results from other activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        Note note;
        if(requestCode == Constants.ADD_NOTE ){
            if(resultCode == RESULT_OK && data != null  ){
                 note = (Note) data.getSerializableExtra( NOTE);
                 addItem( note);
            }
        }else{

            if(requestCode == Constants.NOTE_DETAILS){
                if(resultCode == RESULT_OK && data!= null){
                    note = (Note) data.getSerializableExtra( NOTE );
                 int index =  data.getIntExtra(  "not_postion_key", 0 );
                 mItems.set(index,note);
                 mAdapter.notifyDataSetChanged();
                }
            }else{
                if(requestCode == Constants.NOTE_PHOTO_DETAILS){
                    if(resultCode == RESULT_OK && data != null){
                        note = (Note) data.getSerializableExtra( NOTE );
                        int index = data.getIntExtra( "note_photo_position_key",  0 );
                        mItems.set(index,note);
                        mAdapter.notifyDataSetChanged();
                    }
                }else{

                    if(requestCode == Constants.NOTE_CHECK_DETAILS){
                        if(resultCode == RESULT_OK && data != null){
                            note = (Note) data.getSerializableExtra( NOTE );
                            int index = data.getIntExtra( "note_check_position_key", 0 );
                            mItems.set(index,note);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }

    //Aaa item method
    private void addItem( Note note){
       mItems.add(note);
       mAdapter.notifyDataSetChanged();

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
        if(item.getItemId()== R.id.action_view_list){
           mRecyclerView.setLayoutManager(mListLayoutManager);
           item.setVisible(false);
           mMenu.findItem( R.id.action_grid ).setVisible(true);
           return true;

        }else if(item.getItemId()== R.id.action_grid){
            mRecyclerView.setLayoutManager(mGridtLayoutManager);
            item.setVisible(false);
            mMenu.findItem( R.id.action_view_list ).setVisible(true);
            return true;

        }
        return super.onOptionsItemSelected( item );
    }

    //Delete Item Dialog options
    private void deleteItem(final int position){
        AlertDialog alertDialog = new AlertDialog.Builder( this ).setMessage( R.string.delete_confirmation).
                setPositiveButton( R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mItems.remove(position);
                        mAdapter.notifyItemRemoved(position );

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
    private void requestNote(){
        final NoteViewModel noteViewModel = ViewModelProviders.of( this ).get(NoteViewModel.class);
        noteViewModel.getAllNoteLiveData().observe( this, new Observer<List<Note>>() {

            @Override
            public void onChanged(List<Note> notes) {
                    textNotes.clear();
                    textNotes.addAll(notes);
                    showData();
            }
        } );

    }

    /*
Request noteCheckItem data
*/
    private void requestNoteCheckItem(){
        NoteViewModel noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNoteCheckItemLiveData().observe( this, new Observer<List<NoteCheckItem>>() {
            @Override
            public void onChanged(List<NoteCheckItem> noteCheckItems) {
                checkNotes.clear();
                checkNotes.addAll(noteCheckItems);
                showData();
            }
        } );
    }

    /*
     Request notePhotItem data
  */
    private void requestNotePhotoItem(){
        NoteViewModel noteViewModel = ViewModelProviders.of( this ).get( NoteViewModel.class );
        noteViewModel.getAllNotePhotoItemLiveData().observe( this, new Observer<List<NotePhotoItem>>() {
            @Override
            public void onChanged(List<NotePhotoItem> notePhotoItems) {
                photoNotes.clear();
                photoNotes.addAll(notePhotoItems);
                showData();
            }
        } );
    }

    private void showData() {
        mItems.clear();
        mItems.addAll( textNotes );
        mItems.addAll( photoNotes );
        mItems.addAll( checkNotes );
        Collections.sort( mItems );
        mAdapter.notifyDataSetChanged();
    }
}
