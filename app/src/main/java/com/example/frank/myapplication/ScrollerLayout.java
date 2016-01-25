package com.example.frank.myapplication;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by frank on 2016/1/25.
 */
public class ScrollerLayout extends ViewGroup {
    private Scroller mScroller;
    private  int    mTouchSlop;
    private float mXDown;
    private  float mXMove;
    private float mXLastMove;
    private int leftBorder=0;
    private int rightBorder =0;




    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed){
            int childCount = getChildCount();
            for (int i=0;i<childCount;i++){
                View childView = getChildAt(i);
                childView.layout(i*childView.getMeasuredWidth(),0,(i+1)*childView.getMeasuredWidth(),childView.getMeasuredHeight());

            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount-1).getRight();
        }

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;

            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove-mXDown);
                mXLastMove = mXMove;
                if (diff>mTouchSlop){
                    return  true;
                }
            break;

        }


        return super.onInterceptTouchEvent(ev);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrollx = (int) (mXLastMove-mXMove);
                if (getScaleX()+scrollx<leftBorder){
                    scrollTo(leftBorder,0);
                    return  true;
                }else if(getScrollX()+getWidth()+scrollx>rightBorder){
                    scrollTo(rightBorder-getWidth(),0);
                    return  true;
                }

                scrollBy(scrollx,0);
                mXLastMove = mXMove;
            break;
            case MotionEvent.ACTION_UP:
                int targetIndex = (getScrollX()+getWidth()/2)/getWidth();
                int dx = targetIndex*getWidth()-getScrollX();
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;

        }
        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();

        }
    }
}
