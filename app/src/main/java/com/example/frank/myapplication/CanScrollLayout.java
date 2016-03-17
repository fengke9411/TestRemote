package com.example.frank.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by frank on 2016/1/26.
 */
public class CanScrollLayout    extends ViewGroup {


    private Scroller mScroller;
    private int mLastY;
    private VelocityTracker mTracker;
    public CanScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed){

            int childCount = getChildCount();

            View childView = getChildAt(0);
            if (childCount>1){
                throw new IllegalStateException("最多一个子view");
            }

            childView.layout(l, t, r, b);

        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();

        View childView = getChildAt(0);
        if (childCount>1){
            throw new IllegalStateException("最多一个子view");
        }

//        measureChild(getChildAt(0),widthMeasureSpec,heightMeasureSpec);


    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                return true;
        }



        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int souX =0;
        int dx=0;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                souX = (int) event.getRawX();


                break;

            case MotionEvent.ACTION_MOVE:
                dx = (int) (event.getRawX()-souX);
                scrollBy(dx,0);

                break;


            case  MotionEvent.ACTION_UP:
                mScroller.startScroll(getScrollX(),0,dx,0);
                break;

        }







        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }

    }
}
