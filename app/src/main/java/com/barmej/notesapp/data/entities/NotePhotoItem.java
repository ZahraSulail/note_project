package com.barmej.notesapp.data.entities;


import android.net.Uri;
import android.os.Parcel;

import com.barmej.notesapp.Constants;

import androidx.room.Entity;

//NotePhotoItem sub class inherts from Note super class
@Entity(tableName = "note_photo_item")
public class NotePhotoItem extends Note {

    //Uri to get photo uri
    private Uri photo;


     public NotePhotoItem() {
        super();
     }

     public NotePhotoItem(String text, Uri photo){
         super( text, Constants.NOTE__PHOTO_VIEW_TYPE);
         this.photo= photo;
     }



    //setter and getter method
    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }


    protected NotePhotoItem(Parcel in) {
        super(in);
        photo = in.readParcelable( Uri.class.getClassLoader() );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel( dest, flags );
        dest.writeParcelable( photo, flags );
    }

    public static final Creator<NotePhotoItem> CREATOR = new Creator<NotePhotoItem>() {
        @Override
        public NotePhotoItem createFromParcel(Parcel in) {
            return new NotePhotoItem( in );
        }

        @Override
        public NotePhotoItem[] newArray(int size) {
            return new NotePhotoItem[size];
        }
    };

}
