package com.android.moviecatalogue.Database.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.TABLE_DATA;

public class FavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_DATA;
    private static FavoriteHelper favoriteHelper;
    private final DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (favoriteHelper == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (favoriteHelper == null) {
                    favoriteHelper = new FavoriteHelper(context);
                }
            }
        }
        return favoriteHelper;
    }

    public void OpenDatabase() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void CloseDatabase() {
        databaseHelper.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor QueryIDProvider(String id) {

        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor QueryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long InsertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }


    public int UpdateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int DeleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}

