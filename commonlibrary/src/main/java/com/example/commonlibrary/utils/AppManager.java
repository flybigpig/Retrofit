package com.example.commonlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import java.util.Stack;

/**
 *  2017/3/9  15:23.
 *
 *
 * @version 1.0.0
 * @class AppManager
 * @describe 基类应用程序Activity管理类：管理Activity和应用程序
 */
public class AppManager {

    protected static Stack<Activity> mActivityStack;
    private static AppManager mInstance;

    protected AppManager() {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
    }

    /**
     * 获取单例
     */
    public static AppManager getInstance() {
        if (null == mInstance) {
            synchronized (AppManager.class) {
                if (null == mInstance) {
                    mInstance = new AppManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * @return activity
     */
    public Stack<Activity> getActivities() {
        return mActivityStack;
    }

    /**
     * 获取顶层Activity
     */
    public Activity currentActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中后一个压入的Activity)
     */
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        if (activity != null) {
            activity.finish();
            removeActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity... activitys) {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
                removeActivity(activity);
            }
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    public void removeActivity(Class<?> cls) {
        Activity activity = getActivity(cls);
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取指定的Activity对象
     */
    public Activity getActivity(Class<?> cls) {
        for (int i = 0; i < mActivityStack.size(); i++) {
            if (mActivityStack.get(i).getClass().equals(cls)) {
                return mActivityStack.get(i);
            }
        }
        return null;
    }

    /**
     * 结束除开指定Activity的其他Activity
     */
    public void finishOtherActivity(Class<?> cls) {
            for (int i = 0; i < mActivityStack.size(); i++) {
                if (!mActivityStack.get(i).getClass().equals(cls)) {
                    this.finishActivity(mActivityStack.get(i));
                    //如果不加这一行，remove之后当前元素的下标跟变化之后的size完全不对应，可能会跳过几个元素没有检查或者下标会大于size造成数组越界
                    i = -1;
                }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                this.finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        int size = mActivityStack.size();
        for (int i = 0; i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        //移出Stack中所有的元素
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            this.finishAllActivity();
            //MobclickAgent.onKillProcess(context);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delayMillis时间后关闭应用
     */
    public void killApplication(final Context context, int delayMillis) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                AppExit(context);
            }
        }, delayMillis);
    }

}
