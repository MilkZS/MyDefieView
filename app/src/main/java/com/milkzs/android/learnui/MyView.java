package com.milkzs.android.learnui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

public class MyView extends View {

    private String TAG = "MyView";
    private Boolean DBG = false;

    private Paint mPaint;
    private int y = 3;

    private int height = 0;
    private int width = 0;

    private Boolean ifRun = true;

    private int Circle_x = 30;
    private int Circle_y = 30;
    private int speed_y = 30;

    private final int MSG_CODE_TIME = 10;
    private final int MSG_CODE_STOP = 11;

    private ArrayList<Ball> ballArrayList = new ArrayList<>();

    public MyView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //width = manager.getDefaultDisplay().getWidth();
        //height = manager.getDefaultDisplay().getHeight();
        mPaint = new Paint();
        mPaint.setStrokeWidth(40);
        Log.d(TAG, "second");

        Ball ball = new Ball();
        ball.setBall_x(width / 2);
        ball.setBall_y(50);
        ball.setSpeed(50);
        ball.setDegree(0);
        ballArrayList.add(ball);

        Circle_y = 50;
        new Thread() {
            @Override
            public void run() {

                do {
                    Circle_x = width / 2;

                    if (height <= Circle_y + speed_y || Circle_y + speed_y <= 0) {
                        Log.d(TAG, "height is : " + height);
                        Log.d(TAG, "y is : " + Circle_y);
                        speed_y = -1 * speed_y;
                    }
                    Circle_y = Circle_y + speed_y;
                    postInvalidate();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ifRun);
            }
        }.start();
    }

    private void runBall(Ball ball) {
        int ball_x = ball.getBall_x();
        int ball_y = ball.getBall_y();
        int degree = ball.getDegree();
        int speed = ball.getSpeed();

        int speedX = (int) (speed * Math.cos(degree * Math.PI / 180));
        int speedY = (int) (speed * Math.sin(degree * Math.PI / 180));

        if (height <= ball_y + speedY
                || ball_y + speedY <= 0
                || width <= ball_x + speedX
                || ball_x + speedX <= 0) {
            Log.d(TAG, "height is : " + height);
            Log.d(TAG, "y is : " + ball_y);
            speed = -1 * speed;
            speedX = -1 * speedX;
            speedY = -1 * speedY;
        }
        ball_y = ball_y + speedY;
        ball_x = ball_x + speedX;

        ball.setSpeed(speed);
        ball.setBall_x(ball_x);
        ball.setBall_y(ball_y);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (DBG) Log.d(TAG, "onMeasure width is : " + widthMeasureSpec
                + " | height is : " + heightMeasureSpec);
        if (DBG) Log.d(TAG, "onMeasure get width is : " + getMeasuredWidth() + " | "
                + " get height is : " + getMeasuredHeight());
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (DBG) Log.d(TAG, "onDraw");
        super.onDraw(canvas);
        mPaint.setColor(Color.GREEN);

        canvas.drawCircle(Circle_x, Circle_y, 20, mPaint);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CODE_TIME: {
                    handler.removeMessages(MSG_CODE_TIME);

                    if (width < y + 30) {
                        y = 3;
                    }
                    y = y + 15;
                    if (DBG) Log.d(TAG, "run " + y);
                    // postInvalidate();
                    invalidate();
                    handler.sendEmptyMessageAtTime(MSG_CODE_TIME, 100);
                }
                break;
                case MSG_CODE_STOP: {
                    handler.removeMessages(MSG_CODE_TIME);
                }
                break;
            }
        }
    };

    class Ball {

        private int ball_x;
        private int ball_y;
        private int speed;
        private int degree;

        public int getBall_x() {
            return ball_x;
        }

        public void setBall_x(int ball_x) {
            this.ball_x = ball_x;
        }

        public int getBall_y() {
            return ball_y;
        }

        public void setBall_y(int ball_y) {
            this.ball_y = ball_y;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getSpeed() {
            return speed;
        }

        public void setDegree(int degree){
            this.degree = degree;
        }

        public int getDegree() {
            return degree;
        }
    }
}
