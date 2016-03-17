package com.frank.utils;

import android.content.Context;
import android.util.Xml;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.internal.Util;

/**
 * Created by frank on 2016/3/16.
 */
public class FileUtil {


    //写文件到程序根目录
    public static void writeFile(Context context,String fileName,String content){
        try {
            FileOutputStream fout = context.openFileOutput(fileName,context.MODE_PRIVATE);
            byte[] bytes = content.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从asset读取文件
     * @param context
     * @param fileName
     * @return
     */
    public static String readFileFromAsset(Context context,String fileName){
        String result="";
        long st=System.currentTimeMillis();
        try {
            InputStream inputStream = context.getAssets().open(fileName);

            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line="";
            while ((line = bufferedReader.readLine())!=null){
                result +=line;
            }

//            int length = inputStream.available();
//            byte[] bytes = new byte[length];
//            inputStream.read(bytes);
//            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.Logd("读文件耗时："+(System.currentTimeMillis()-st));
        return result;
    }
}
