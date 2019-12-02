package com.tool.cn.widget.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.tool.cn.R;

/**
 *  2017/1/16 14:51
 *
 *
 * @version 1.0.0
 * @class PercentImageView
 * @describe 百分比的ImageView
 * 注：1.注：宽高权重都设置时权重设置百分比方法才有效;
 * 2.宽高权重和百分比都设置时使用百分比方法.
 */
public class PercentImageView extends AppCompatImageView {
    /**
     * 宽高比
     */
    private float mWidthHeightPercent;
    /**
     * 宽权重
     */
    private int mWidthWeight = -1;
    /**
     * 高权重
     */
    private int mHeightWeight = -1;

    public PercentImageView(Context context) {
        this(context, null);
    }

    public PercentImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageView(context, attrs, defStyleAttr);
    }

    private void initImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typrdArray = getResources().obtainAttributes(attrs, R.styleable.PercentView);
        mWidthHeightPercent = typrdArray.getFloat(R.styleable.PercentView_width_height_percent, -1);
        mWidthWeight = typrdArray.getInteger(R.styleable.PercentView_width_weight, mWidthWeight);
        mHeightWeight = typrdArray.getInteger(R.styleable.PercentView_height_weight, mHeightWeight);
        typrdArray.recycle();
    }

    /**
     * 设置宽高比
     *
     * @param widthHeightPercent 宽高比
     */
    public void setWidthHeightPercent(float widthHeightPercent) {
        this.mWidthHeightPercent = widthHeightPercent;
    }

    /**
     * 设置宽权重
     *
     * @param widthWeight 宽权重
     */
    public void setWidthWeight(int widthWeight) {
        this.mWidthWeight = widthWeight;
    }

    /**
     * 设置高权重
     *
     * @param heightWeight 高权重
     */
    public void setHeightWeight(int heightWeight) {
        this.mHeightWeight = heightWeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        //父容器传过来的宽度方向上的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //父容器传过来的高度方向上的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //父容器传过来的宽度的值
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        //父容器传过来的高度的值
        int height = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        switch (widthMode) {
            case MeasureSpec.EXACTLY://精确尺寸eg:andorid:layout_width="50dp"或者andorid:layout_width="match_parent"

                break;
            case MeasureSpec.UNSPECIFIED://未指定尺寸eg:WRAP_CONTENT,控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可.

                break;
            case MeasureSpec.AT_MOST://最大尺寸,这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式

                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY://精确尺寸eg:andorid:layout_width="50dp"或者andorid:layout_width="match_parent"

                break;
            case MeasureSpec.UNSPECIFIED://未指定尺寸eg:WRAP_CONTENT,控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可.
                if (-1 != mWidthHeightPercent) {
                    height = (int) ((float) width / mWidthHeightPercent);
                } else if (-1 != mWidthWeight && -1 != mHeightWeight) {
                    height = (int) ((float) width * mHeightWeight / (float) mWidthWeight);
                }
                height += +getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.AT_MOST://最大尺寸,这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式

                break;
        }
        if (d != null && (-1 != mWidthHeightPercent || (-1 != mWidthWeight && -1 != mHeightWeight))) {
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
