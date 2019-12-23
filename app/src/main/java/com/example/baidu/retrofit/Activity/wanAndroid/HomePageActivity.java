package com.example.baidu.retrofit.Activity.wanAndroid;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.R;

import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends Rx2Activity {


    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected void getHttp() {

    }

    @Override
    protected void nationalizationData(Properties prop) {

    }


}
