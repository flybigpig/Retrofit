package com.tool.cn.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 *  2017/8/8  10:54.
 *
 *
 * @version 1.0.0
 * @class LoadingView
 * @describe 自定义加载中View
 * 参考：https://github.com/linglongxin24/BaiduProgressBar
 */
public class LoadingView extends View {

    private int mCenterX = -1;//View的中心点X坐标
    private int mCenterY = -1;//View的中心点Y坐标
    private float mMaxOffset = 200f;//圆点最大偏移量
    private float mRadius = 30f;//圆点的半径
    private float mCurrentOffset;//当前偏移量
    private Paint mPaint;
    private int mLeftCircleColor, mCenterCircleColor, mRightCircleColor;//左、中、右圆的颜色
    private ValueAnimator mValueAnimator;
    /**
     * 开始执行的第一个动画的索引，
     * 由于第一个和第二个同时当执行，
     * 当第一遍执行完毕后就让第一个停下来在中间位置，换原来中间位置的第三个开始执行动画，
     * 以此类推，当第二遍执行完毕后第二个停下来，中间位置的开始执行动画。
     */
    private int mSweepIndex;

    public LoadingView(Context context) {
        super(context);
        initView();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLeftCircleColor = Color.BLUE;
        mCenterCircleColor = Color.RED;
        mRightCircleColor = Color.GREEN;
        initWithStartAnimator();
    }

    /**
     * 初始化并开启动画(用属性动画实现圆点移动)
     */
    private void initWithStartAnimator() {
        mValueAnimator = ValueAnimator.ofFloat(0f, mMaxOffset, 0f);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentOffset = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {//重复
                sweep(mSweepIndex);
            }
        });
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setDuration(1000);
        mValueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (-1 == mCenterX || -1 == mCenterY) {
            mCenterX = getWidth() / 2;
            mCenterY = getHeight() / 2;
        }
        //绘制左边的圆
        mPaint.setColor(mLeftCircleColor);
        canvas.drawCircle(mCenterX - mCurrentOffset, mCenterY, mRadius, mPaint);
        //绘制中间的圆
        mPaint.setColor(mCenterCircleColor);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        //绘制右边的圆
        mPaint.setColor(mRightCircleColor);
        canvas.drawCircle(mCenterX + mCurrentOffset, mCenterY, mRadius, mPaint);
    }

    /**
     * 每次让先执行动画的目标和中间停止的动画目标交换
     *
     * @param sweepIndex 需要和中间目标交换的索引(此处只有0或者2)
     */
    private void sweep(int sweepIndex) {
        int temp = mCenterCircleColor;
        if (0 == sweepIndex) {
            mCenterCircleColor = mLeftCircleColor;
            mLeftCircleColor = temp;
            mSweepIndex = 2;
        } else if (2 == sweepIndex) {
            mCenterCircleColor = mRightCircleColor;
            mRightCircleColor = temp;
            mSweepIndex = 0;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //在View销毁时停止动画并释放资源
        if (null != mValueAnimator) {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
    }

}
