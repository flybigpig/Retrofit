package com.tool.cn.dialog;

import android.content.Context;

import com.tool.cn.R;

/**
 *  2017/8/8  11:28.
 *
 *
 * @version 1.0.0
 * @class LoadingDialog
 * @describe LoadingView
 */
public class LoadingDialog extends BaseDialog {

    private static LoadingDialog loadingDialog;

    public LoadingDialog(Context context) {
        super(context, R.layout.dialog_loading, R.style.Theme_Dialog);
    }

    @Override
    protected void initView() {

    }

    /**
     * 显示等待框
     */
    public static void showLoadding(Context context) {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(context);
        }
        loadingDialog.show();
    }

    /**
     * 隐藏等待框
     */
    public static void dismissLoadding() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
    @Override
    public void onBackPressed() {
        dismissLoadding();
    }
}
