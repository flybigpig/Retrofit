package com.example.baidu.retrofit.util;

import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import com.example.baidu.retrofit.Activity.other.FirstActivity;
import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Bean.ResultBean;
import com.example.baidu.retrofit.Constants;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.tool.cn.utils.IntentUtils;
import com.tool.cn.utils.LogUtils;
import com.tool.cn.utils.NetworkUtils;
import com.tool.cn.utils.PreferencesManager;
import com.tool.cn.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<ResultBean<T>> {

    private Rx2Activity rx2Activity;
    public static final int RESPONSE_CODE_OK = 0;       //自定义的业务逻辑，成功返回积极数据
    public final int NO_NETWORK_CODE = 1;       //无网络
    private final int SOCKET_TIMEOUT_CODE_FAILED = -1;  //服务器响应超时
    private final int CONNECT_CODE_FAILED = -2;  //连接服务器失败
    private final int JSON_SYNTAX_FAILED = -3;  //Json解析异常
    private final int SQLITE_FAILED = -4;  //本地数据库异常
    private final int LOG_OUT_OF_DATE = 401;  //登录过期
    private final int DUPLICATE_LOGON = 502;  //账号重复登录
    private final int NO_SET_PASSWORD = 1202;  //未设置交易密码
    public static final int PASSWORD_ERROR_LOGON = 13;  //登录密码输出两次进行图形验证
    private final int NEW_VERSION_NOTICE = -11;  //新版本发布
    private final int NODE_BANKS_ARE_BANNED = -12;  //当前节点银行被禁用
    private boolean isShowProgress; //是否显示dialog
    private String disposableKey;
    private static final String TAG = "BaseObserver";
    private String stNewVersion = "New version"; //新版本

    /**
     * 根据具体的Api 业务逻辑去重写 onSuccess 方法
     */
    public abstract void onSuccess(T t);

    /**
     * 兼容其他业务数据字段
     */
    public void onCompatSuccess(Object data) {

    }

    public void onIsSuccess(boolean isSuccess) {
        if (isShowProgress) {

        }
    }

    /**
     * 分页加载回调
     */
    public void onPageLoading(int pageSize, int totalCount) {

    }

    /**
     * @param rx2Activity * @param showProgress 默认需要显示进程，不要的话请传 false
     */
    public BaseObserver(Rx2Activity rx2Activity, boolean showProgress) {
        this.rx2Activity = rx2Activity;
        isShowProgress = showProgress;
    }


    /**
     * 防止重复请求
     *  @param rx2Activity
     *
     */
    public BaseObserver(Rx2Activity rx2Activity) {
        this.rx2Activity = rx2Activity;
        this.disposableKey = disposableKey;
    }

    public BaseObserver(Rx2Activity rx2Activity, boolean showProgress, String disposableKey) {
        this.rx2Activity = rx2Activity;
        isShowProgress = showProgress;
        this.disposableKey = disposableKey;
    }

    @Override
    public final void onSubscribe(Disposable d) { //不管取消，和生命周期绑定
        if (!NetworkUtils.isConnected(rx2Activity)) {
            d.dispose();
            int errorCode = NO_NETWORK_CODE;
            String errorMsg = Constants.noNetwork;
            onFailure(errorCode, errorMsg);
        } else {
            if (isShowProgress) {

            }
            rx2Activity.addDisposable(d);
            if (!TextUtils.isEmpty(disposableKey)) { // //防止重复请求
                rx2Activity.unDisposable(disposableKey);
                rx2Activity.saveDisposable(disposableKey, d);
            }
        }
    }

    @Override
    public void onNext(ResultBean<T> response) {
        onPageLoading(response.pageSize, response.totalCount);
        if (response.code == RESPONSE_CODE_OK) {
            ToastUtils.showToastOnce(rx2Activity, response.msg);
            onIsSuccess(response.success);
            if (response.extend != null) {
                onCompatSuccess(response.extend);
            }
            if (response.items != null) {
                onSuccess(response.items);
            } else if (response.data != null) {
                onSuccess(response.data);
            }
            if (response.results != null) {
                onSuccess(response.results);
            }
        } else {
            onFailure(response.code, response.msg);
        }
    }

    @Override
    public void onError(Throwable e) {
        int errorCode = 0;
        String errorMsg = "";
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
        } else if (e instanceof SocketTimeoutException) {
            errorCode = SOCKET_TIMEOUT_CODE_FAILED;
            errorMsg = "服务器响应超时";
        } else if (e instanceof ConnectException) {
            errorCode = CONNECT_CODE_FAILED;
            errorMsg = "连接服务器失败";
        } else if (e instanceof JsonSyntaxException) {
            JsonSyntaxException jsonException = (JsonSyntaxException) e;
            errorCode = JSON_SYNTAX_FAILED;
            errorMsg = jsonException.getMessage();
        } else if (e instanceof SQLiteException) {
            SQLiteException jsonException = (SQLiteException) e;
            errorCode = SQLITE_FAILED;
            errorMsg = jsonException.getMessage();
        }
        onFailure(0, Constants.retryAfterTips);
    }

    @Override
    public void onComplete() {
    }

    public void onFailure(int code, String message) {
        if (code != NO_NETWORK_CODE) {
            if (isShowProgress) {

            }
            switch (code) {
                case NEW_VERSION_NOTICE:  //通知新版本更新
                    break;
                case LOG_OUT_OF_DATE:
                case DUPLICATE_LOGON:  //token过期重新登录
                    PreferencesManager.getInstance(rx2Activity).put(Constants.IS_LOGIN, false);
                    PreferencesManager.getInstance(rx2Activity).put(Constants.API_KEY, "");
                    PreferencesManager.getInstance(rx2Activity).put(Constants.TICKET, "");
                    IntentUtils.openActivity(rx2Activity, FirstActivity.class);
                    break;
                case NO_SET_PASSWORD: //未设置交易密码

                    break;
                case NODE_BANKS_ARE_BANNED: //当前节点银行被禁用

                    break;
            }
        }
        ToastUtils.showToastOnce(rx2Activity, message);
        LogUtils.d(TAG, "errorCode:" + code + "  errorMsg:" + message);
    }

}