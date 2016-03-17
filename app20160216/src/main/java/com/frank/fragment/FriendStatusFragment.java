package com.frank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.frank.myapplication.R;
import com.frank.adapter.FSListAdapter;
import com.frank.dbutils.DBFSManager;
import com.frank.entity.FriendStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子页面
 * Created by frank on 2016/3/8.
 */
public class FriendStatusFragment extends Fragment {

    private View mainView;
    private ListView fsListView;
    private FSListAdapter fsListAdapter;
    private DBFSManager dbfsManager;
    private List<FriendStatus> fslist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_friendstatus,container,false);
        init(mainView);
        return mainView;
    }

    private void init(View view){
        dbfsManager = new DBFSManager(getActivity());
        fslist = new ArrayList<>();
        fslist.add(new FriendStatus("123", "img", "今天不开心。。。", "15:23", null, 23));
        fslist.addAll(dbfsManager.queryAllFS());

        fsListView = (ListView) view.findViewById(R.id.FSList);
        fsListAdapter = new FSListAdapter(getActivity(),fslist);
        fsListView.setAdapter(fsListAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbfsManager.dbClose();
    }

}
