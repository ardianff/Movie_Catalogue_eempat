package com.android.moviecatalogue.Database.Helper;

import android.database.Cursor;

import com.android.moviecatalogue.Model.Favorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.CATEGORY;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.ID;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.POSTER;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.TITLE;

public class MappingHelper {
    public static ArrayList<Favorite> getMovieData(Cursor cursor) {
        ArrayList<Favorite> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(_ID));
            int mId = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY));

            if (category.equals("movie")) {
                list.add(new Favorite(id, mId , category, title, overview, poster));
            }
        }
        return list;
    }
    public static ArrayList<Favorite> getTvData(Cursor cursor) {
        ArrayList<Favorite> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            int mId = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY));

            if (category.equals("tv")) {
                list.add(new Favorite(id, mId , category, title, overview, poster));
            }
        }
        return list;
    }
}
