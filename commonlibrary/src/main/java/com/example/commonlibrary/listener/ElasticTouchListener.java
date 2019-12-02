package com.example.commonlibrary.listener;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 *  2017/4/13  11:18.
 *
 *
 * @version 1.0.0
 * @class ElasticTouchListener
 * @describe 弹性UI实现。
 * eg:View.setOnTouchListener(new ElasticTouchListener());
 */
public class ElasticTouchListener implements View.OnTouchListener {
    private View mRootView;
    private View[] mChildren;
    private float mY;
    private Rect mNormal = new Rect();
    private boolean isAnimationFinish = true;
    private int[] mTops;
    private int[] mBottoms;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (null == mRootView && null == mChildren) {
            if (v instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) v;
                int count = group.getChildCount();
                if (count > 0) {
                    mChildren = new View[count];
                    mTops = new int[count];
                    mBottoms = new int[count];
                    for (int i = 0; i < count; i++) {
                        mChildren[i] = group.getChildAt(i);
                        mTops[i] = mChildren[i].getTop();
                        mBottoms[i] = mChildren[i].getBottom();
                    }
                }
            }
            mRootView = v;
        }

        if (isAnimationFinish && (null != mRootView || null != mChildren)) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    mY = 0;
                    if (isNeedAnimation()) {
                        animation();
                    }
                    mRootView.invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float preY = mY == 0 ? event.getY() : mY;
                    float nowY = event.getY();
                    int deltaY = (int) (preY - nowY);
                    mY = nowY;
                    //当滚动到最上或最下时就不会再滚动，这时移动布局
                    if (isNeedMove()) {
                        if (mNormal.isEmpty()) {
                            mNormal.set(mRootView.getLeft(), mRootView.getTop(), mRootView.getRight(), mRootView.getBottom());
                        }
                        if (null != mChildren) {
                            for (View aMChildren : mChildren) {
                                aMChildren.layout(aMChildren.getLeft(), aMChildren.getTop() - deltaY / 2, aMChildren.getRight(), aMChildren.getBottom() - deltaY / 2);
                            }
                        } else {
                            mRootView.layout(mRootView.getLeft(), mRootView.getTop() - deltaY / 2, mRootView.getRight(), mRootView.getBottom() - deltaY / 2);
                        }
                    }
                    mRootView.invalidate();
                    break;
            }
        } else {
            return false;
        }
        return true;
    }

    private void animation() {
        if (null == mChildren) {
            //开启移动动画
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, mNormal.top - mRootView.getTop());
            translateAnimation.setDuration(200);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isAnimationFinish = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRootView.clearAnimation();
                    //设置回到正常的布局位置
                    mRootView.layout(mNormal.left, mNormal.top, mNormal.right, mNormal.bottom);
                    mNormal.setEmpty();
                    isAnimationFinish = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mRootView.startAnimation(translateAnimation);
        } else {
            for (int i = 0; i < mChildren.length; i++) {
                final View view = mChildren[i];
                if (View.VISIBLE == view.getVisibility()) {
                    final int index = i;
                    //开启移动动画
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, mTops[i] - view.getTop());
                    translateAnimation.setDuration(200);
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isAnimationFinish = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            view.clearAnimation();
                            //设置回到正常的布局位置
                            view.layout(view.getLeft(), mTops[index], view.getRight(), mBottoms[index]);
                            mNormal.setEmpty();
                            isAnimationFinish = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    view.startAnimation(translateAnimation);
                }
            }
        }
    }

    /**
     * 是否需要开启动画
     *
     * @return true需要，false不需要
     */
    private boolean isNeedAnimation() {
        return !mNormal.isEmpty();
    }

    /**
     * 是否需要移动布局
     *
     * @return true需要，false不需要
     */
    private boolean isNeedMove() {
        return true;
    }

}
