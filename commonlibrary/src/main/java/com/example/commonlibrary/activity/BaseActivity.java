package com.example.commonlibrary.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tool.cn.R;
import com.tool.cn.utils.AppManager;
import com.tool.cn.utils.KeyBoardUtils;
import com.tool.cn.utils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 *  2017/3/9  16:07.
 *
 *
 * @version 1.0.0
 * @class BaseActivity
 * @describe 公共基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    //当前的Context对象，方便内部类使用
    protected Context mContext;
    //布局填充器
    protected LayoutInflater mInflater;
    //根布局
    protected LinearLayout mLlRoot;
    //标题栏
    protected Toolbar mToolBar;
    // 将被替换的内容布局
    protected FrameLayout mFmContent;
    // 标题返回局
    protected ImageView mIvTitleBack;
    //标题图片
    protected ImageView mIvTitleName;
    // 标题名
    protected TextView mTvTitleName;
    // 标题右边文字
    protected TextView mTvTitleRight;
    // 标题右图片
    protected ImageView mIvTitleRight;
    //标题第二张右图片
    protected ImageView mIvTitleRightSecond;
    //标题左文字
    protected TextView mTvTitleLeft;
    // 标题左图片
    protected ImageView mIvTitleLeft;
    //标题中间区域
    protected LinearLayout mLlTitleCenter;
    protected FrameLayout mFmTitleRight;
    protected FrameLayout mFmBackLeft;
    protected boolean isFront = false; //是否在前台显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.StatusBarDarkMode(this, 3);
        mContext = this;
        mInflater = getLayoutInflater();
        AppManager.getInstance().addActivity(this);// 将activity添加到list中
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 释放Context,降低内存泄漏
        mContext = null;
        AppManager.getInstance().finishActivity(this);
        System.gc();
    }

    //返回事件
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KeyBoardUtils.closeKeyBoard(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        init();
        getHttp();
    }

    /**
     * 初始化,由基类Activity调用
     */
    protected abstract void init();

    protected abstract void getHttp();

    @Override
    public void onResume() {
        super.onResume();
        isFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isFront = false;
    }

    /**
     * 使用默认标题栏
     *
     * @param contentResID   内容布局id
     * @param titleStringRes 标题文字
     */
    public void setContentViewWithDefaultTitle(@LayoutRes final int contentResID, @StringRes int titleStringRes) {
        this.setContentViewWithDefaultTitle(contentResID, getResources().getString(titleStringRes));
    }

    /**
     * 使用默认标题栏
     *
     * @param contentResID 内容布局id
     * @param titleName    标题文字
     */
    public void setContentViewWithDefaultTitle(@LayoutRes final int contentResID, @NonNull String titleName) {
        super.setContentView(R.layout.activity_base);
        mLlRoot = getViewById(R.id.ll_root);
        mToolBar = getViewById(R.id.tb_base);
        mFmContent = getViewById(R.id.fm_base_content);
        mFmBackLeft = getViewById(R.id.fm_title_left);
        mIvTitleLeft = getViewById(R.id.iv_title_left);
        mTvTitleLeft = getViewById(R.id.tv_title_left);
        mFmTitleRight = getViewById(R.id.fm_title_right);
        mTvTitleRight = getViewById(R.id.tv_title_right);
        mIvTitleRight = getViewById(R.id.iv_title_right);
        mIvTitleRightSecond = getViewById(R.id.iv_title_right_second);
        mIvTitleBack = getViewById(R.id.iv_title_back);
        mIvTitleName = getViewById(R.id.iv_title);
        mTvTitleName = getViewById(R.id.tv_title);
        mLlTitleCenter = getViewById(R.id.ll_title_center);

        setSupportActionBar(mToolBar);
        if (contentResID > 0) {
            mInflater.inflate(contentResID, mFmContent);
        }
        ButterKnife.bind(this);
        mTvTitleName.setText(titleName);
        setOnClickListeners(mOnClickListener, mFmBackLeft, mFmTitleRight);
        init();
    }

    /**
     * 使用默认标题栏
     *
     * @param contentResID   内容布局id
     * @param titleStringRes 标题文字
     */
    public void setContentViewWithLeftTitle(@LayoutRes final int contentResID, @StringRes int titleStringRes) {
        this.setContentViewWithLeftTitle(contentResID, getResources().getString(titleStringRes));
    }

    /**
     * 使用默认标题栏
     *
     * @param contentResID 内容布局id
     * @param titleName    标题文字
     */
    public void setContentViewWithLeftTitle(@LayoutRes final int contentResID, @NonNull String titleName) {
        super.setContentView(R.layout.activity_base);
        mLlRoot = getViewById(R.id.ll_root);
        mToolBar = getViewById(R.id.tb_base);
        mFmContent = getViewById(R.id.fm_base_content);
        mFmBackLeft = getViewById(R.id.fm_title_left);
        mIvTitleLeft = getViewById(R.id.iv_title_left);
        mTvTitleLeft = getViewById(R.id.tv_title_left);
        mFmTitleRight = getViewById(R.id.fm_title_right);
        mTvTitleRight = getViewById(R.id.tv_title_right);
        mIvTitleRight = getViewById(R.id.iv_title_right);
        mIvTitleRightSecond = getViewById(R.id.iv_title_right_second);
        mIvTitleBack = getViewById(R.id.iv_title_back);
        mTvTitleName = getViewById(R.id.tv_title);
        mLlTitleCenter = getViewById(R.id.ll_title_center);

        setSupportActionBar(mToolBar);
        if (contentResID > 0) {
            mInflater.inflate(contentResID, mFmContent);
        }
        mTvTitleLeft.setVisibility(View.VISIBLE);
        mTvTitleName.setText(titleName);
        setOnClickListeners(mOnClickListener, mFmBackLeft, mFmTitleRight);
        init();
    }


    /**
     * 设置标题中间区域的View
     *
     * @param view 标题中间区域的View
     */
    protected void setTittleCenterView(View view) {
        mLlTitleCenter.removeAllViews();
        mLlTitleCenter.addView(view);
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
        return (VT) findViewById(id);
    }

    /**
     * 不需要为返回键设置监听，默认为finish当前activity
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.fm_title_left) {// 返回
                onBackPressed();
            } else if (v.getId() == R.id.fm_title_right) {
                rightClickEnvent();
            }
        }
    };

    /**
     * 标题栏右键点击事件
     */
    protected void rightClickEnvent() {

    }

    /**
     * 为多个View 添加点击事件
     *
     * @param listener View.OnClickListener
     * @param views    View
     */
    @Deprecated
    protected void setOnClickListeners(View.OnClickListener listener, View... views) {
        if (listener != null) {
            for (View view : views) {
                view.setOnClickListener(listener);
            }
        }
    }

    /**
     * 解决系统改变字体大小的时候导致的界面布局混乱的问题(不使用系统资源)
     *
     * @return Resources
     */
    @Override
    public Resources getResources() {
        if (isNeedUseSystemResConfig()) {
            return super.getResources();
        } else {
            Resources res = super.getResources();
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            res.updateConfiguration(configuration, res.getDisplayMetrics());
            return res;
        }
    }

    // 默认返回true，使用系统资源，如果个别界面不需要，
    // 在这些activity中Override this method ，then return false;
    protected boolean isNeedUseSystemResConfig() {
        return true;
    }

    /**
     * 设置Activity进出动画
     *
     * @param enterAnim 进入动画
     * @param exitAnim  消失动画
     */
    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

}
