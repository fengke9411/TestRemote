package com.frank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.myapplication.R;
import com.frank.utils.ResourceUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 2016/3/11.
 */
public class FunctionGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> funLists;

    private LayoutInflater inflater;
    public  FunctionGridViewAdapter(Context context,List<Map<String,String>> funLists){
        this.context = context;
        this.funLists = funLists;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return funLists.size();
    }

    @Override
    public Object getItem(int position) {
        return funLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Map<String,String> fun = funLists.get(position);

        ViewHolder viewHolder =null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.functionitem_layout,null);
            viewHolder.funIcon = (ImageView) convertView.findViewById(R.id.functionIcon);
            viewHolder.funName = (TextView) convertView.findViewById(R.id.functionDes);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.funIcon.setImageResource(ResourceUtil.getDrawableId(context,fun.get("icon")));
        viewHolder.funName.setText(fun.get("name"));


        return convertView;
    }


    class ViewHolder{
        ImageView funIcon;
        TextView funName;
    }
}
