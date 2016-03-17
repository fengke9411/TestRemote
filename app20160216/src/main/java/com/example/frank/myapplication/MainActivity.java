package com.example.frank.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.frank.dbutils.DBManager;
import com.frank.entity.Essay;
import com.frank.adapter.EssayListAdapter;
import com.frank.fragment.ChatListFragment;
import com.frank.fragment.EssayListFragment;
import com.frank.fragment.FriendStatusFragment;
import com.frank.fragment.HomeFragment;
import com.frank.fragment.MyAccountFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{


    private TextView btn1,btn2,btn3,btn4;
    private ViewPager viewPager;

    private DBManager manager;

    private List<Fragment> fragments;
    private List<TextView> btns;


    public static final int  LOGIN_REQUESTCODE=2;

    private String[] essayTitle = {"新人报到！","项目招商！","出售大量挖掘机！","刚刚完工来秀一波。。。","来给大家拜年啦！","好烦呐，老板又不发工资。"};
    private String[] essayBody = {"新人报到！","项目招商！","出售大量挖掘机！","刚刚完工来秀一波。。。","来给大家拜年啦！","好烦呐，老板又不发工资。"};


    private EssayListFragment essayListFragment;    //发现
    private ChatListFragment chatListFragment;      //消息列表
    private MyAccountFragment myAccountFragment;    //个人中心
    private FriendStatusFragment friendStatusFragment;//圈子
    private HomeFragment homeFragment;  //首页



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init(){

        btn1 = (TextView) findViewById(R.id.btn1);
        btn2 = (TextView) findViewById(R.id.btn2);
        btn3 = (TextView) findViewById(R.id.btn3);
        btn4 = (TextView) findViewById(R.id.btn4);

        btns = new ArrayList<TextView>();
        btns.add(btn1);
        btns.add(btn2);
        btns.add(btn3);
        btns.add(btn4);

        for (TextView tv:btns){
            tv.setOnClickListener(this);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new ArrayList<Fragment>();
        essayListFragment = new EssayListFragment();
        chatListFragment = new ChatListFragment();
        myAccountFragment = new MyAccountFragment();
        friendStatusFragment = new FriendStatusFragment();
        homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        fragments.add(chatListFragment);
        fragments.add(friendStatusFragment);
        fragments.add(myAccountFragment);


        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                resetFootBtn(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewPager.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn1:
                viewPager.setCurrentItem(0);
                resetFootBtn(0);

                break;
            case R.id.btn2:
                viewPager.setCurrentItem(1);
                resetFootBtn(1);
                break;
            case R.id.btn3:
                viewPager.setCurrentItem(2);
                resetFootBtn(2);
                break;
            case R.id.btn4:
                viewPager.setCurrentItem(3);
                resetFootBtn(3);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){


            switch (requestCode){
                //新增文章
                case 1:
                    essayListFragment.refreshList();
                    break;
                //登陆成功结果
                case LOGIN_REQUESTCODE:

                    break;
            }
        }
    }


    private void resetFootBtn(int position){
        for (TextView tv:btns){
            tv.setTextColor(Color.BLACK);
        }
        btns.get(position).setTextColor(Color.BLUE);
    }


}
