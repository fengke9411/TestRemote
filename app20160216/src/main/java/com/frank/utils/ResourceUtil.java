package com.frank.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by frank on 2016/3/11.
 */
public class ResourceUtil {

    /**
     * 通过资源名称获取资源id
     * @param name
     * @return
     */
    public static int getDrawableId(Context context,String name){
//
//        Resources resources = context.getResources();
//        resources.getIdentifier(name,"drawable",context.getPackageName());
        return context.getResources().getIdentifier(name,"drawable",context.getPackageName());
    }

}
