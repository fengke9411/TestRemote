package com.frank.dbutils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by frank on 2016/2/17.
 */
public class DBHelper extends SQLiteOpenHelper{


    public static final String DBNAME="app.db";
    public static final int DBVERSION = 1;


    public DBHelper(Context context) {
        super(context,DBNAME,null,DBVERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS essay(_id INTEGER PRIMARY KEY AUTOINCREMENT ,author VARCHAR,essayTitle VARCHAR,essayBody TEXT,publishDate VARCHAR,essayType INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE essay ADD COLUMN other STRING");
    }
}
