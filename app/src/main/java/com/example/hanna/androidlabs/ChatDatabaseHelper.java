package com.example.hanna.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Messages.db";
    public static int VERSION_NUM = 2;

    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "message";
    public static final String MESSAGE_TABLE_NAME  = "messages";
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
}
