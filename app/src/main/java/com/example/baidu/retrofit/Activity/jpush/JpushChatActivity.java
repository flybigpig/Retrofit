package com.example.baidu.retrofit.Activity.jpush;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.baidu.retrofit.Constants;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BitmapUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.tool.cn.activity.BaseActivity;
import com.tool.cn.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class JpushChatActivity extends BaseActivity {


    @BindView(R.id.account)
    TextInputEditText account;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    TextView register;

    private String names;
    private String passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_chat);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        // c = (b = Pattern.compile(z[6])  字符串-6
        names = "flasdasdasdasdas1dasday";
        passwords = "123456";
    }

    /**
     * 登录
     */
    private void login() {
        String name = account.getText().toString();
        String pwd = password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToastOnce(mContext, "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showToastOnce(mContext, "密码不能为空");
            return;
        }
        JMessageClient.login(names, passwords, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.d(TAG, i + " -----" + s);
                if (i == 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                setUserImage();
                                Thread.sleep(2000);
                                startActivity(new Intent(JpushChatActivity.this, ChatUIActivity.class));
                                Looper.prepare();
                                password.setText("");
                                Looper.loop();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
            }
        });
    }

    private void setUserImage() {
        
    }


    @Override
    protected void getHttp() {

    }

    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
