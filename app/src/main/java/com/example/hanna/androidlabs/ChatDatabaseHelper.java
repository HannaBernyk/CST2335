package com.example.hanna.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ChatDatabaseHelper";
    public static final String DATABASE_NAME = "Messages.db";
    public static int VERSION_NUM = 3;

    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "message";
    public static final String MESSAGE_TABLE_NAME  = "messages";
    public static final String[] COLUMN_ALL = new String[]{KEY_ID, KEY_MESSAGE};

    public static final String CREATE_MESSAGE_TABLE = "create table "
            + MESSAGE_TABLE_NAME + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";

    private static final String DROP_MESSAGE_TABLE = "DROP TABLE IF EXISTS " + MESSAGE_TABLE_NAME;

    //create class
    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    //method creates table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    // old version - when create new base onUpgrade method is called
    // new version -
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL(DROP_MESSAGE_TABLE);
        onCreate(db);
    }

    public void insertMessage(String message) {
        final SQLiteDatabase db = getWritableDatabase();

        final ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);
        long insertId = db.insert(ChatDatabaseHelper.MESSAGE_TABLE_NAME, null, values);

        if (insertId > 0) {
            Log.i(TAG, "Message inserted successfully");
        }
        else {
            Log.e(TAG, "Failed to insert message");
        }
    }

    public List<String> getAllMessages() {
        final List<String> messages = new ArrayList<>();
        final SQLiteDatabase db = getWritableDatabase();

        final Cursor cursor = db.query(MESSAGE_TABLE_NAME, COLUMN_ALL, null, null, null, null, null);
        Log.i(TAG, "Coursor's column count: " + cursor.getColumnCount());

        final int columnIdIndex = cursor.getColumnIndex(KEY_ID);
        final int columnMessageIndex = cursor.getColumnIndex(KEY_MESSAGE);

        Log.i(TAG, "Cursor's Column: " + cursor.getColumnName(columnIdIndex));
        Log.i(TAG, "Cursor's Column: " + cursor.getColumnName(columnMessageIndex));

        while (cursor.moveToNext()){
            final String message = cursor.getString(columnMessageIndex);
            Log.i(TAG, "SQL message: " + message);
            messages.add(message);
        }


        cursor.close();
        return messages;
    }
}
