package com.frank.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by frank on 2016/2/29.
 */
public class CircleImageView extends ImageView {



    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null==drawable){
            return;
        }
        if (getWidth()==0||getHeight()==0){
            return;
        }
        Bitmap b = ((BitmapDrawable)drawable).getBitmap();
        if (null==b){
            return;
        }

        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        int w = getWidth(),h = getHeight();
        Bitmap cirbitmap = getCroppedBitmap(bitmap,w);



        canvas.drawBitmap(cirbitmap, 0, 0, null);


    }


    private  Bitmap getCroppedBitmap(Bitmap bitmap,int raius){
        Bitmap bmp;
        if (bitmap.getWidth()!=raius||bitmap.getHeight()!=raius){
            bmp = Bitmap.createScaledBitmap(bitmap,raius,raius,false);

        }else {
            bmp = bitmap;
        }


        Bitmap output = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect rect = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(Color.parseColor("#bab399"));
        canvas.drawCircle(bmp.getWidth() / 2, bmp.getHeight() / 2, bmp.getWidth() / 2 , paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp,rect,rect,paint);
        return output;

    }
}
