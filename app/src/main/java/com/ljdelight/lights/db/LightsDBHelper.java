package com.ljdelight.lights.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ljdelight.lights.generated.Comment;
import com.ljdelight.lights.generated.Lights;
import com.ljdelight.lights.generated.Location;
import com.ljdelight.lights.generated.Meta;
import com.ljdelight.lights.generated.TaggedLocation;
import com.ljdelight.lights.generated.TaggedLocationWithMeta;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ljdelight on 4/24/16.
 */
public class LightsDBHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lights.db";

    private SQLiteDatabase mDatabase;

    public LightsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDatabase = this.getWritableDatabase();
        //onUpgrade(mDatabase, 1, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "In LightsDBHelper onCreate");
        db.execSQL(LightsContract.LocationsEntry.CREATE_TABLE);
        db.execSQL(LightsContract.CommentsEntry.CREATE_TABLE);
        db.execSQL(LightsContract.LocationsCommentsEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "In LightsDBHelper onUpgrade");
        db.execSQL(LightsContract.LocationsCommentsEntry.DELETE_TABLE);
        db.execSQL(LightsContract.LocationsEntry.DELETE_TABLE);
        db.execSQL(LightsContract.CommentsEntry.DELETE_TABLE);
        onCreate(db);
    }

    public boolean hasValidSQLCache() {
        Log.d(TAG, "In hasValidSQLCache");
        int count = -1;
        String countQuery = "SELECT count(id) from " + LightsContract.LocationsEntry.TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(countQuery, null);
        try {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        } finally {
            cursor.close();
        }
        Log.d(TAG, "Local tables have " + count + " entries in the cache");
        return count > 0;
    }

    public void insertAll(List<TaggedLocationWithMeta> locations) {
        Log.d(TAG, "In insertAll");

        mDatabase.beginTransaction();
        for (TaggedLocationWithMeta tloc : locations) {
            Log.d(TAG, "Inserting " + tloc.tag.uid);
            this.insertLocation(tloc.tag.uid, tloc.tag.location.getLat(), tloc.tag.location.getLng(), tloc.tag.votes);
            for (Comment comment : tloc.meta.comments) {
                this.insertComment(tloc.tag.uid, comment.id, comment.comment, comment.votes);
            }
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public void insertLocation(long locationId, double lat, double lng, long votes) {
        ContentValues values = new ContentValues();
        values.put(LightsContract.LocationsEntry.COLUMN_NAME_ID, locationId);
        values.put(LightsContract.LocationsEntry.COLUMN_NAME_LAT, lat);
        values.put(LightsContract.LocationsEntry.COLUMN_NAME_LNG, lng);
        values.put(LightsContract.LocationsEntry.COLUMN_NAME_VOTES, votes);

        mDatabase.insert(LightsContract.LocationsEntry.TABLE_NAME, null, values);
    }


    public void insertComment(long locationId, long commentId, String comment, int commentVotes) {
        ContentValues commentValues = new ContentValues();
        commentValues.put(LightsContract.CommentsEntry.COLUMN_NAME_ID, commentId);
        commentValues.put(LightsContract.CommentsEntry.COLUMN_NAME_COMMENT, comment);
        commentValues.put(LightsContract.CommentsEntry.COLUMN_NAME_VOTES, commentVotes);
        mDatabase.insert(LightsContract.CommentsEntry.TABLE_NAME, null, commentValues);

        ContentValues joinValues = new ContentValues();
        joinValues.put(LightsContract.LocationsCommentsEntry.COLUMN_NAME_LOCATION_ID, locationId);
        joinValues.put(LightsContract.LocationsCommentsEntry.COLUMN_NAME_COMMENT_ID, commentId);
        mDatabase.insert(LightsContract.LocationsCommentsEntry.TABLE_NAME, null, joinValues);
    }

    private static final String COMMENTS_JOIN_LOCCOM =
            " INNER JOIN locations_comments ON comments.id=locations_comments.comment_id ";

    public List<Comment> getComments(long locationId)
    {
        List<Comment> comments = new LinkedList<>();
        String[] allCommentsColumns = {
                LightsContract.CommentsEntry.COLUMN_NAME_ID,
                LightsContract.CommentsEntry.COLUMN_NAME_COMMENT,
                LightsContract.CommentsEntry.COLUMN_NAME_VOTES,
        };
        Cursor commentsJoinLocations = mDatabase.query(
                LightsContract.CommentsEntry.TABLE_NAME + COMMENTS_JOIN_LOCCOM,
                allCommentsColumns,
                "locations_comments.location_id=?",
                new String[]{Long.toString(locationId)},
                null,
                null,
                null,
                null);

        try {
            while (commentsJoinLocations.moveToNext()) {
                long id = commentsJoinLocations.getLong(commentsJoinLocations.getColumnIndexOrThrow(LightsContract.CommentsEntry.COLUMN_NAME_ID));
                int votes = commentsJoinLocations.getInt(commentsJoinLocations.getColumnIndexOrThrow(LightsContract.CommentsEntry.COLUMN_NAME_VOTES));
                String comment = commentsJoinLocations.getString(commentsJoinLocations.getColumnIndexOrThrow(LightsContract.CommentsEntry.COLUMN_NAME_COMMENT));

                comments.add(new Comment(votes, comment, id));
            }
        } finally {
            commentsJoinLocations.close();
        }
        return comments;
    }

    public List<TaggedLocation> getLocations() {
        List<TaggedLocation> result = new LinkedList<>();

        String[] allLocationColumns = {
                LightsContract.LocationsEntry.COLUMN_NAME_ID,
                LightsContract.LocationsEntry.COLUMN_NAME_LAT,
                LightsContract.LocationsEntry.COLUMN_NAME_LNG,
                LightsContract.LocationsEntry.COLUMN_NAME_VOTES
        };
        Cursor locations = mDatabase.query(
                LightsContract.LocationsEntry.TABLE_NAME,
                allLocationColumns,
                null, null, null, null, null, null);
        try {
            Log.d(TAG, "Local sqlite table has " + locations.getCount() + " locations");
            while (locations.moveToNext()) {
                long id = locations.getLong(locations.getColumnIndexOrThrow(LightsContract.LocationsEntry.COLUMN_NAME_ID));
                double lat = locations.getDouble(locations.getColumnIndexOrThrow(LightsContract.LocationsEntry.COLUMN_NAME_LAT));
                double lng = locations.getDouble(locations.getColumnIndexOrThrow(LightsContract.LocationsEntry.COLUMN_NAME_LNG));
                int votes = locations.getInt(locations.getColumnIndexOrThrow(LightsContract.LocationsEntry.COLUMN_NAME_VOTES));

                TaggedLocation tag = new TaggedLocation(id, new Location(lat, lng), votes);
                //Log.d(TAG, "Location: " + tag.toString());
                result.add(tag);
            }
        } finally {
            locations.close();
        }

        return result;
    }


    public List<TaggedLocationWithMeta> getLocationsWithMeta() {
        Log.d(TAG, "In method getLocationsWithMeta()");

        List<TaggedLocation> tags = this.getLocations();
        List<TaggedLocationWithMeta> tagsMeta = new LinkedList<>();

        String[] allCommentsColumns = {
                LightsContract.CommentsEntry.COLUMN_NAME_ID,
                LightsContract.CommentsEntry.COLUMN_NAME_COMMENT,
                LightsContract.CommentsEntry.COLUMN_NAME_VOTES,
        };

        for (TaggedLocation tag : tags) {
            // Get the comments from the current tagged location
            Cursor commentsJoinLocations = mDatabase.query(
                    LightsContract.CommentsEntry.TABLE_NAME + COMMENTS_JOIN_LOCCOM,
                    allCommentsColumns,
                    "locations_comments.location_id=?",
                    new String[]{Long.toString(tag.getUid())},
                    null,
                    null,
                    null,
                    null);

            try {
                List<Comment> commentList = new LinkedList<>();
                while (commentsJoinLocations.moveToNext()) {
                    long id = commentsJoinLocations.getLong(commentsJoinLocations.getColumnIndexOrThrow(LightsContract.CommentsEntry.COLUMN_NAME_ID));
                    int votes = commentsJoinLocations.getInt(commentsJoinLocations.getColumnIndexOrThrow(LightsContract.CommentsEntry.COLUMN_NAME_VOTES));
                    String comment = commentsJoinLocations.getString(commentsJoinLocations.getColumnIndexOrThrow(LightsContract.CommentsEntry.COLUMN_NAME_COMMENT));

                    commentList.add(new Comment(votes, comment, id));
                }

                TaggedLocationWithMeta tlwm = new TaggedLocationWithMeta(tag, new Meta(commentList));
                //Log.d(TAG, "Tagged location: " + tlwm.toString());
                tagsMeta.add(tlwm);
            } finally {
                commentsJoinLocations.close();
            }
        }
        return tagsMeta;
    }
}
