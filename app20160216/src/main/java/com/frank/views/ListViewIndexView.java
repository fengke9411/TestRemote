package com.frank.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.frank.utils.Utils;

/**
 * Created by frank on 2016/3/16.
 */
public class ListViewIndexView extends View {
    private Paint mTextPaint;
    private int viewHeight;
    private int mTextheight = 0;
    private int mTextwidth = 40;
    private int viewWidth = 0;
    private int touchIndex = -1;
    private OnIndexTouchListener indexTouchListener;

    private String indexStr = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public ListViewIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(30);
        setClickable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        Utils.Logd("ViewHeight=" + viewHeight + "       mTextheight=" + mTextheight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();
        mTextheight = viewHeight / indexStr.length();

    }

    public void drawText(Canvas canvas) {

        Rect rect = new Rect();
        for (int i = 0; i < indexStr.length(); i++) {

//            if (i==touchIndex){
//                mTextPaint.setColor(Color.BLUE);
//            }

            String index = indexStr.charAt(i) + "";
            mTextPaint.getTextBounds(index, 0, 1, rect);

            int startx = (viewWidth - rect.width()) / 2;
            int starty = mTextheight * (i + 1) - (mTextheight - rect.height()) / 2;
            canvas.drawText(indexStr.charAt(i) + "", startx, starty, mTextPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float downy = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.GRAY);
                downy = event.getY();

                touchIndex =(int)downy/mTextheight;
                if (indexTouchListener!=null&&touchIndex<indexStr.length()){
                    indexTouchListener.onIndexTouchDown(indexStr.charAt(touchIndex) + "");
                }

                Utils.Logd("ActionDown:" + downy);
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                if (0<moveY&&moveY<viewHeight){
                    touchIndex =(int)moveY/mTextheight;
                    Utils.Logd("ActionMove:index = " + touchIndex);
                    if (indexTouchListener!=null&&touchIndex<indexStr.length()){
                        indexTouchListener.onIndexTouch(indexStr.charAt(touchIndex)+"");
                    }
                }

//                invalidate();
//                Utils.Logd("ActionMove:" + moveY);
                break;
            case MotionEvent.ACTION_UP:
                float upy = event.getY();
                Utils.Logd("ActionUp:" + upy);
                setBackgroundColor(Color.WHITE);
                indexTouchListener.onIndexTouchUp();
                break;

            case MotionEvent.ACTION_CANCEL:
                float cancely = event.getY();
                Utils.Logd("ActionCancel:" + cancely);
                break;
        }


        return super.onTouchEvent(event);
    }

    public void setOnIndexTouchListener(OnIndexTouchListener indexTouchListener){
        this.indexTouchListener = indexTouchListener;

    }


    public interface OnIndexTouchListener{
        void onIndexTouch(String index);
        void onIndexTouchDown(String index);
        void onIndexTouchUp();
    }
}
