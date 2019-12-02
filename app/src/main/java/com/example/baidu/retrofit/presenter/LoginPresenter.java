package com.example.baidu.retrofit.presenter;

import com.example.baidu.retrofit.View.LoginView;
import com.example.baidu.retrofit.contract.LoginContract;
import com.example.baidu.retrofit.model.LoginModel;

public class LoginPresenter implements Login2Presenter {

    private LoginView login2View;

    LoginModel model;

    /**
     * 这里我们写一个带参构造方法
     *
     * @param login2View 哪个View调用我们，就需要传哪个View的参数
     */
    public LoginPresenter(LoginView login2View) {
        this.login2View = login2View;
    }

    /**
     * 下面都是实现我们刚刚在接口中的方法
     */
    @Override
    public void onSuccess() {
        login2View.onSuccess();
    }

    @Override
    public void onfail(String msg) {
        login2View.shouToast(msg);
    }


}
