package com.example.baidu.retrofit.Activity.wanAndroid;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.baidu.retrofit.R;
import com.tool.cn.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        WebSettings settings = null;
        if (webView != null) {
            settings = webView.getSettings();
        }
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        //自己处理所有的网络请求，而不是打开浏览器
        WebViewClient client = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        };
        webView.setWebViewClient(client);
        String url = bundle.getString("url");
        webView.loadUrl(url);

        //使webView监听返回键，能够进行网页的返回
        webView.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                            webView.goBack();
                            return true;
                        }
                        return false;
                    }
                }
        );

//        TextView title = (TextView) findViewById(R.id.title);
//        title.setSelected(true);
    }

    @Override
    protected void getHttp() {

    }
}
