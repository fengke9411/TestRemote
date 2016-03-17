package com.frank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frank.dbutils.DBManager;
import com.frank.entity.Essay;
import com.example.frank.myapplication.R;

import java.util.List;

/**
 * Created by frank on 2016/2/16.
 */
public class EssayListAdapter extends BaseAdapter {

    private Context context;
    private List<Essay> essays;
    private DBManager manager;

    public EssayListAdapter(Context context,List<Essay> essays){
        this.context = context;
        this.essays = essays;
        manager = new DBManager(context);
        Log.d("essays",essays.toString());

    }

    public void refreshDatalist(){
        essays = manager.queryEssay();
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return essays.size();
    }

    @Override
    public Object getItem(int position) {
        return essays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Essay essayitem = essays.get(position);

        Log.d("Essay", essayitem.toString());

        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_essay,null);
            holder.essayTitle = (TextView) convertView.findViewById(R.id.essaytitle);
            holder.essayBody = (TextView) convertView.findViewById(R.id.essaybody);
            holder.essayTime = (TextView) convertView.findViewById(R.id.essaytime);
            holder.essayType = (ImageView) convertView.findViewById(R.id.essaytype);
            holder.commentNum = (TextView) convertView.findViewById(R.id.commentnum);
            holder.scanNum = (TextView) convertView.findViewById(R.id.scannum);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String title = essayitem.getEssayTitle();
        String body = essayitem.getEssayBody();

        holder.essayTitle.setText(title.length()>12?title.substring(0,12)+"...":title);
        holder.essayBody.setText(body.length()>20?body.substring(0,20):body);
        holder.essayTime.setText(essayitem.getPublishDate());


        if(essayitem.getEssayType()==1){
            holder.essayType.setImageResource(R.drawable.lts);
        }else {
            holder.essayType.setImageResource(R.drawable.ltr);
        }


        return convertView;
    }



    class ViewHolder{
        TextView essayTitle;  //标题
        TextView essayBody;     //内容
        TextView essayTime;     //时间
        ImageView essayType;    //类型
        TextView commentNum;    //评论数量
        TextView scanNum;       //浏览数

    }
}
