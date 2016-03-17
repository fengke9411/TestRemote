package com.frank.activity;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frank.myapplication.R;
import com.frank.adapter.CityListExpandAdapter;
import com.frank.utils.FileUtil;
import com.frank.utils.JSONUtil;
import com.frank.utils.Utils;
import com.frank.views.ListViewIndexView;

import org.json.JSONArray;
import org.json.JSONObject;

//import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 城市列表
 */
public class ActivityCityList extends Activity implements ListViewIndexView.OnIndexTouchListener {
    private RadioGroup radioGroup;

    private ExpandableListView cityListView;
    JSONArray rootArray;
    private CityListExpandAdapter expandAdapter;
    private LayoutTransition layoutTransition; //布局动画
    private LayoutAnimationController animationController;
    private ListViewIndexView indexView;  //listview索引

    private RelativeLayout listParView;
    private TextView addedView;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        cityListView = (ExpandableListView) findViewById(R.id.cityListView);
        indexView = (ListViewIndexView) findViewById(R.id.indexview);
        indexView.setOnIndexTouchListener(this);
        listParView = (RelativeLayout) cityListView.getParent();

        addedView = new TextView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addedView.setLayoutParams(layoutParams);
//        addedView.setGravity(Gravity.CENTER);
        addedView.setPadding(20,20,20,20);
        addedView.setBackgroundColor(Color.GRAY);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//


        String content = FileUtil.readFileFromAsset(ActivityCityList.this, "city.json");
        rootArray = JSONUtil.getJSONArray(content);
//        rootArray = JSONUtil.sortArrayByData(JSONUtil.getJSONArray(content),"pinyin");


//        for(int i=0;i<rootArray.length();i++){
//            JSONObject object = JSONUtil.getJSONObject(rootArray,i);
//            try {
//                object.put("pinyin", Trans2PinYin.trans2PinYin(JSONUtil.getString(object,"name")));
//                JSONArray array = JSONUtil.getJSONArray(object,"sub");
//                if (array!=null){
//                    for (int j=0;j<array.length();j++){
//                        JSONObject object2 = JSONUtil.getJSONObject(array,j);
//                        if (JSONUtil.getString(object2,"name").equals("请选择"))
//                        {
//                            Object o =array.remove(i);
//
//                            continue;
//                        }
//
//                        object2.put("pinyin", Trans2PinYin.trans2PinYin(JSONUtil.getString(object2,"name")));
//                    }
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        Utils.Logd("RootArray:"+rootArray);
        FileUtil.writeFile(this, "city.json", rootArray.toString());



        if (rootArray != null) {
            expandAdapter = new CityListExpandAdapter(this, rootArray);
            expandAdapter.setListView(cityListView);
            cityListView.setAdapter(expandAdapter);
        }


        cityListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(ActivityCityList.this, "点击" + groupPosition, Toast.LENGTH_LONG).show();
                Utils.Logd("点击" + groupPosition);
                Utils.Logd("点击sub ：" + JSONUtil.getJSONArray(JSONUtil.getJSONObject(rootArray, groupPosition), "sub"));
                //点击group，关闭其他

                JSONObject object = JSONUtil.getJSONObject(rootArray, groupPosition);

                if (JSONUtil.getJSONArray(object, "sub")==null||JSONUtil.getJSONArray(object, "sub").length() < 1) {

                    return true;
                }
                return false;           //返回false展开，true不展开
            }
        });

        layoutTransition = new LayoutTransition();
        layoutTransition.setStagger(LayoutTransition.APPEARING, 30);
        layoutTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);

        layoutTransition.setDuration(300);

        ObjectAnimator animatorAppearing = ObjectAnimator.ofFloat(null, "scaleY", 0, 1).setDuration(layoutTransition.getDuration(LayoutTransition.APPEARING));


        layoutTransition.setAnimator(LayoutTransition.APPEARING, animatorAppearing);

        cityListView.setLayoutTransition(layoutTransition);
//        cityListView.setLayoutAnimation(new LayoutAnimationController(ActivityCityList.this,null));

    }


    @Override
    public void onIndexTouch(String index) {        //显示内容
        Utils.Logd("onIndexTouch------->" + index);
//        Toast.makeText(this,index,Toast.LENGTH_SHORT).show();
        if (listParView.indexOfChild(addedView)>0)
            addedView.setText(index);
    }

    @Override
    public void onIndexTouchDown(String index) {//显示View
        if (listParView.indexOfChild(addedView)<0){
            addedView.setText(index);
            listParView.addView(addedView);
        }
    }

    @Override
    public void onIndexTouchUp() {          //隐藏view
        if (listParView.indexOfChild(addedView)>0)
            listParView.removeView(addedView);
    }
}
