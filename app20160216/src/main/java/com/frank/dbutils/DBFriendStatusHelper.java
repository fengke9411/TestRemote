package com.frank.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by frank on 2016/2/17.
 */
public class DBFriendStatusHelper extends SQLiteOpenHelper{


    public static final String DBNAME="app.db";
    public static final int DBVERSION = 1;


    public DBFriendStatusHelper(Context context) {
        super(context,DBNAME,null,DBVERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS friend_status(_id INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR,fsbody VARCHAR,fstime TEXT,fspraisenum INTEGER,fsheadimg TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE essay ADD COLUMN other STRING");
    }
}
