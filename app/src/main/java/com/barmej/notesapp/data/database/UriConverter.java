package com.barmej.notesapp.data.database;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriConverter {

   @TypeConverter
   public static String stringToUri(Uri uri){
       return uri.toString();
   }

   @TypeConverter
   public static Uri uriToString(String value){
       return Uri.parse( value );
   }

}
