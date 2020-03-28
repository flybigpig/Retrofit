package com.example.baidu.retrofit.View;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.List;


/**
 * 自动垂直滚动的ImageView
 */
public class AutoVerticalScrollImageView extends ImageSwitcher implements ViewSwitcher.ViewFactory {

    //mInUp,mOutUp分别构成向下翻页的进出动画
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;

    public AutoVerticalScrollImageView(Context context) {
        this(context, null);
    }

    public AutoVerticalScrollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {

        setFactory(this);

        mInUp = createAnim(true, true);
        mOutUp = createAnim(false, true);

        setInAnimation(mInUp);//当View显示时动画资源ID
        setOutAnimation(mOutUp);//当View隐藏是动画资源ID。

    }

    private Rotate3dAnimation createAnim(boolean turnIn, boolean turnUp) {

        Rotate3dAnimation rotation = new Rotate3dAnimation(turnIn, turnUp);
        rotation.setDuration(animTime);//执行动画的时间
        rotation.setFillAfter(false);//是否保持动画完毕之后的状态
        rotation.setInterpolator(new AccelerateInterpolator());//设置加速模式
        return rotation;
    }


    private boolean isRunning = true;
    private int number = 0;
    private List<Integer> list;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 199) {
                next();
                number++; // 切换现实的index
                if (list.size() > 0) {
                    setImageResource(list.get(number % list.size()));
                }
            }
        }
    };


    public void setList(List<Integer> list) {
        this.list = list;
    }

    public void startAutoScroll() {
        number = 0;
        isRunning = true;
        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    handler.sendEmptyMessage(199);
                    SystemClock.sleep(imageStillTime);

                }
            }
        }.start();
    }


    public void stopAutoScroll() {
        isRunning = false;
    }

    private int imageStillTime = 3000;//停留时长间隔
    private int animTime = 300;//执行动画的时间

    /**
     * 设置停留时长间隔
     */
    public void setImageStillTime(int textStillTime) {
        this.imageStillTime = textStillTime;
    }

    /**
     * 设置进入和退出的时间间隔
     */
    public void setAnimTime(int animTime) {
        this.animTime = animTime;
    }

    //这里返回的ImageView，就是我们看到的View,可以设置自己想要的效果
    public View makeView() {
        ImageView imageView = new ImageView(getContext().getApplicationContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       /* TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(15);
        textView.setSingleLine(true);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(getResources().getColor(R.color.player_black));*/
        return imageView;

    }

    //定义动作，向上滚动翻页
    public void next() {
        //显示动画
        if (getInAnimation() != mInUp) {
            setInAnimation(mInUp);
        }
        //隐藏动画
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(mOutUp);
        }
    }

    class Rotate3dAnimation extends Animation {
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public Rotate3dAnimation(boolean turnIn, boolean turnUp) {
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight();
            mCenterX = getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime), 0.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }


}
 