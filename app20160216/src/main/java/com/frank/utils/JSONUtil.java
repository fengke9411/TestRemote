package com.frank.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by frank on 2016/2/29.
 */
public class JSONUtil {


    /**
     * String2JSon
     *
     * @param s
     * @return
     */
    public static JSONObject getJSONObject(String s) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public static JSONArray getJSONArray(JSONObject jsonObject, String name) {
        JSONArray array = null;
        try {
            array = jsonObject.getJSONArray(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static String getString(JSONObject jsonObject, String name) {

        String result = "";
        try {
            result = jsonObject.getString(name);
        } catch (JSONException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }


    public static String getString(JSONArray array, int i, String key) {
        String result = "";
        try {
            JSONObject object = array.getJSONObject(i);
            result = getString(object, key);
        } catch (JSONException e) {
            result = "";
            e.printStackTrace();
        }

        return result;
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String key) {

        JSONObject object = null;
        try {
            object = jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static JSONObject getJSONObject(JSONArray jsonArray, int index) {

        JSONObject object = null;
        try {
            object = jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 从String获取JSONArray
     *
     * @param array
     * @return
     */
    public static JSONArray getJSONArray(String array) {
        JSONArray array1 = null;
        try {
            array1 = new JSONArray(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array1;
    }


    public static JSONArray sortArrayByData(JSONArray array, String bywhat) {
        List<JSONObject> objects = new ArrayList<>();
        JSONObject jsonObject = null;
        for (int i = 0; i < array.length(); i++) {
            jsonObject = JSONUtil.getJSONObject(array, i);
            objects.add(jsonObject);
        }

        JSONComparator comparator = new JSONComparator(bywhat);
        Collections.sort(objects, comparator);
        array = new JSONArray();
        for (int i = 0; i < objects.size(); i++) {
            array.put(objects.get(i));
        }

        return array;
    }


    static class JSONComparator implements Comparator<JSONObject> {
        String dataName = "";

        JSONComparator(String dataName) {
            this.dataName = dataName;
        }

        @Override
        public int compare(JSONObject lhs, JSONObject rhs) {
            String data1 = JSONUtil.getString(lhs, dataName);
            String data2 = JSONUtil.getString(rhs, dataName);
            if (data1.compareTo(data2) < 0) {
                return -1;
            } else if (data1.compareTo(data2) > 0) {
                return 1;
            }

            return 0;
        }
    }
}
