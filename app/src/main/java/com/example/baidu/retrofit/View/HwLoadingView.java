package com.example.baidu.retrofit.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baidu.retrofit.R;

/**
 * @author
 * @date 2020/3/26.
 * GitHub：
 * email：
 * description：
 */
public class HwLoadingView extends View {

    private static final int CIRCLE_COUNT = 12;
    private static final int DEGREE_PER_CIRCLE = 360 / CIRCLE_COUNT;
    private float[] mWholeCircleRadius = new float[CIRCLE_COUNT];
    private int[] mWholeCircleColors = new int[CIRCLE_COUNT];
    private float mMaxCircleRadius;
    private int mSize;
    private int mColor;

    private Paint mPaint;

    private ValueAnimator mAnimator;
    private int mAnimateValue = 0;
    private long mDuration;

    public HwLoadingView(Context context) {
        this(context, null);
    }

    public HwLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HwLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
        initValue();
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HwLoadingView);

        mSize = (int) ta.getDimension(R.styleable.HwLoadingView_size, dp2px(context, 50));
        setSize(mSize);

        mColor = ta.getColor(R.styleable.HwLoadingView_color, Color.parseColor("#CCCCCC"));
        setColor(mColor);

        mDuration = ta.getInt(R.styleable.HwLoadingView_duration, 1500);

        ta.recycle();
    }

    public void setConfig(int size) {
        this.mSize = size;
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
    }


    private void initValue() {
        float minCircleRadius = mSize / 24;
        for (int i = 0; i < CIRCLE_COUNT; i++) {
            switch (i) {
                case 7:
                    mWholeCircleRadius[i] = minCircleRadius * 1.25f;
                    mWholeCircleColors[i] = (int) (255 * 0.7f);
                    break;
                case 8:
                    mWholeCircleRadius[i] = minCircleRadius * 1.5f;
                    mWholeCircleColors[i] = (int) (255 * 0.8f);
                    break;
                case 9:
                case 11:
                    mWholeCircleRadius[i] = minCircleRadius * 1.75f;
                    mWholeCircleColors[i] = (int) (255 * 0.9f);
                    break;
                case 10:
                    mWholeCircleRadius[i] = minCircleRadius * 2f;
                    mWholeCircleColors[i] = 255;
                    break;
                default:
                    mWholeCircleRadius[i] = minCircleRadius;
                    mWholeCircleColors[i] = (int) (255 * 0.5f);
                    break;
            }
        }
        mMaxCircleRadius = minCircleRadius * 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mSize > 0) {

            canvas.rotate(DEGREE_PER_CIRCLE * mAnimateValue, mSize / 2, mSize / 2);
            for (int i = 0; i < CIRCLE_COUNT; i++) {

                mPaint.setAlpha(mWholeCircleColors[i]);

                canvas.drawCircle(mSize / 2, mMaxCircleRadius, mWholeCircleRadius[i], mPaint);
                canvas.rotate(DEGREE_PER_CIRCLE, mSize / 2, mSize / 2);
            }
        }
    }


    public void setSize(int size) {
        mSize = size;
        invalidate();
    }


    public void setColor(@ColorInt int color) {
        mColor = color;
        invalidate();
    }


    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mAnimateValue = (int) animation.getAnimatedValue();
            invalidate();
        }
    };


    public void start() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, CIRCLE_COUNT - 1);
            mAnimator.addUpdateListener(mUpdateListener);
            mAnimator.setDuration(mDuration);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.start();
        } else if (!mAnimator.isStarted()) {
            mAnimator.start();
        }
    }


    public void stop() {
        if (mAnimator != null) {
            mAnimator.removeUpdateListener(mUpdateListener);
            mAnimator.removeAllUpdateListeners();
            mAnimator.cancel();
            mAnimator = null;
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }


    private int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }
}