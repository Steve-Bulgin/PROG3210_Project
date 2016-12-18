package io.github.steve_bulgin.prog3210_finalproject;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import io.github.steve_bulgin.prog3210_finalproject.RSSItem;
/**
 * Created by Stevie on 12/13/2016.
 */
public class newsReaderDB {
    RSSItem insertRSSItem = new RSSItem();
    // database constants
    public static final String DB_NAME = "newsReader.db";
    public static final int DB_VERSION = 1;

    //table constants
    public static final String newsReader_TABLE = "savedStories";

    public static final String storyID = "_id";
    public static final int storyID_COL = 0;

    public static final String storyName = "storyName";
    public static final int storyName_COL = 2;

    public static final String storyDescription = "Description";
    public static final int storyDescription_COL = 3;

    public static final String storyLink = "storyLink";
    public static final int storyLink_COL = 4;

    public static final String storySource = "storySource";
    public static final int storySource_COL = 5;

    public static final String CREATE_newsReader_TABLE =
            "CREATE TABLE " + newsReader_TABLE + " (" +
                    storyID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    storyName + " INTEGER, " +
                    storyDescription + " TEXT, " +
                    storyLink + " TEXT, " +
                    storySource + " TEXT);";

    public static final String DROP_newReader_TABLE =
            "DROP TABLE IF EXISTS " + newsReader_TABLE;


    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_newsReader_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("Task list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(newsReaderDB.DROP_newReader_TABLE);
            onCreate(db);
        }
    }
    // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public newsReaderDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    public ArrayList<RSSItem> getLists() {
        ArrayList<RSSItem> lists = new ArrayList<RSSItem>();
        openReadableDB();
        Cursor cursor = db.query(newsReader_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            RSSItem list = new RSSItem();
            list.setId(Integer.parseInt(cursor.getString(storyID_COL)));
            list.setTitle(cursor.getString(storyName_COL));
            list.setDescription(cursor.getString(storyDescription_COL));
            list.setLink(cursor.getString(storyLink_COL));
            list.setSource(cursor.getString(storySource_COL));

            lists.add(list);
        }
        if (cursor != null)
            cursor.close();
        closeDB();

        return lists;
    }

    public RSSItem getList(String name) {
        String where = storyName + "= ?";
        String[] whereArgs = { name };

        openReadableDB();
        Cursor cursor = db.query(newsReader_TABLE, null,
                where, whereArgs, null, null, null);
        RSSItem list = null;
        cursor.moveToFirst();
        list = new RSSItem();

        list.setId(cursor.getInt(storyID_COL));
        list.setTitle(cursor.getString(storyName_COL));
        list.setDescription(cursor.getString(storyDescription_COL));
        list.setLink(cursor.getString(storyLink_COL));
        list.setSource(cursor.getString(storySource_COL));
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return list;
    }

    public ArrayList<RSSItem> getStories(String storyName) {

        String where =
                storyID + " = ?";
        int listID = getList(storyName).getId();
        String[] whereArgs = { Integer.toString(listID) };

        this.openReadableDB();
        Cursor cursor = db.query(newsReader_TABLE, null,
                where, whereArgs,
                null, null, null);
        ArrayList<RSSItem> tasks = new ArrayList<RSSItem>();
        while (cursor.moveToNext()) {
            tasks.add(getStoryFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return tasks;
    }


    private static RSSItem getStoryFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                RSSItem story = new RSSItem();
                story.setTitle(cursor.getString(storyName_COL));
                story.setSource(cursor.getString(storySource_COL));
                story.setDescription(cursor.getString(storyDescription_COL));
                story.setLink(cursor.getString(storyLink_COL));

                return story;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long insertStory(RSSItem story) {
        ContentValues cv = new ContentValues();
        cv.put(storyName, story.getTitle());
        cv.put(storyDescription, story.getDescription());
        cv.put(storyLink, story.getLink());
        cv.put(storySource, story.getSource());

        this.openWriteableDB();
        long rowID = db.insert(newsReader_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }


    public int deleteStory(long id) {
        String where = storyID+ "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(newsReader_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
}
