package com.example.commonlibrary.widget.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 *  2016/6/12 12:08
 *
 *
 * @version 1.0.0
 * @class SelfAdaptionImageView
 * 描述:宽度match_parent，高度自适应的ImageView
 */
public class SelfAdaptionImageView extends AppCompatImageView {
    public SelfAdaptionImageView(Context context) {
        super(context);
    }

    public SelfAdaptionImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfAdaptionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        if (d != null) {
            height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            height = height + getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
