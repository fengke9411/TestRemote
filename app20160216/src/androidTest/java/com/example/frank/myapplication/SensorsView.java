package com.example.frank.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by frank on 2016/2/16.
 */
public class SensorsView extends View implements SensorEventListener{

    private Bitmap mBitmap;
    private Paint mPaint = new Paint();
    private Canvas mCanvas = new Canvas();
    private Path mPath = new Path();
    private RectF mRectF = new RectF();
    private float mLastValues[] = new float[3*2];
    private float mOrientationValues[] = new float[3];
    private int mColors[] = new int[3*2];
    private float mLastX ;
    private float mScale[] = new float[2];
    private float mYoffset;
    private float mMaxX;
    private float mSpeed = 1.0f;
    private float mWidth;
    private float mHeight;




    public SensorsView(Context context) {
        super(context);
        initValue();
    }

    public SensorsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }



    private void initValue(){
        mColors[0] = Color.argb(192,255,64,64);
        mColors[1] = Color.argb(192,64,128,64);
        mColors[2] = Color.argb(192,64,64,255);
        mColors[3] = Color.argb(192,64,255,255);
        mColors[4] = Color.argb(192,128,64,128);
        mColors[5] = Color.argb(192,255,255,64);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mRectF.set(-0.5f, -0.5f,0.5f,0.5f);
        mPath.arcTo(mRectF,0,180);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (this){

            if (mBitmap!=null){
                final Paint paint = mPaint;
                final Path path = mPath;
                final int outer = 0xffc0c0c0;
                final int inner = 0xffff7010;
                if (mLastX>mMaxX){
                    mLastX = 0;
                    final Canvas canvas1 = mCanvas;
                    float yoffset = mYoffset;
                    float maxx = mMaxX;
                    float oneG = SensorManager.STANDARD_GRAVITY*mScale[0];
                    paint.setColor(0xffaaaaaa);
                    canvas1.drawColor(0xffffffff);
                    canvas1.drawLine(0,yoffset,maxx,yoffset,paint);
                    canvas1.drawLine(0,yoffset+oneG,maxx,yoffset+oneG,paint);
                    canvas1.drawLine(0,yoffset-oneG,maxx,yoffset-oneG,paint);


                }

                canvas.drawBitmap(mBitmap,0,0,null);
                float values[] = mOrientationValues;
                if (mWidth<mHeight){
                    float w0 = mWidth*0.33333f;
                    float w = w0-32;
                    float x = w0*0.5f;
                    for(int i=0;i<3;i++){
                        canvas.save(Canvas.MATRIX_SAVE_FLAG);
                        canvas.translate(x, w * 0.5f + 0.4f);
                        canvas.save(Canvas.MATRIX_SAVE_FLAG);
                        paint.setColor(outer);
                        canvas.scale(w,w);
                        canvas.drawOval(mRectF,paint);
                        canvas.restore();
                        canvas.scale(w-5,w-5);
                        paint.setColor(inner);
                        canvas.rotate(-values[i]);
                        canvas.drawPath(path,paint);
                        canvas.restore();
                        x+=w0;
                    }
                }else {
                    float h0 = mHeight*0.333333f;
                    float h = h0-32;
                    float y = h0*0.5f;
                    for (int i=0;i<3;i++){
                        canvas.save(Canvas.MATRIX_SAVE_FLAG);
                        canvas.translate(mWidth - (h * 0.5f + 4.0f), y);
                        canvas.save(Canvas.MATRIX_SAVE_FLAG);
                        paint.setColor(outer);
                        canvas.scale(h,h);
                        canvas.drawOval(mRectF,paint);
                        canvas.restore();
                        canvas.scale(h-5,h-5);
                        paint.setColor(inner);
                        canvas.rotate(-values[i]);
                        canvas.drawPath(path,paint);
                        canvas.restore();
                        y+=h0;
                    }
                }
            }
        }


    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
