package com.example.baidu.retrofit.Study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;


public class MyView extends View {

    private Point controlPoint = new Point(0, 0);
    private Context mContext;
    private Scroller mScroller;

    public MyView(Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        mScroller = new Scroller(mContext);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setTextSize(20);
        Path path = new Path();
        path.moveTo(100, 500);
        canvas.drawText("??????????????????-1", 100, 500, paint);
        path.quadTo(controlPoint.x, controlPoint.y, 700, 500);
        canvas.drawText("??????????????????-2", controlPoint.x, controlPoint.y, paint);
        canvas.drawText("??????????????????-3", 700, 500, paint);
        //绘制路径
        canvas.drawPath(path, paint);
        //绘制辅助点
        canvas.drawPoint(controlPoint.x, controlPoint.y, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                controlPoint.x = (int) event.getX();
                controlPoint.y = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }

    }
}