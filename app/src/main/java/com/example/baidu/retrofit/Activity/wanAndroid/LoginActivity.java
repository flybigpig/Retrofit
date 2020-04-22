package com.example.baidu.retrofit.Activity.wanAndroid;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Bean.home.ArticalBean;
import com.example.baidu.retrofit.Bean.home.ArticleBean;
import com.example.baidu.retrofit.Bean.home.ResultBean;
import com.example.baidu.retrofit.Bean.home.UserBean;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.RetrofitUtil;

import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends Rx2Activity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.name_icon)
    ImageView nameIcon;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password_icon)
    ImageView passwordIcon;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    TextView register;

    private String names;
    private String passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        ButterKnife.bind(this);
    }

    @Override
    protected void nationalizationData(Properties prop) {

    }

    @Override
    protected void init() {
        super.init();
        names = name.getText().toString().trim();
        passwords = password.getText().toString().trim();

    }

    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                login(names, passwords);
                break;
            case R.id.register:
                break;
        }
    }

    private void login(String name, String password) {
//        if(TextUtils.isEmpty(name) && TextUtils.isEmpty(passwords)){
//            Toast.makeText(mContext,"请检查账号名称和密码",Toast.LENGTH_LONG).show();
//            return;
//        }
//        RetrofitUtil.getTestService().login("294722929@qq.com", "JaPMwf7m2bmbVVb").subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResultBean<UserBean>>() {
//
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(ResultBean<UserBean> userBeanResultBean) {
//                        Log.d("UserBean", userBeanResultBean.getData().getCollectids().size() + "");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }
}
