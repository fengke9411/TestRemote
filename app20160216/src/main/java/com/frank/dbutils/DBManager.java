package com.frank.dbutils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.frank.entity.Essay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/2/17.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase database;

    public DBManager(Context context){
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }


    public void addEssay(Essay essay){
            database.beginTransaction();
        try {
            database.execSQL("INSERT INTO essay VALUES(null,?,?,?,?,?)", new Object[]{essay.getAuthor(), essay.getEssayTitle(), essay.getEssayBody(), essay.getPublishDate(), essay.getEssayType()});
            database.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();    //结束事务
        }
    }

    public List<Essay> queryEssay(){
        List<Essay> essays = new ArrayList<Essay>();
        Cursor c = database.rawQuery("SELECT * FROM essay",null);

        while (c.moveToNext()){

            Essay essay = new Essay();
            essay.setAuthor(c.getString(c.getColumnIndex("author")));
            essay.setEssayTitle(c.getString(c.getColumnIndex("essayTitle")));
            essay.setEssayBody(c.getString(c.getColumnIndex("essayBody")));
            essay.setPublishDate(c.getString(c.getColumnIndex("publishDate")));
            essay.setEssayType(c.getInt(c.getColumnIndex("essayType")));

            essays.add(essay);
        }
        c.close();
        return essays;
    }


    public void dbClose(){
        database.close();
    }

}
