package com.frank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.frank.myapplication.R;
import com.frank.utils.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by frank on 2016/3/15.
 */
public class CityListAdapter extends BaseAdapter {

    private Context context;
    private JSONArray jsonArray;
    private LayoutInflater inflater;

    public CityListAdapter(Context context,JSONArray jsonArray){
        this.context = context;
        this.jsonArray = jsonArray;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return JSONUtil.getJSONObject(jsonArray,position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject cityJson = JSONUtil.getJSONObject(jsonArray,position);

        ViewHolder holder = null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_citylist,null);
            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.cityName);
            holder.cityName.setText(JSONUtil.getString(cityJson,"name"));
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder{
        TextView cityName;
    }
}
