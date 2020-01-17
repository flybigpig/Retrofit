package com.example.baidu.retrofit.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author
 * @date 2020/1/17.
 * GitHub：
 * email：
 * description：SurfaceView
 */
public class SurfaceViewDemo extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Paint paint;

    public SurfaceViewDemo(Context context) {
        this(context, null, 0);
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("=========surfaceCreated========");
        new Thread(new Runnable() {
            @Override
            public void run() {
                draw();
            }
        }).start();
    }

    private void draw() {
        try {
            System.out.println("============draw========");
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawCircle(500, 500, 300, paint);
            mCanvas.drawCircle(100, 100, 20, paint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null)
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("=========surfaceChanged========");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("=========surfaceDestroyed========");
    }
}
