package com.frank.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.frank.myapplication.R;
import com.frank.activity.ActivityCityList;
import com.frank.adapter.FunctionGridViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;

    private String mParam1;
    private String mParam2;


    private GridView funGroup;
    private TextView location;

    private FunctionGridViewAdapter adapter;
    private List<Map<String,String>> funLists =null;
    private String[] funName={"家装","土建","设备","水电","涂装","防水","施前","其他"};
    private String[] funIcon={"icon03","icon04","icon05","icon06",
            "icon07","icon08","icon09","icon10"};

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        funGroup = (GridView) view.findViewById(R.id.functionGroup);
        location = (TextView) view.findViewById(R.id.location);
        location.setOnClickListener(this);
        funLists = new ArrayList<>();
        Map<String,String> map1=null;
        for (int i=0;i<8;i++){
             map1= new HashMap<>();
            map1.put("name",funName[i]);
            map1.put("icon",funIcon[i]);
            funLists.add(map1);
        }

        adapter = new FunctionGridViewAdapter(getActivity(),funLists);
        funGroup.setAdapter(adapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location:
                Intent intent = new Intent(getActivity(),ActivityCityList.class);

                startActivity(intent);

                break;
        }
    }
}
