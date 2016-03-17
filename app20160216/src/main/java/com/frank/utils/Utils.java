package com.frank.utils;

import android.media.session.MediaSession;
import android.util.Log;

import com.easemob.util.HanziToPinyin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by frank on 2016/2/17.
 */
public class Utils {


    /**
     * 获取当前格式化过的时间
     * @return
     */
    public static String getFormatDate(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return formatter.format(new Date());
    }



    public static void Logd(String msg){

        Log.d("Chat",msg);

    }



}
