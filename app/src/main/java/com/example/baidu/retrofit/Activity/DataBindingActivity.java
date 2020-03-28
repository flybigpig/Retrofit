package com.example.baidu.retrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.baidu.retrofit.R;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_binding);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
//        ActivityDataBindingBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
////
//        User userBean = new User();
//        userBean.setUser("姜涛");
//
//        // setUser这个方法根据Variable标签的name属性自动生成
//        // viewDataBinding.setIsLogin(true);
//        if (viewDataBinding.getIsLogin()) {
//            viewDataBinding.setUser(userBean);
//        } else {
//            userBean.setUser("hahha");
//            viewDataBinding.setUser(userBean);
//        }
    }
}
