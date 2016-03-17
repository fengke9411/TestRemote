package com.example.frank.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.frank.entity.Essay;

/**
 * Created by frank on 2016/2/17.
 */
public class EssayDetialActivity extends Activity {

    private TextView showTitle,showDate,showBody,showAuthor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.essaydetial_layout);
        Intent intent = getIntent();
        Essay essay = (Essay) intent.getSerializableExtra("Essay");

        showTitle = (TextView) findViewById(R.id.showtitle);
        showDate = (TextView) findViewById(R.id.showdate);
        showBody = (TextView) findViewById(R.id.showbody);
        showAuthor = (TextView) findViewById(R.id.showauthor);

        showTitle.setText(essay.getEssayTitle());
        showBody.setText(essay.getEssayBody());
        showDate.setText(essay.getPublishDate());
        showAuthor.setText(essay.getAuthor());


    }



}
