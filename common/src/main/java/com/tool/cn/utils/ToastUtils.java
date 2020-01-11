package com.tool.cn.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 *  2017/2/22  11:30.
 *
 *
 * @version 1.0.0
 * @class ToastUtils
 * @describe Toast显示时间根据文字长短确定, 提供多次提示只显示一次的方案,
 * 提供带图片并居中显示的Toast
 */
public class ToastUtils{

    private static final int textLengthSign = 6;
    private static long mLastShowTime = 0;
    private static String mLastShowMessage = null;

    /**
     * 居中带图片的toast
     *
     * @param context     上下文
     * @param msg         消息
     * @param drawableRes 图片id
     */
    @SuppressWarnings("unused")
    public static void showToastWithPic(@NonNull Context context, String msg, @DrawableRes int drawableRes) {
        if (TextUtils.isEmpty(msg) && context.getResources().getDrawable(drawableRes) == null) {
            return;
        }
        Toast toast = makeText(context, msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(drawableRes);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    /**
     * 默认
     *
     * @param context 上下文
     * @param msg     消息
     */
    public static void showToastDefault(@NonNull Context context, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = makeText(context, msg);
        toast.show();
    }

    /**
     * 显示Toast，多个同样消息的Toast只显示一次
     *
     * @param context 上下文
     * @param msg     消息
     */
    public static void showToastOnce(@NonNull Context context, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast;
        if (msg.equals(mLastShowMessage)) {
            if (System.currentTimeMillis() - mLastShowTime > 3000) {
                toast = makeText(context, msg);
                toast.show();
                mLastShowTime = System.currentTimeMillis();
            }
        } else {
            showToastDefault(context, msg);
            mLastShowMessage = msg;
            mLastShowTime = System.currentTimeMillis();
        }
    }

    /**
     * 显示Toast，多个同样消息的Toast只显示一次
     *
     * @param context   上下文
     * @param stringRes 消息id
     */
    public static void showToastOnce(@NonNull Context context, @StringRes int stringRes) {
        if (TextUtils.isEmpty(context.getResources().getString(stringRes))) {
            return;
        }
        showToastOnce(context, context.getString(stringRes));
    }

    /**
     * 创建Toast对象
     *
     * @param context 上下文
     * @param msg     消息
     * @return Toast对象
     */
    private static Toast makeText(Context context, String msg) {
        if (msg.isEmpty()) {
            return Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else if (msg.length() > textLengthSign) {
            return Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            return Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
    }

}
