package com.barmej.notesapp.data;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class MyBindingAdapter {

    @BindingAdapter( "imageRes" )
    public static void setImageRes(ImageView imageView, int resId) {
        imageView.setImageResource( resId );
    }

}
