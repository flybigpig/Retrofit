package com.tool.cn.widget.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**recyclerview中的linear布局时用到的测量recyclerview高度
 * Created by 关 on 2016/4/8.
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {
    RecyclerView recyclerView;
    int spanCount;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public CustomLinearLayoutManager(Context context, RecyclerView recyclerView) {
        super(context);
        this.recyclerView = recyclerView;
    }

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        int measuredWidth = recyclerView.getMeasuredWidth();
        int measuredHeight = recyclerView.getMeasuredHeight();
        int myMeasureHeight = 0;
        int count = state.getItemCount();
        for (int i = 0; i < count; i++) {
            View childView = recycler.getViewForPosition(i);
            if (recyclerView != null) {
                if (myMeasureHeight < measuredHeight) {
                    RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) childView.getLayoutParams();
                    int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), p.width);
                    int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), p.height);
                    childView.measure(childWidthSpec, childHeightSpec);
                    myMeasureHeight += childView.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                }
                recycler.recycleView(childView);
            }
        }
        setMeasuredDimension(measuredWidth, Math.min(measuredHeight, myMeasureHeight));
    }
}
