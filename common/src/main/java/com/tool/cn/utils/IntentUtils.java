package com.tool.cn.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.FileProvider;

import com.tool.cn.BaseConstants;

import java.io.File;

/**
 *  2017/2/21  12:12.
 *
 *
 * @version 1.0.0
 * @class IntentUtils
 * @describe Activity跳转、获取Intent意图工具类
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class IntentUtils {

    public static final String BUNDLE = "BUNDLE";

    /**
     * 通过类名启动Activity
     *
     * @param context 上下文对象
     * @param cls     要跳转的Activity
     */
    public static void openActivity(@NonNull Context context, Class<?> cls) {
        openActivity(context, cls, null);
    }

    /**
     * 通过类名启动Activity
     *
     * @param context 上下文对象
     * @param cls     要跳转的Activity
     */
    public static void startActivityForResult(@NonNull Context context, Class<?> cls, int requestCode) {
        startActivityForResult(context, cls, null, requestCode);
    }


    /**
     * 通过类名启动Activity
     *
     * @param context 上下文对象
     * @param cls     要跳转的Activity
     * @param bundle  需要传递的参数,通过IntentUtils.BUNDLE获取参数
     */
    public static void openActivity(@NonNull Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 通过类名启动Activity
     *
     * @param context 上下文对象
     * @param cls     要跳转的Activity
     * @param bundle  需要传递的参数,通过IntentUtils.BUNDLE获取参数
     */
    public static void startActivityForResult(@NonNull Context context, Class<?> cls, Bundle bundle, int requestCode) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, cls);
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            ((Activity) context).startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 开启相机
     */
    public static void openCamera(@NonNull Activity context, String photoPath) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 先验证手机是否有sd卡
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri = Uri.fromFile(new File(photoPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                context.startActivityForResult(intent, BaseConstants.CAMERE_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启相册
     */
    public static void openPhoto(@NonNull Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, BaseConstants.PHOTO_REQUEST_CODE);
    }

    /**
     * 打电话,跳到拨号界面
     *
     * @param context 上下文对象
     * @param mobile  电话号码
     */
    public static void callMobile(@NonNull Context context, String mobile) {
        if (!TextUtils.isEmpty(mobile)) {
            Uri uri = Uri.parse("tel:" + mobile);
            context.startActivity(new Intent(Intent.ACTION_DIAL, uri));
        } else {
            ToastUtils.showToastOnce(context, "号码为空");
        }
    }

    /**
     * 获取拨打电话意图
     * 需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}
     *
     * @param context     上下文对象
     * @param phoneNumber 电话号码
     * @return 拨打电话意图
     */
    public static Intent getCallIntent(@NonNull Context context, String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            ToastUtils.showToastOnce(context, "号码为空");
            return null;
        }
    }

    /**
     * 调用系统浏览器打开uri
     *
     * @param context 上下文
     * @param uriRes  需要打开的链接
     */
    public static void openSystemBrowser(@NonNull Context context, @StringRes int uriRes) {
        openSystemBrowser(context, context.getResources().getString(uriRes));
    }

    /**
     * 调用系统浏览器打开uri
     *
     * @param context 上下文
     * @param uri     需要打开的链接
     */
    public static void openSystemBrowser(@NonNull Context context, String uri) {
        openSystemBrowser(context, Uri.parse(uri));
    }

    /**
     * 调用系统浏览器打开uri
     *
     * @param context 上下文
     * @param uri     Uri链接
     */
    public static void openSystemBrowser(@NonNull Context context, Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 获取安装App（支持6.0）的意图
     *
     * @param filePath 文件路径
     * @return intent
     */
    public static Intent getInstallAppIntent(@NonNull Context context, @NonNull String filePath) {
        File file = FileUtils.getFileByPath(filePath);
        return null == file ? null : getInstallAppIntent(context, file);
    }

    /**
     * 获取安装App(支持6.0)的意图
     *
     * @param file 文件
     * @return intent
     */
    @SuppressWarnings("ConstantConditions")
    public static Intent getInstallAppIntent(@NonNull Context context, @NonNull File file) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type;

        if (Build.VERSION.SDK_INT < 23) {
            type = "application/vnd.android.package-archive";
        } else {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileUtils.getFileExtension(file));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.your.package.fileProvider", file);
            intent.setDataAndType(contentUri, type);
        }
        intent.setDataAndType(Uri.fromFile(file), type);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取卸载App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getUninstallAppIntent(@NonNull String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取打开App的意图
     *
     * @param context     上下文对象
     * @param packageName 包名
     * @return 打开App的意图
     */
    public static Intent getLaunchAppIntent(@NonNull Context context, @NonNull String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取App具体设置的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getAppDetailsSettingsIntent(@NonNull String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享文本的意图
     *
     * @param content 分享文本
     * @return intent
     */
    public static Intent getShareTextIntent(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   文本
     * @param imagePath 图片文件路径
     * @return intent
     */
    public static Intent getShareImageIntent(String content, String imagePath) {
        return getShareImageIntent(content, FileUtils.getFileByPath(imagePath));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 文本
     * @param image   图片文件
     * @return intent
     */
    public static Intent getShareImageIntent(String content, File image) {
        if (!FileUtils.isFileExists(image)) return null;
        return getShareImageIntent(content, Uri.fromFile(image));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 分享文本
     * @param uri     图片uri
     * @return intent
     */
    public static Intent getShareImageIntent(String content, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @return intent
     */
    public static Intent getComponentIntent(@NonNull String packageName, @NonNull String className) {
        return getComponentIntent(packageName, className, null);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     * @return intent
     */
    public static Intent getComponentIntent(@NonNull String packageName, @NonNull String className, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取关机的意图
     * 需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN"/>}
     *
     * @return intent
     */
    public static Intent getShutdownIntent() {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至拨号界面意图
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getDialIntent(@NonNull String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至发送短信界面的意图
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static Intent getSendSmsIntent(@NonNull String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取拍照的意图
     *
     * @param outUri 输出的uri
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(@NonNull Uri outUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        return intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private static final String MINETYPE = "application/vnd.android.package-archive";

    /**
     * 安装Apk文件
     *
     * @param context 上下文对象
     * @param data    Uri
     */
    public static void installApk(Context context, Uri data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, MINETYPE);
        // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
        promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(promptInstall);
    }

}
