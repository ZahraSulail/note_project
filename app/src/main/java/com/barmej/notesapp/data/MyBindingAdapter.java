package com.barmej.notesapp.data;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class MyBindingAdapter {

    @BindingAdapter( "imageUri" )
    public static void setImageUri(ImageView imageView, Uri photo) {
        imageView.setImageURI( photo );
    }


}
