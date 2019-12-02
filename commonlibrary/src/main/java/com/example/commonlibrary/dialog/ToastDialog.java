package com.example.commonlibrary.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tool.cn.BaseApplication;
import com.tool.cn.R;
import com.tool.cn.utils.DisplayUtils;


/**
 *  on 2017/6/6.
 */

public class ToastDialog extends BaseDialog implements View.OnClickListener {

    TextView tvTitle;
    TextView text_dialog_cancel;
    TextView tv_dialog_confirm;

    private OnConfirmListener mOnClickListener;

    public ToastDialog(Context context, String title,String cancel,String ok) {
        super(context, R.layout.dialog_toast, R.style.Theme_Dialog);
        tvTitle.setText(title);
        text_dialog_cancel.setText(cancel); //取消
        tv_dialog_confirm.setText(ok); //确认
    }

    @Override
    protected void initView() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams windowLp = window.getAttributes(); // 获取对话框当前的参数值
        windowLp.width = BaseApplication.mWidthPixels - DisplayUtils.dp2px(mContext, 100);
        window.setAttributes(windowLp);
        tvTitle = getViewById(R.id.tv_title);
        text_dialog_cancel = getViewById(R.id.tv_dialog_cancel);
        tv_dialog_confirm = getViewById(R.id.tv_dialog_confirm);
        setOnClickListeners(this, text_dialog_cancel, tv_dialog_confirm);

    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (mOnClickListener == null) {
            return;
        }
        if (view.getId() == R.id.tv_dialog_cancel) {
            mOnClickListener.onCancelDialog();
        } else if (view.getId() == R.id.tv_dialog_confirm) {
            mOnClickListener.onConfirmListener(view);
        }
    }

    public interface OnConfirmListener {
        void onConfirmListener(View view);

        void onCancelDialog();
    }

    public void setOnConfirmListenerr(OnConfirmListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
