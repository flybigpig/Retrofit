package com.example.commonlibrary.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 *  2017/5/2  13:52.
 *
 *
 * @version 1.0.0
 * @class MeasureHeightViewPager
 * @describe 每个Fragment高度都自适应的ViewPager
 */
public class MeasureHeightViewPager extends ViewPager {

    public MeasureHeightViewPager(Context context) {
        super(context);
    }

    public MeasureHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                View child = getChildAt(position);
                if (null != child) {
                    child.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                    layoutParams.height = child.getMeasuredHeight();
                    setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        View child;
        int currentIndex = getCurrentItem();
        child = getChildAt(currentIndex);
        if (null != child) {
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
