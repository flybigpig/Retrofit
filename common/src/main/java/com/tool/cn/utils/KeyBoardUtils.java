package com.tool.cn.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 *  2016/11/9 14:05.
 *
 *
 * @version 1.0.0
 * @class KeyBoardUtils
 * @describe 软键盘相关工具类
 */
public class KeyBoardUtils{

    /**
     * 打开软键盘
     *
     * @param context  上下文对象
     * @param editText 输入框
     */
    @SuppressWarnings("unused")
    public static void openKeyBoard(@NonNull Context context, @NonNull EditText editText) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     */
    @SuppressWarnings("unused")
    public static void closeKeyBoard(@NonNull Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        View v = activity.getWindow().peekDecorView();
        if (null != v) { //收起软键盘
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 打开软键盘（兼容华为手机）
     * @param editText
     */
    public static void showKeyBoard(final EditText editText) {
        if (editText != null) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        InputMethodManager inputManager =
                                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(editText, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 300);

        }
    }
}