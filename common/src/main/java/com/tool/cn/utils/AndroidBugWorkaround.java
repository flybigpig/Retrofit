package com.tool.cn.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 *  2017/6/7  15:33.
 *
 *
 * @version 1.0.0
 * @class AndroidBugWorkaround
 * @describe 解决软键盘弹起时遮挡输入框的终极办法
 * <p>
 * 注：在全屏模式、使用沉浸式状态栏、h5等情况下设置adjestPan和adjestResize无效,
 * 这是Android自1.x到现在的一个Bug(https://code.google.com/p/android/issues/detail?id=5497)，
 * 此时可使用该方法解决。
 * <p>
 * 使用方法：在需要的Activity的onCreateff中添加 new AndroidBugWorkaround(this); 即可
 */
public class AndroidBugWorkaround {
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean isFullScreen;//true全屏，false非全屏
    private Activity mActivity;

    public AndroidBugWorkaround(Activity activity) {
        isFullScreen = true;
        init(activity);
    }

    public AndroidBugWorkaround(Activity activity, boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        init(activity);
    }

    private void init(Activity activity) {
        mActivity = activity;
        FrameLayout content = activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {//软键盘可能可见
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {//软键盘可能不可见
                //这样设置在有虚拟键开启的手机上如果输入框在底部，会导致虚拟键遮挡输入框
//                frameLayoutParams.height = usableHeightSansKeyboard;
                frameLayoutParams.height = -1;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect rect = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(rect);
        if (isFullScreen) {// 全屏模式下： return r.bottom
            //此处这样是为了减掉透明状态栏的高度,此处是25dp，具体项目具体调整
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return rect.bottom - DisplayUtils.dp2px(mActivity, 25);
            }
            return rect.bottom;
        } else {//非全屏时减去状态栏高度
            return rect.bottom - rect.top;
        }
    }

}
