package com.android.moviecatalogue.Database.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.moviecatalogue.Database.DatabaseContract;

import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.TABLE_DATA;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_favorite";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_DATA,
            DatabaseContract.FavoriteColumns._ID,
            DatabaseContract.FavoriteColumns.ID,
            DatabaseContract.FavoriteColumns.CATEGORY,
            DatabaseContract.FavoriteColumns.TITLE,
            DatabaseContract.FavoriteColumns.POSTER,
            DatabaseContract.FavoriteColumns.OVERVIEW
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(sqLiteDatabase);
    }
}

