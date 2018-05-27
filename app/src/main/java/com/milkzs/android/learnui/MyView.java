package com.milkzs.android.learnui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {

    private String TAG = "MyView";

    private Paint mPaint;
    private int y = 3;

    int height = 0;

    private int Circle_x = 30;
    private int Circle_y = 30;

    public MyView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final int width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();

        mPaint = new Paint();
        mPaint.setStrokeWidth(40);
        Log.d(TAG,"second");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(width < y + 30){
                    y = 3;
                }
                y = y+ 15;
                Log.d(TAG,"run " + y);
                invalidate();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,1000,100);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GREEN);

        canvas.drawLine(10,height/2,30 + y,height/2,mPaint  );

        canvas.drawCircle(Circle_x,Circle_y,50,mPaint);


    }
}
