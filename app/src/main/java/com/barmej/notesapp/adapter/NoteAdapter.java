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
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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
                 viewHolder = new NoteViewHolder( view, mItemClickListener, mItemLongClickListener);
                 break;

             case (Constants.NOTE_CHECK_VIEW_TYPE):
                 view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_note_check, parent, false);
                 viewHolder = new NoteChecItemViewHolder( view, mItemClickListener, mItemLongClickListener);
                 break;
             case (Constants.NOTE__PHOTO_VIEW_TYPE):
                 view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_note_photo, parent, false);
                 viewHolder = new NotePhotoItemViewHolder( view, mItemClickListener, mItemLongClickListener);
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
                    ((NoteViewHolder) holder).noteTextView.setText(note.getText());
                    break;
                case(Constants.NOTE_CHECK_VIEW_TYPE):
                    ((NoteChecItemViewHolder) holder).noteCheckTextView.setText(note.getText());
                    ((NoteChecItemViewHolder) holder). noteCheckBox.setChecked(((NoteCheckItem) note).isChecked());
                    break;
                case (Constants.NOTE__PHOTO_VIEW_TYPE):
                    ((NotePhotoItemViewHolder) holder).notePhotoTextViewt.setText(note.getText()  );
                    ((NotePhotoItemViewHolder) holder).notePhotoImageView.setImageResource(((NotePhotoItem) note).getImageResId());

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
            private TextView noteTextView;
            NoteViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener, final ItemLongClickListener itemLongClickListener) {
                super( itemView );
                position=   getAdapterPosition();
                noteTextView = itemView.findViewById( R.id.note_text);

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
    }

         //NoteCheckItemViewHolder
         class NoteChecItemViewHolder extends RecyclerView.ViewHolder{
            int position;
            private TextView noteCheckTextView;
            private CheckBox noteCheckBox;

            NoteChecItemViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener, final ItemLongClickListener itemLongClickListener) {
                super( itemView );

                noteCheckTextView = itemView.findViewById( R.id.note_check_text );
                noteCheckBox = itemView.findViewById( R.id.note_check_box);

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
    }

    //NotePhotoViewHolder
     class NotePhotoItemViewHolder extends RecyclerView.ViewHolder{
        int position;
        private ImageView notePhotoImageView;
        private TextView notePhotoTextViewt;
        NotePhotoItemViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener, final ItemLongClickListener itemLongClickListener) {
            super( itemView );

            notePhotoImageView = itemView.findViewById(R.id.note_photo);
            notePhotoTextViewt = itemView.findViewById(R.id.note_photo_text);

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

    }
}

