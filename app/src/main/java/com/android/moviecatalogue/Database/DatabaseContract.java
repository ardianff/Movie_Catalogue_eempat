package com.android.moviecatalogue.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHOR = "com.android.moviecatalogue";
    private static final String SCHEME = "content";

    private DatabaseContract() {

    }

    public static final class FavoriteColumns implements BaseColumns {
        public static final String TABLE_DATA = "db_favorite";
        public static final String ID = "mId";
        public static final String CATEGORY = "category";
        public static final String POSTER = "poster";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHOR)
                .appendPath(TABLE_DATA)
                .build();
    }
}