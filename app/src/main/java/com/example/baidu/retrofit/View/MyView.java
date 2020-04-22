package com.example.baidu.retrofit.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.example.baidu.retrofit.util.PointEvaluator;

public class MyView extends View {
    private Paint mPaint;
    private Point currentPoint;
    private static int RADIUS = 40;
    private static long mLast = 0;

    public MyView(Context context) {
        super(context);
        initPaint();
    }

    public MyView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mLast = System.currentTimeMillis();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            int x = currentPoint.x;
            int y = currentPoint.y;
            canvas.drawCircle(x, y, RADIUS, mPaint);

            Point startPoint = new Point(RADIUS, RADIUS);
            Point endPoint = new Point(700, 1000);


            ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
//            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//
//                    long cur = System.currentTimeMillis();
//
//                    double i = (Math.sin(new Random().nextInt(32))) * 100;
//                    RADIUS = (int) i;
//                    mLast = cur;
//
//
//                }
//            });
            anim.setDuration(5000);
            anim.setRepeatCount(-1);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentPoint = (Point) animation.getAnimatedValue();
                    invalidate();
                }
            });
            anim.start();
        } else {
            float x = currentPoint.x;
            float y = currentPoint.y;
            canvas.drawCircle(x, y, RADIUS, mPaint);
        }
        super.onDraw(canvas);
    }
}
