package com.frank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.frank.myapplication.R;
import com.frank.utils.JSONUtil;
import com.frank.utils.Trans2PinYin;
import com.frank.utils.Utils;
import com.frank.views.NoScrollGridView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by frank on 2016/3/16.
 */
public class CityListExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private JSONArray rootJsonarray;
    private LayoutInflater inflater;
    private ExpandableListView listView;

    public  CityListExpandAdapter(Context context,JSONArray rootJsonarray){
        this.context = context;
        this.rootJsonarray = rootJsonarray;
        inflater = LayoutInflater.from(context);

    }


    public void setListView(ExpandableListView listView){
        this.listView = listView;

    }

    @Override
    public int getGroupCount() {
        return rootJsonarray.length();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        JSONArray subArray = JSONUtil.getJSONArray(JSONUtil.getJSONObject(rootJsonarray,groupPosition), "sub");
        return subArray==null?0:1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return JSONUtil.getJSONObject(rootJsonarray,groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        JSONArray subArray = JSONUtil.getJSONArray(JSONUtil.getJSONObject(rootJsonarray,groupPosition), "sub");

        return JSONUtil.getJSONObject(subArray,childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Utils.Logd("展开："+groupPosition);
       if( listView!=null){
           for (int i=0;i<getGroupCount();i++){
               Utils.Logd("是否展开："+i+listView.isGroupExpanded(i));
               if ((i!=groupPosition)&&listView.isGroupExpanded(i)){
                   listView.collapseGroup(i);
               }
           }

//           listView.setSelectedChild(groupPosition,0,false);
       }
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        JSONObject cityJson = JSONUtil.getJSONObject(rootJsonarray,groupPosition);

        ViewHolder holder = null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_citylist,null);
            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.cityName);

            convertView.setTag(holder);


        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        String cityName = JSONUtil.getString(cityJson, "name");
       Utils.Logd(cityName+"   《》   "+Trans2PinYin.trans2PinYin(cityName));

        holder.cityName.setText(cityName);

        if (JSONUtil.getJSONArray(cityJson,"sub")==null){
            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }


        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        JSONArray subArray = JSONUtil.getJSONArray(JSONUtil.getJSONObject(rootJsonarray, groupPosition), "sub");

//        JSONObject subObject =JSONUtil.getJSONObject(subArray, childPosition);
        NoScrollGridView gridView=null;
        if (convertView==null){
            gridView = new NoScrollGridView(context);
            gridView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            gridView.setNumColumns(3);

//            convertView = inflater.inflate(R.layout.item_citylist,null);
//            gridView = (GridView) convertView.findViewById(R.id.subGridView);

        }else{
            gridView = (NoScrollGridView) convertView;
        }
        if(subArray!=null){
            gridView.setAdapter(new SubCityAdapter(context, subArray));

        }


        return gridView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder{
        TextView cityName;
    }





    class SubCityAdapter extends BaseAdapter{

       private Context context;
        private JSONArray array;
        private LayoutInflater inflater;
        public SubCityAdapter(Context context,JSONArray array){
            this.context = context;
            this.array = array;
            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {

            return array.length();
        }

        @Override
        public Object getItem(int position) {
            return JSONUtil.getJSONObject(array,position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Button button;
            if (convertView==null){

                button = new Button(context);
                button.setText(JSONUtil.getString(JSONUtil.getJSONObject(array,position),"name"));
                convertView = button;
            }


            return convertView;
        }

    }
}
