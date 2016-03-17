package com.frank.dbutils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.frank.entity.Essay;
import com.frank.entity.FriendStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/2/17.
 */
public class DBFSManager {
    private DBFriendStatusHelper helper;
    private SQLiteDatabase database;

    public DBFSManager(Context context){
        helper = new DBFriendStatusHelper(context);
        database = helper.getWritableDatabase();
    }


    public void addFS(FriendStatus status){
            database.beginTransaction();
        try {
            database.execSQL("INSERT INTO essay VALUES(null,?,?,?,?,?)", new Object[]{status.getName(), status.getFsBody(), status.getFsTime(), status.getFsPraiseNum(), status.getHeadViewImgSrc()});
            database.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();    //结束事务
        }
    }

    public List<FriendStatus> queryAllFS(){
        List<FriendStatus> statuses = new ArrayList<FriendStatus>();
        Cursor c = database.rawQuery("SELECT * FROM essay",null);

        while (c.moveToNext()){

            FriendStatus status = new FriendStatus();
            status.setName(c.getString(c.getColumnIndex("name")));
            status.setFsBody(c.getString(c.getColumnIndex("fsbody")));
            status.setFsTime(c.getString(c.getColumnIndex("fstime")));
            status.setFsPraiseNum(c.getInt(c.getColumnIndex("fspraisenum")));
            status.setHeadViewImgSrc(c.getString(c.getColumnIndex("fsheadimg")));

            statuses.add(status);
        }
        c.close();
        return statuses;
    }


    public void dbClose(){
        database.close();
    }

}
