package com.example.baidu.retrofit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.View.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.login2_zhanghao)
    EditText login2Zhanghao;
    @BindView(R.id.login2_mima)
    EditText login2Mima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
    }

    /**
     * 这里做提示
     *
     * @param msg 我们所想提示的内容
     */
    @Override
    public void shouToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 在这做一个简单的登陆成功后的跳转
     */
    @Override
    public void onSuccess() {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    /**
     * 对外提供获取用户输入的账号
     *
     * @return
     */
    @Override
    public String getName() {
        return login2Zhanghao.getText().toString();
    }

    /**
     * 对外提供获取用户输入的密码
     *
     * @return
     */
    @Override
    public String getPassWord() {
        return login2Mima.getText().toString();
    }
}
