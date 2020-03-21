package com.barmej.notesapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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

    //LayoutManagers to view items as a list or a grid
    RecyclerView.LayoutManager mListLayoutManager;
    RecyclerView.LayoutManager mGridtLayoutManager;

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
                         intent.putExtra( "note_photo_details", note );
                         intent.putExtra( "note_phot_detais_1", (NotePhotoItem) note );
                         intent.putExtra( "note_photo_position_key", position );
                         startActivityForResult(intent,Constants.NOTE_PHOTO_DETAILS);
                         break;
                         case Constants.NOTE_CHECK_VIEW_TYPE:
                             intent = new Intent(MainActivity.this, NoteCheckDetailsActivity.class);
                             intent.putExtra( "note_check_details",  note );
                             intent.putExtra( "note_check_detais_1", (NoteCheckItem) note );
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
                        mAdapter.notifyDataSetChanged();
                    }
                }else{

                    if(requestCode == Constants.NOTE_CHECK_DETAILS){
                        if(resultCode == RESULT_OK && data != null){
                            note = (Note) data.getSerializableExtra( NOTE );
                            int index = data.getIntExtra( "note_check_position_key", 0 );
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


}
