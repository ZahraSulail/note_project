package com.barmej.notesapp.adapter;

import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.NoteCheckItem;
import com.barmej.notesapp.data.NotePhotoItem;
import com.barmej.notesapp.databinding.ItemNoteBinding;
import com.barmej.notesapp.databinding.ItemNoteCheckBinding;
import com.barmej.notesapp.databinding.ItemNotePhotoBinding;
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

//Note adapter class
public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //ArrayList variable
    private ArrayList<Note> mItems;

    //ItemClickListener and ItemLongClickListener variables
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;

    //Constructor
    public NoteAdapter(ArrayList<Note> mItems, ItemClickListener mItemClickListener, ItemLongClickListener mItemLongClickListener ) {
        this.mItems = mItems;
        this.mItemClickListener = mItemClickListener;
        this.mItemLongClickListener = mItemLongClickListener;
    }

    //overriding onCreateViewHolder method
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;


         switch (viewType){
             case (Constants.NOTE_VIEW_TYPE):
                 view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_note, parent, false);
                 ItemNoteBinding noteBinding = DataBindingUtil.bind( view );
                 viewHolder = new NoteViewHolder( noteBinding, mItemClickListener, mItemLongClickListener);
                 break;

             case (Constants.NOTE_CHECK_VIEW_TYPE):
                 view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_note_check, parent, false);
                 ItemNoteCheckBinding noteCheckBinding = DataBindingUtil.bind( view );
                 viewHolder = new NoteChecItemViewHolder( noteCheckBinding,  mItemClickListener, mItemLongClickListener);
                 break;
             case (Constants.NOTE__PHOTO_VIEW_TYPE):
                 view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_note_photo, parent, false);
                 ItemNotePhotoBinding notePhotoBinding = DataBindingUtil.bind(view);
                 viewHolder = new NotePhotoItemViewHolder( notePhotoBinding, mItemClickListener, mItemLongClickListener);
                 break;
             default:
                 throw new IllegalArgumentException();
         }
           return viewHolder;
         }

    //onBindViewHolder method to bind viewHolders to the views
    @Override
    public void onBindViewHolder(@NonNull  final RecyclerView.ViewHolder holder, final int position) {
        Note note = mItems.get( position );

        if (note != null) {
            switch (note.getViewType()) {

                case (Constants.NOTE_VIEW_TYPE):
                    //((NoteViewHolder) holder).noteTextView.setText(note.getText());

                    ((NoteViewHolder) holder).bind( note );


                    break;
                case(Constants.NOTE_CHECK_VIEW_TYPE):
                    /*
                    ((NoteChecItemViewHolder) holder).noteCheckTextView.setText(note.getText());
                    ((NoteChecItemViewHolder) holder). noteCheckBox.setChecked(((NoteCheckItem) note).isChecked());
                    */

                    ((NoteViewHolder) holder).bind( note );
                    ((NoteChecItemViewHolder) holder).bind( (NoteCheckItem) note );

                    break;
                case (Constants.NOTE__PHOTO_VIEW_TYPE):
                    /*
                    ((NotePhotoItemViewHolder) holder).notePhotoTextViewt.setText(note.getText()  );
                    ((NotePhotoItemViewHolder) holder).notePhotoImageView.setImageResource(((NotePhotoItem) note).getImageResId());
                     */

                    ((NoteViewHolder) holder).bind( note );
                    ((NotePhotoItemViewHolder) holder).bind( (NotePhotoItem) note );

                    break;


                default:
                    throw new IllegalStateException( "Unexpected value: " + note.getViewType() );

            }
            holder.itemView.setBackgroundColor( note.getBackgroundColor() );
        }
    }

    //getItemViewType method
    @Override
    public int getItemViewType(int position) {
        Note note = mItems.get(position);
        if(note instanceof NoteCheckItem){
            return Constants.NOTE_CHECK_VIEW_TYPE;

        }else if(note instanceof NotePhotoItem){
            return Constants.NOTE__PHOTO_VIEW_TYPE;

        }else{
           return  Constants.NOTE_VIEW_TYPE;

        }
    }

    //getItemCount method
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //NoteViewHolder
    class NoteViewHolder extends RecyclerView.ViewHolder{
            int position;
            final ItemNoteBinding noteBinding;
            private TextView noteTextView;
            NoteViewHolder(@NonNull ItemNoteBinding noteBinding, final ItemClickListener itemClickListener, final ItemLongClickListener itemLongClickListener) {
                super( noteBinding.getRoot());
                this.noteBinding = noteBinding;
                position=   getAdapterPosition();



                itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position=   getAdapterPosition();
                        mItemClickListener.onClickItem(position);
                    }
                } );
                itemView.setOnLongClickListener( new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        position=   getAdapterPosition();
                        mItemLongClickListener.onLongClickItem( position );
                        return true;
                    }
                } );

            }
            void bind(Note note){
                noteBinding.setNote( note );
            }
    }

         //NoteCheckItemViewHolder
         class NoteChecItemViewHolder extends RecyclerView.ViewHolder{
            int position;
           final ItemNoteCheckBinding noteCheckBinding;
            NoteChecItemViewHolder(@NonNull ItemNoteCheckBinding noteCheckBinding, final ItemClickListener itemClickListener, final ItemLongClickListener itemLongClickListener) {
                super( noteCheckBinding.getRoot() );
                this.noteCheckBinding = noteCheckBinding;

                itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position=   getAdapterPosition();
                        mItemClickListener.onClickItem(position);
                    }
                } );
                itemView.setOnLongClickListener( new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        position=   getAdapterPosition();
                        mItemLongClickListener.onLongClickItem( position );
                        return true;
                    }
                } );
            }
            void bind(NoteCheckItem noteCheckItem){
                noteCheckBinding.setNoteCheck( noteCheckItem );
            }
    }

    //NotePhotoViewHolder
     class NotePhotoItemViewHolder extends RecyclerView.ViewHolder{
        int position;
       final ItemNotePhotoBinding notePhotoBinding;

        NotePhotoItemViewHolder(@NonNull ItemNotePhotoBinding  notePhotoBinding, final ItemClickListener itemClickListener, final ItemLongClickListener itemLongClickListener) {
            super( notePhotoBinding.getRoot());
            this.notePhotoBinding = notePhotoBinding;

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position=   getAdapterPosition();
                    mItemClickListener.onClickItem(position);

                }
            } );
            itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    position=   getAdapterPosition();
                    mItemLongClickListener.onLongClickItem( position );
                    return true;
                }
            } );
        }
        void bind(NotePhotoItem notePhotoItem){
            notePhotoBinding.setNotePhoto( notePhotoItem );
        }
    }


}

