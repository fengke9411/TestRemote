package com.frank.application;

import android.app.Application;

import com.easemob.chat.EMChat;

/**
 * Created by frank on 2016/2/19.
 */
public class MyApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        EMChat.getInstance().init(this);
        /**
         * debugMode == true 时为打开，sdk 会在log里输入调试信息
         * @param debugMode
         * 在做代码混淆的时候需要设置成false
         */
        EMChat.getInstance().setDebugMode(true);

    }
}
