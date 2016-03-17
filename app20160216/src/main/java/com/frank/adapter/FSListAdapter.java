package com.frank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frank.myapplication.R;
import com.frank.entity.FriendStatus;

import java.util.List;

/**
 * Created by frank on 2016/3/8.
 */
public class FSListAdapter extends BaseAdapter {

    private Context context;
    private List<FriendStatus> fsLists;
    private  LayoutInflater inflater;

    public  FSListAdapter(Context context,List<FriendStatus> fsLists){
        this.context = context;
        this.fsLists = fsLists;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return fsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return fsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        FriendStatus status = fsLists.get(position);

        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_fslist,null);
            holder = new ViewHolder();
            holder.fsName = (TextView) convertView.findViewById(R.id.fsName);
            holder.fsBody = (TextView) convertView.findViewById(R.id.fsBody);
            holder.fsHeadView = (ImageView) convertView.findViewById(R.id.fsheadview);
            holder.fsTime = (TextView) convertView.findViewById(R.id.fsTime);
            holder.fsPraise = (TextView) convertView.findViewById(R.id.fsPraise);
            holder.fsComment = (TextView) convertView.findViewById(R.id.fsComment);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.fsName.setText(status.getName());
        holder.fsBody.setText(status.getFsBody());
        holder.fsHeadView.setImageResource(R.drawable.head);
        holder.fsTime.setText(status.getFsTime());
        holder.fsPraise.setText("赞(" + status.getFsPraiseNum() + ")");
        holder.fsComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"评论。。。"+position,Toast.LENGTH_LONG).show();
            }
        });


        return convertView;
    }

    class ViewHolder{
        ImageView fsHeadView;
        TextView fsName;
        TextView fsBody;
        TextView fsTime;
        TextView fsComment;
        TextView fsPraise;

    }
}
