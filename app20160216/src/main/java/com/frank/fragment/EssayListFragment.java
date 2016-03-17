package com.frank.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.frank.myapplication.EssayDetialActivity;
import com.example.frank.myapplication.EssayEditActivity;
import com.example.frank.myapplication.R;
import com.frank.adapter.EssayListAdapter;
import com.frank.dbutils.DBManager;
import com.frank.entity.Essay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/2/19.
 */
public class EssayListFragment extends Fragment implements View.OnClickListener{

    private View view;
    private ImageView addEssay;
    private ListView listView;
    private EssayListAdapter essayListAdapter;
    private List<Essay> essays;
    private DBManager manager;

    private String[] essayTitle = {"新人报到！","项目招商！","出售大量挖掘机！","刚刚完工来秀一波。。。","来给大家拜年啦！","好烦呐，老板又不发工资。"};
    private String[] essayBody = {"新人报到！","项目招商！","出售大量挖掘机！","刚刚完工来秀一波。。。","来给大家拜年啦！","好烦呐，老板又不发工资。"};




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_essaylist,container,false);
        initView(view);

        return view;
    }


    private void initView(View view){
        manager = new DBManager(getActivity());
        view.findViewById(R.id.datalist);
        addEssay = (ImageView) view.findViewById(R.id.addessay);
        addEssay.setColorFilter(0xffffffff);
        listView = (ListView) view.findViewById(R.id.datalist);

        essays = new ArrayList<Essay>();

        for(int i=0;i<10;i++){
            essays.add(new Essay("冯可可",essayTitle[i%6],essayBody[i%6],"2016-2-"+i,i%2));
        }

        essays.addAll(manager.queryEssay());


        essayListAdapter = new EssayListAdapter(getActivity(),essays);


        listView.setAdapter(essayListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EssayDetialActivity.class);
                intent.putExtra("Essay", essays.get(position));
                startActivity(intent);
            }
        });

        addEssay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addessay:
                Intent intent = new Intent(getActivity(), EssayEditActivity.class);
                getActivity().startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.dbClose();
    }

    public void refreshList(){
        if (essayListAdapter!=null){
            essayListAdapter.refreshDatalist();
        }
    }


}
