package com.example.frank.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewDebug;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {


    private ViewPager pager;
    private ViewDragHelper dragHelper;
    private ViewDebug debug;
    private ScrollView scrollView;
    WebViewClient webViewClient;
    WebView webView;
    WebChromeClient chromeClient;
    private VelocityTracker velocityTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (velocityTracker==null){
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);

        switch (action){
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1,0.1f);


                Log.d("TAG","velocityTracker:"+velocityTracker.getXVelocity());
                velocityTracker.computeCurrentVelocity(1000);


                Log.d("TAG","velocityTracker:"+velocityTracker.getXVelocity());

                break;

        }

        return super.onTouchEvent(event);
    }
}
