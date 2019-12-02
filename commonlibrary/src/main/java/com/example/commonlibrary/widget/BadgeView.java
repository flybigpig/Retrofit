package com.example.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.tool.cn.R;
import com.tool.cn.utils.DisplayUtils;

/**
 *  2017/4/14  18:30.
 *
 *
 * @version 1.0.0
 * @class PointMarkView
 * @describe 圆点标签
 */
public class BadgeView extends View {

    private Context mContext;
    private Paint mBgPaint;//圆形背景画笔
    private Paint mMarkPaint;//标签画笔
    private int mBgColor;
    private int mTextColor;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;//原点半径
    private String mBadgeText = "";
    private float mTextSize = 36;

    private int MODE_NUMBER = 1;
    private int MODE_POINT = 2;
    private int mMarkMode;//模式，默认为小圆点

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.BadgeView);
        mMarkMode = typedArray.getInt(R.styleable.BadgeView_mode, MODE_POINT);
        mBgColor = typedArray.getColor(R.styleable.BadgeView_background_color, Color.RED);
        mRadius = typedArray.getFloat(R.styleable.BadgeView_radius_size, 8);
        mTextColor = typedArray.getColor(R.styleable.BadgeView_text_color, Color.WHITE);
        mContext = context;
        mBgPaint = new Paint();
        mBgPaint.setColor(mBgColor);
        mBgPaint.setAntiAlias(true);
        if (mMarkMode == 1) {
            mMarkPaint = new Paint();
            mMarkPaint.setTextSize(mTextSize);
            mMarkPaint.setColor(mTextColor);
            mMarkPaint.setAntiAlias(true);
            mMarkPaint.setTextAlign(Paint.Align.CENTER);
        }
        setVisibility(GONE);//默认不显示
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCenterX = ((float) getWidth()) / 2f;
        mCenterY = ((float) getHeight()) / 2f;
        if (MODE_NUMBER == mMarkMode) {//数字
            if (TextUtils.isEmpty(mBadgeText)) {
                return;
            }
            float mRadius = Math.min(mCenterX, mCenterY);
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mBgPaint);

            Paint.FontMetricsInt fm = mMarkPaint.getFontMetricsInt();
            int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
            canvas.drawText(mBadgeText, mCenterX, startY, mMarkPaint);
        } else {//圆点
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mBgPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = DisplayUtils.dp2px(mContext, mRadius);
        setMeasuredDimension(width, width);
    }

    /**
     * 设置标记数量
     *
     * @param count 标记数量
     */
    public void refresh(int count) {
        if (count > 0) {
            setVisibility(VISIBLE);
            if (count < 100) {
                mBadgeText = String.valueOf(count);
            } else {
                mBadgeText = "...";
            }
            invalidate();
        } else {
            setVisibility(GONE);
        }
    }

    public Paint getBgPaint() {
        return mBgPaint;
    }

    public void setBgPaint(Paint mBgPaint) {
        this.mBgPaint = mBgPaint;
    }
}
