package com.ljdelight.lights.db;

import android.provider.BaseColumns;

public class LightsContract {
    private LightsContract() {}

    public static abstract class LocationsEntry implements BaseColumns {
        public static final String TABLE_NAME = "locations";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LNG = "lng";
        public static final String COLUMN_NAME_VOTES = "votes";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID
                + " INTEGER," + COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL," + COLUMN_NAME_LAT
                + " REAL NOT NULL," + COLUMN_NAME_LNG + " REAL NOT NULL," + COLUMN_NAME_VOTES
                + " INTEGER NOT NULL DEFAULT 0"
                + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class CommentsEntry implements BaseColumns {
        public static final String TABLE_NAME = "comments";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_COMMENT = "comment";
        public static final String COLUMN_NAME_VOTES = "votes";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID
                + " INTEGER," + COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + COLUMN_NAME_COMMENT + " TEXT NOT NULL," + COLUMN_NAME_VOTES
                + " INTEGER NOT NULL DEFAULT 0"
                + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class LocationsCommentsEntry implements BaseColumns {
        public static final String TABLE_NAME = "locations_comments";
        public static final String COLUMN_NAME_LOCATION_ID = "location_id";
        public static final String COLUMN_NAME_COMMENT_ID = "comment_id";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID
                + " INTEGER," + COLUMN_NAME_LOCATION_ID + " INTEGER NOT NULL,"
                + COLUMN_NAME_COMMENT_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + COLUMN_NAME_LOCATION_ID + ") REFERENCES "
                + LocationsEntry.TABLE_NAME + " (" + LocationsEntry.COLUMN_NAME_ID + "),"
                + "FOREIGN KEY (" + COLUMN_NAME_COMMENT_ID + ") REFERENCES "
                + CommentsEntry.TABLE_NAME + " (" + CommentsEntry.COLUMN_NAME_ID + ")"
                + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
