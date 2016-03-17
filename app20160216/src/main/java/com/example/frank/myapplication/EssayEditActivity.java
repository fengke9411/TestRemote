package com.example.frank.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frank.dbutils.DBManager;
import com.frank.entity.Essay;
import com.frank.utils.Utils;

import java.util.Date;

/**
 * Created by frank on 2016/2/17.
 */
public class EssayEditActivity extends Activity implements View.OnClickListener{


    private EditText addTitle,addBody;
    private Button submit;
    private TextView adddate;
    private  DBManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.essayedit_layout);
        manager = new DBManager(this);
        init();
    }

    public void init(){
        addTitle = (EditText) findViewById(R.id.addtitle);
        addBody = (EditText) findViewById(R.id.addbody);
        adddate = (TextView) findViewById(R.id.adddate);
        Date date = new Date();

        adddate.setText(Utils.getFormatDate());
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.submit:
                manager.addEssay(new Essay("frank",addTitle.getText().toString(),addBody.getText().toString(),adddate.getText().toString(),1));
                setResult(RESULT_OK);
                finish();
                Toast.makeText(this,"发表成功！",Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.dbClose();
    }
}
