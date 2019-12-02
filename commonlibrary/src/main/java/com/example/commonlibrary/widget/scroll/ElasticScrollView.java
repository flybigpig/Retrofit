package com.example.commonlibrary.widget.scroll;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 *  2017/4/13  11:59.
 *
 *
 * @version 1.0.0
 * @class ElasticScrollView
 * @describe 弹性ScrollView
 */
public class ElasticScrollView extends ScrollView {
    private View mRootView;
    private float mY;
    private Rect mNormal = new Rect();
    private boolean isAnimationFinish = true;

    public ElasticScrollView(Context context) {
        this(context, null);
    }

    public ElasticScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mRootView = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (null == mRootView) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        if (isAnimationFinish) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mY = ev.getY();
                    super.onTouchEvent(ev);
                    break;
                case MotionEvent.ACTION_UP:
                    mY = 0;
                    if (isNeedAnimation()) {
                        animation();
                    }
                    super.onTouchEvent(ev);
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float preY = mY == 0 ? ev.getY() : mY;
                    float nowY = ev.getY();
                    int deltaY = (int) (preY - nowY);
                    mY = nowY;
                    // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                    if (isNeedMove()) {
                        if (mNormal.isEmpty()) {
                            // 保存正常的布局位置
                            mNormal.set(mRootView.getLeft(), mRootView.getTop(), mRootView.getRight(), mRootView.getBottom());
                        }
                        // 移动布局
                        mRootView.layout(mRootView.getLeft(), mRootView.getTop() - deltaY / 2, mRootView.getRight(), mRootView.getBottom() - deltaY / 2);
                    } else {
                        super.onTouchEvent(ev);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 开启动画移动
     */
    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, mNormal.top - mRootView.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimationFinish = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRootView.clearAnimation();
                // 设置回到正常的布局位置
                mRootView.layout(mNormal.left, mNormal.top, mNormal.right, mNormal.bottom);
                mNormal.setEmpty();
                isAnimationFinish = true;
            }
        });
        mRootView.startAnimation(ta);
    }

    /**
     * 是否需要开启动画
     *
     * @return true需要，false不需要
     */
    public boolean isNeedAnimation() {
        return !mNormal.isEmpty();
    }

    /**
     * 是否需要移动布局
     *
     * @return true需要，false不需要
     */
    public boolean isNeedMove() {
        int offset = mRootView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == 0 || scrollY == offset;
    }

}
