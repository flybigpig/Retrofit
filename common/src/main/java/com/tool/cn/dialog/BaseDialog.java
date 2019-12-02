package com.tool.cn.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tool.cn.R;
import com.tool.cn.utils.StatusBarUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.ButterKnife;

/**
 *  2017/5/23  10:38.
 *
 *
 * @version 1.0.0
 * @class BaseDialog
 * @describe 所有Dialog的基类。
 * 注：在BroadcastReceiver等地方显示对话框时需要加上下面这句话，并申请权限：
 * android.permission.SYSTEM_ALERT_WINDOW
 * alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
 */
public abstract class BaseDialog extends Dialog {

    protected String TAG = this.getClass().getSimpleName();
    private static final int INVALID = -1;
    private int mGravity = Gravity.BOTTOM;//默认底部进入
    private View mAnimationView;//需要执行进出动画的View
    private boolean isOpenAnimation;//是否开启进出动画
    private boolean mOutAnimationIsFisish;//执行动画时，退出动画是否执行完毕，true完毕，false，未完成

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Gravity.TOP, Gravity.CENTER, Gravity.BOTTOM})
    public @interface CONTENT_GRAVITY {

    }

    protected View mView;
    protected Context mContext;

    public BaseDialog(Context context, int layoutId, int theme) {
        super(context, theme);
        StatusBarUtils.setStatusBarLightMode((Activity) context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(layoutId, null);
        this.setContentView(mView);
        setCanceledOnTouchOutside(false); // 设置点击屏幕Dialog不消失
        getWindow().setGravity(Gravity.CENTER);
        ButterKnife.bind(this);
        setAnimationView(mView);
        initView();
    }

    /**
     * 查找View,不用强制转型
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return 对应的View
     */
    @SuppressWarnings("unchecked")
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mView.findViewById(id);
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 为多个View 添加点击事件
     *
     * @param listener 点击事件监听
     * @param views    需要设置点击事件的View
     */
    protected void setOnClickListeners(View.OnClickListener listener, View... views) {
        if (listener != null) {
            for (View view : views) {
                view.setOnClickListener(listener);
            }
        }
    }

    public BaseDialog setGravity(@CONTENT_GRAVITY int gravity) {
        this.mGravity = gravity;
        return this;
    }

    protected BaseDialog setAnimationView(View animationView) {
        this.mAnimationView = animationView;
        return this;
    }

    public BaseDialog setOpenAnimation(boolean isOpenAnimation) {
        this.isOpenAnimation = isOpenAnimation;
        return this;
    }

    /**
     * 显示Dialog
     */
    @Override
    public void show() {
        super.show();
        if (isOpenAnimation) {
            startInAnimation();
            mOutAnimationIsFisish = false;
        }
    }

    /**
     * 隐藏Dialog
     */
    @Override
    public void dismiss() {
        if (isOpenAnimation && !mOutAnimationIsFisish) {
            startOutAnimation();
        } else {
            super.dismiss();
        }
    }

    private void startInAnimation() {
        Animation inAnimation = getInAnimation();
        if (null != inAnimation) {
            mAnimationView.startAnimation(inAnimation);
        }
    }

    private void startOutAnimation() {
        Animation outAnimation = getOutAnimation();

        if (null != outAnimation) {
            outAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mOutAnimationIsFisish = true;
                    dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mAnimationView.startAnimation(outAnimation);
        } else {
            mOutAnimationIsFisish = true;
            dismiss();
        }
    }

    private Animation getInAnimation() {
        int inAnimationRes = getAnimationResource(true);
        if (INVALID == inAnimationRes) return null;
        try {
            return AnimationUtils.loadAnimation(mContext, inAnimationRes);
        } catch (Exception e) {//红米1S手机在此处会报NumberFormatException，郁闷
            return null;
        }
    }

    private Animation getOutAnimation() {
        int inAnimationRes = getAnimationResource(false);

        if (INVALID == inAnimationRes) return null;
        try {
            return AnimationUtils.loadAnimation(mContext, inAnimationRes);
        } catch (Exception e) {//红米1S手机在此处会报NumberFormatException，郁闷
            return null;
        }
    }

    private int getAnimationResource(boolean isInAnimation) {
        if (Gravity.TOP == mGravity) {
            return isInAnimation ? R.anim.slide_in_top : R.anim.slide_out_top;
        }
        if (Gravity.BOTTOM == mGravity) {
            return isInAnimation ? R.anim.slide_in_bottom : R.anim.slide_out_bottom;
        }
        if (Gravity.CENTER == mGravity) {
            return isInAnimation ? R.anim.fade_in_center : R.anim.fade_out_center;
        }
        return INVALID;
    }
}
