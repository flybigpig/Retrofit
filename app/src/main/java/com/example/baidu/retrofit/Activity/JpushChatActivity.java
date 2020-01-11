package com.example.baidu.retrofit.Activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baidu.retrofit.R;
import com.tool.cn.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;

public class JpushChatActivity extends BaseActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;

    private String names;
    private String password;

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
        password = "123456";
    }


    @Override
    protected void getHttp() {

    }
}
