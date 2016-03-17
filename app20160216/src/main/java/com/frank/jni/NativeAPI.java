package com.frank.jni;

import java.io.InputStream;

/**
 * Created by frank on 2016/3/15.
 */
public class NativeAPI {

    public static native String getJsonFromFile(InputStream inputStream);

}
