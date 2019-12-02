package com.example.baidu.retrofit.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.tool.cn.BaseApplication;
import com.tool.cn.dialog.BaseDialog;
import com.tool.cn.utils.DisplayUtils;

public class CustomDialog extends BaseDialog {

    public CustomDialog(Context context, int layoutId, int theme) {
        super(context, layoutId, theme);
    }

    @Override
    protected void initView() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams windowLp = window.getAttributes(); // 获取对话框当前的参数值
        windowLp.width = BaseApplication.mWidthPixels - DisplayUtils.dp2px(mContext, 100);
        window.setAttributes(windowLp);
        setCanceledOnTouchOutside(false); // 设置点击屏幕Dialog不消失
    }
}
