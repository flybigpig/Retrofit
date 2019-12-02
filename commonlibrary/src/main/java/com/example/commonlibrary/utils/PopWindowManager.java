package com.example.commonlibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 *  2017/3/6  14:17.
 *
 *
 * @version 1.0.0
 * @class PopWindowManager
 * @describe 通用PopWindow管理类
 * 参考：https://github.com/pinguo-zhouwei/CustomPopwindow
 */
public class PopWindowManager {
    private Context mContext;
    private int mPopWidth;
    private int mPopHeight;
    private boolean isFocusable = true;
    private boolean isOutSide = true;
    private int mResLayoutId = -1;
    private View mContextView;
    private PopupWindow mPopupWindow;
    private int mAnimationStyle = -1;

    private boolean isClippEnable = true;
    private boolean isGnoreCheekPress = false;
    private int mInputMode = -1;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private int mSoftInputMode;
    private boolean isTouchable = true;
    private View.OnTouchListener mOnTouchListener;
    private OnBgDismissListener onBgDismissListener;

    private PopWindowManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 直接显示在参照View 的左下方
     *
     * @param anchor
     * @return
     */
    public PopWindowManager showAsDropDown(View anchor) {
        if (mPopupWindow != null && anchor.isShown()) {
            mPopupWindow.showAsDropDown(anchor);
        }
        return this;
    }

    /**
     * 显示在参照View的左下方，可以通过xoff，yOff,来调节x,y方向的偏移
     *
     * @param anchor
     * @param xOff
     * @param yOff
     * @return
     */
    public PopWindowManager showAsDropDown(View anchor, int xOff, int yOff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PopWindowManager showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }

    /**
     * Gravity.TOP | Gravity.LEFT 以屏幕左上角为坐标原点
     * Gravity.BOTTOM | Gravity.RIGHT 以屏幕右下角为坐标原点
     * Gravity.LEFT 以屏幕左侧，屏幕高度 1/2 处为坐标原点
     *
     * @param parent  父控件
     * @param gravity
     * @param x       the popup's x location offset
     * @param y       the popup's y location offset
     * @return
     */
    public PopWindowManager showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }

    /**
     * 关闭PopupWindow
     */
    public void dissmiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 设置点击背景消失
     *
     * @param backgroundTouchable
     * @return
     */
    public void setBackgroundTouchable(boolean backgroundTouchable) {
        if (mContextView != null && backgroundTouchable) {
            mContextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBgDismissListener != null) {
                        onBgDismissListener.onBgDismiss();
                    }
                    dissmiss();
                }
            });
        }
    }

    public void setOnDismissListener(OnBgDismissListener onBgDismissListener) {
        this.onBgDismissListener = onBgDismissListener;
    }

    public interface OnBgDismissListener {
        void onBgDismiss();
    }

    private PopupWindow build() {
        if (null == mContextView && -1 != mResLayoutId) {
            mContextView = LayoutInflater.from(mContext).inflate(mResLayoutId, null);
        }
        if (0 != mPopWidth && 0 != mPopHeight) {
            mPopupWindow = new PopupWindow(mContextView, mPopWidth, mPopHeight);
        } else {
            mPopupWindow = new PopupWindow(mContextView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (-1 != mAnimationStyle) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }

        apply(mPopupWindow);//设置一些其它属性

        mPopupWindow.setFocusable(isFocusable);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(isOutSide);

        if (0 == mPopWidth || 0 == mPopHeight) {
            mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mPopWidth = mPopupWindow.getContentView().getMeasuredWidth();
            mPopHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }
        mPopupWindow.update();
        return mPopupWindow;
    }

    /**
     * 添加一些属性设置
     *
     * @param popupWindow PopupWindow
     */
    private void apply(PopupWindow popupWindow) {
        popupWindow.setClippingEnabled(isClippEnable);
        if (isGnoreCheekPress) {
            popupWindow.setIgnoreCheekPress();
        }
        if (mInputMode != -1) {
            popupWindow.setInputMethodMode(mInputMode);
        }
        if (mSoftInputMode != -1) {
            popupWindow.setSoftInputMode(mSoftInputMode);
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener);
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener);
        }
        popupWindow.setTouchable(isTouchable);
    }

    public static class PopupWindowBuilder {
        private PopWindowManager mPopWindowManager;

        public PopupWindowBuilder(Context context) {
            mPopWindowManager = new PopWindowManager(context);
        }

        /**
         * 设置展示的宽、高
         *
         * @param popWidth  宽
         * @param popHeight 高
         * @return PopupWindowBuilder
         */
        public PopupWindowBuilder size(int popWidth, int popHeight) {
            mPopWindowManager.mPopWidth = popWidth;
            mPopWindowManager.mPopHeight = popHeight;
            return this;
        }

        /**
         * 设置是否获取焦点
         *
         * @param focusable true获取焦点,false不获取焦点
         * @return PopupWindowBuilder
         */
        public PopupWindowBuilder setFocusable(boolean focusable) {
            mPopWindowManager.isFocusable = focusable;
            return this;
        }

        /**
         * 设置显示的View
         *
         * @param view 显示的View
         * @return PopupWindowBuilder
         */
        public PopupWindowBuilder setView(View view) {
            mPopWindowManager.mContextView = view;
            mPopWindowManager.mResLayoutId = -1;
            return this;
        }

        /**
         * 设置显示的View资源文件id
         *
         * @param resLayoutId 显示的View资源文件id
         * @return PopupWindowBuilder
         */
        public PopupWindowBuilder setView(@LayoutRes int resLayoutId) {
            mPopWindowManager.mContextView = null;
            mPopWindowManager.mResLayoutId = resLayoutId;
            return this;
        }

        /**
         * 设置点击PopupWindow 以外区域是否可以隐藏PopupWindow
         * 注意：这里要注意一下，有时侯我们希望触摸PopupWindow 以外区域就隐藏PopupWindow,
         * 理论上我们只需要调用 setOutsideTouchable(ture)设置为ture就可以了，
         * 但是实际上只设置这个属性是不行的，必须设置背景，
         * 也就是说要和setBackgroundDrawable(Drawable background)同时使用才有效，
         * 不然，点击PopupWindow以外区域是不能隐藏掉的。
         *
         * @param outsideTouchable true隐藏，false不隐藏
         * @return PopupWindowBuilder
         */
        public PopupWindowBuilder setOutsideTouchable(boolean outsideTouchable) {
            mPopWindowManager.isOutSide = outsideTouchable;
            return this;
        }

        /**
         * 设置PopWindow动画
         *
         * @param animationStyle PopWindow动画
         * @return PopupWindowBuilder
         */
        public PopupWindowBuilder setAnimationStyle(int animationStyle) {
            mPopWindowManager.mAnimationStyle = animationStyle;
            return this;
        }

        public PopupWindowBuilder setClippingEnable(boolean enable) {
            mPopWindowManager.isClippEnable = enable;
            return this;
        }

        public PopupWindowBuilder setIgnoreCheekPress(boolean ignoreCheekPress) {
            mPopWindowManager.isGnoreCheekPress = ignoreCheekPress;
            return this;
        }

        public PopupWindowBuilder setInputMethodMode(int mode) {
            mPopWindowManager.mInputMode = mode;
            return this;
        }

        public PopupWindowBuilder setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener) {
            mPopWindowManager.mOnDismissListener = onDissmissListener;
            return this;
        }

        public PopupWindowBuilder setSoftInputMode(int softInputMode) {
            mPopWindowManager.mSoftInputMode = softInputMode;
            return this;
        }

        public PopupWindowBuilder setTouchable(boolean touchable) {
            mPopWindowManager.isTouchable = touchable;
            return this;
        }

        public PopupWindowBuilder setTouchIntercepter(View.OnTouchListener touchIntercepter) {
            mPopWindowManager.mOnTouchListener = touchIntercepter;
            return this;
        }

        public PopWindowManager create() {
            mPopWindowManager.build();
            return mPopWindowManager;
        }
    }

    public int getWidth() {
        return mPopWidth;
    }

    public int getHeight() {
        return mPopHeight;
    }

    public View getView() {
        return mContextView;
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

}
