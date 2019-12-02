package com.example.commonlibrary.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tool.cn.BaseApplication;
import com.tool.cn.R;

import java.util.Properties;

/**
 * 相机相册选择框
 *  on 2017/6/8.
 */

public class CameraPhotoDialog extends BaseDialog implements View.OnClickListener {

    TextView dialog_camera;
    TextView dialog_photo;
    TextView dialog_cancel;
    private OnPickerListener onPickerListener;
    private OnBackPressedListener onBackPressedListener;

    public CameraPhotoDialog(Context context, Properties prop) {
        super(context, R.layout.dialog_camera_photo, R.style.Theme_Dialog);
        dialog_camera.setText(prop.getProperty("app.securityCenter.verified.takePicture"));  //拍照
        dialog_photo.setText(prop.getProperty("app.securityCenter.verified.photoAlbum"));  //相册
        dialog_cancel.setText(prop.getProperty("app.deposit.details.btn.cacncle")); //取消
    }

    @Override
    protected void initView() {
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowLp = window.getAttributes(); // 获取对话框当前的参数值
        windowLp.width = BaseApplication.mWidthPixels;
        window.setAttributes(windowLp);

        dialog_camera = getViewById(R.id.dialog_camera);
        dialog_photo = getViewById(R.id.dialog_photo);
        dialog_cancel = getViewById(R.id.dialog_cancel);

        setOnClickListeners(this, dialog_camera, dialog_photo, dialog_cancel);
    }

    @Override
    public void onClick(final View v) {
        dismiss();
        if (onPickerListener != null) {
            onPickerListener.onPickerListener(v);
        }
    }

    public interface OnPickerListener {
        void onPickerListener(View view);
    }
    public void setOnPickerListener(OnPickerListener onClickListener) {
        onPickerListener = onClickListener;
    }

    public interface OnBackPressedListener {
        void onBackPressed();
    }

   public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener){
       this.onBackPressedListener =onBackPressedListener;
   }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (onBackPressedListener != null) {
            onBackPressedListener.onBackPressed();
        }
    }
}
