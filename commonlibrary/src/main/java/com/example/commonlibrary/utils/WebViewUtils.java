package com.example.commonlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tool.cn.BaseApplication;

/**
 *  2016/12/18 12:26.
 *
 *
 * @version 1.0.0
 * @class WebViewUtils
 * @describe WebView工具类
 */
@SuppressWarnings("ConstantConditions")
public class WebViewUtils {
    private static final String TAG = "WebViewUtils";
    private static CustomWebViewClient mCustomWebViewClient;
    private static boolean loadError;

    /**
     * 初始化WebView
     *
     * @param webView WebView
     */
    public static void initWebView(@NonNull WebView webView) {
        // 获取WebView属性
        WebSettings ws = webView.getSettings();
        // 设置支持javascript代码
        ws.setJavaScriptEnabled(true);
        //缩放操作
        ws.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        ws.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        ws.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        ws.setAllowFileAccess(true); //设置可以访问文件
        ws.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        ws.setLoadsImagesAutomatically(true); //支持自动加载图片
        ws.setDefaultTextEncodingName("utf-8");//设置编码格式
        ws.setTextZoom(100); //字体大小不随系统设置变化
        ws.setAllowFileAccess(true);
        ws.setDatabaseEnabled(true);
        ws.setDomStorageEnabled(true);
        //设置自适应屏幕，两者合用
        ws.setUseWideViewPort(true); //将图片调整到适合webview的大小
        ws.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        ws.setSaveFormData(false);
        String cacheDirPath = BaseApplication.getContext().getFilesDir().getAbsolutePath() + "cache/";
        ws.setAppCachePath(cacheDirPath);  // 1. 设置缓存路径
        ws.setAppCacheMaxSize(20 * 1024 * 1024);   // 2. 设置缓存大小
        ws.setAppCacheEnabled(true);  // 3. 开启Application Cache存储机制
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setUserAgentString(ws.getUserAgentString() + " Name/FastBankAndorid");
        ws.setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onPageStarted();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (mCustomWebViewClient != null) {
                    loadError = true;
                    mCustomWebViewClient.onReceivedError();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mCustomWebViewClient != null) {
                    if (!loadError && NetworkUtils.isConnected(BaseApplication.getContext())) {
                        mCustomWebViewClient.onPageFinished();
                    }
                    loadError = false;
                }
            }

            /**
             * 处理https请求，为WebView处理ssl证书设置
             * @param view
             * @param handler
             * @param error
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受信任所有网站的证书
                //handler.cancel();   // 默认操作 不处理
                //handler.handleMessage(null);  // 可做其他处理
                //super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    public static void setCustomWebViewClient(CustomWebViewClient customWebViewClient) {
        mCustomWebViewClient = customWebViewClient;
    }

    public interface CustomWebViewClient {

        void onPageStarted();

        void onReceivedError();

        void onPageFinished();
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

    public static void setRichText(@NonNull WebView webView, String baseUrl, String richText) {
        webView.loadDataWithBaseURL(baseUrl,
                "<head><meta name=\"viewport\" content=\"width=device-width," +
                        " initial-scale=1.0,user-scalable=no\"/><style type=\"text/css\">" +
                        "img,table,video{max-width: 100% !important;height:auto !important;}" +
                        "</style></head>" +
                        richText,
                "text/html", "UTF-8", null);
//        webView.loadDataWithBaseURL(ServerURL.SERVER_API_URL,
//                "<head><meta name=\"viewport\" content=\"width=device-width, " +
//                        "initial-scale=1.0,user-scalable=no\"/>" +
//                        "<style type=\"text/css\">img,table," +
//                        "video{ max-width: 100% !important;  }" +
//                        "</style></head>" +
//                        richText,
//                "text/html", "UTF-8", null);
    }

    /**
     * WebView加载assets目录下html文件
     *
     * @param webView WebView
     * @param name    文件名(需要带后缀".html")
     */
    public static void loadAssetsHtml(@NonNull WebView webView, String name) {
        webView.loadUrl("file:///android_asset/" + name);
    }

    /**
     * WebView加载链接
     *
     * @param webView WebView
     * @param url     URL路径
     */
    public static void loadUrl(@NonNull WebView webView, String url) {
        webView.loadUrl(url);
    }


    /**
     * WebView加载H5代码块
     *
     * @param webView  WebView
     * @param htmlData 代码块
     */
    public static void loadDataWithBaseURL(@NonNull WebView webView, String htmlData) {
        webView.loadDataWithBaseURL("about:blank", htmlData, "text/html", "utf-8", null);
    }

    public static void onPause(@NonNull WebView webView) {
        try {
            if (webView != null) {
                webView.onPause();
                webView.stopLoading();
                webView.reload();
            }
        } catch (Exception e) {
            Log.e(TAG, "WebViewUtils: WebView onPause happend Exception");
        }
    }

    public static void onResume(@NonNull WebView webView) {
        if (webView != null) {
            webView.onResume();
        }
    }

    public static void onDestroy(@NonNull WebView webView) {
        if (webView != null) {
            webView.loadUrl("");
            webView.stopLoading();
        }
    }

    public static void onKeyDown(@NonNull Activity activity, @NonNull WebView webView, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null) {
                webView.loadUrl("");
                webView.stopLoading();
            }
            activity.finish();
        }
    }

}
