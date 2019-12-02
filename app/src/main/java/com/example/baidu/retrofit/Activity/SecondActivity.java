package com.example.baidu.retrofit.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.RetrofitUtil;

import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends Rx2Activity {

    @BindView(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void getHttp() {

    }

    @Override
    protected void nationalizationData(Properties prop) {


    }

    @Override
    protected void init() {
        super.init();
        inits();
    }

    private void inits() {

    }
}
