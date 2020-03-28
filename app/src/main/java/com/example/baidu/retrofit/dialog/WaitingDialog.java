package com.example.baidu.retrofit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.baidu.retrofit.R;

/**
 * @author
 * @date 2020/3/26.
 * GitHub：
 * email：
 * description：
 */
public class WaitingDialog extends Dialog {


    public WaitingDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams windowLp = window.getAttributes(); // 获取对话框当前的参数值
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        windowLp.width = (int) metrics.widthPixels * 9 / 10;
        window.setAttributes(windowLp);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.layout_waiting, null);
        this.setContentView(mView);
    }


}
