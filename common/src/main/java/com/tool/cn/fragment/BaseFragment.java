package com.tool.cn.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

/**
 * 2017/3/9  17:48.
 *
 * @version 1.0.0
 * @class BaseFragment
 * @describe Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG = this.getClass().getSimpleName();

    protected View mView;//当前的View
    protected Activity mContext;//Activity的对象
    private int ResId;//RES   ID
    private View view;// View

    //private boolean isVisible;//当前Fragment是否可见
    private boolean isInitView;//当前Fragment是否与View建立起映射关系
    private boolean isLoadOnlyOnce = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    /**
     * 设置视图
     *
     * @param id 视图id
     */
    protected void setContentView(@LayoutRes int id) {
        this.ResId = id;
    }

    /**
     * 设置视图
     *
     * @param view 视图的View对象
     */
    protected void setContentView(@NonNull View view) {
        this.view = view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (ResId != 0) {
            mView = inflater.inflate(ResId, container, false);
        } else if (view != null) {
            mView = view;
        } else {
            TextView textView = new TextView(mContext);
            mView = textView;
            textView.setText("未添加视图");
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, mView);
        initView();
        isInitView = true;//表示视图初始化完成
        Log.d("LazyLoad", !isHidden() + "");
        if (isLoadOnlyOnce() && isInitView) {
            getHttpData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isInitView && isLoadOnlyOnce()) {
            getHttpData();
        }
    }

    /**
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isLoadOnlyOnce()) {
            getHttpData();
        }
    }

    /**
     * 在此方法中初始化视图
     */
    protected abstract void initView();

    /**
     * 在此方法中获取http数据
     */
    public abstract void getHttpData();


    /**
     * Fragment显示刷新数据
     *
     * @param loadOnlyOnce true：刷新  false：不刷新
     */
    public void setLoadOnlyOnce(boolean loadOnlyOnce) {
        isLoadOnlyOnce = loadOnlyOnce;
    }

    public boolean isLoadOnlyOnce() {
        return isLoadOnlyOnce;
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
     * 为多个View 添加点击事件
     *
     * @param listener 点击事件监听
     * @param views    需要设置点击事件的View
     */
    @Deprecated
    protected void setOnClickListeners(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }
}
