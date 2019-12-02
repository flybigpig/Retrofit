package com.example.baidu.retrofit.Activity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import com.example.baidu.retrofit.Constants;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.receiver.NetworkConnectChangedReceiver;
import com.example.baidu.retrofit.util.Event;
import com.threshold.rxbus2.RxBus;
import com.tool.cn.activity.BaseActivity;
import com.tool.cn.utils.FileUtils;
import com.tool.cn.utils.LogUtils;
import com.tool.cn.utils.NetworkUtils;
import com.tool.cn.utils.PreferencesManager;
import com.tool.cn.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public abstract class Rx2Activity extends BaseActivity {

    private CompositeDisposable compositeDisposable; //统一管理所有的订阅生命周期
    private Disposable disposable;
    public Properties prop;
    private NetworkConnectChangedReceiver mNetWorkChangReceiver;
    private Map<String, Disposable> mSubscriptionMap; //防止重复请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        registerNetworkConnectChanged();
        registerEvent();
        registerStickyEvent();
    }

    @Override
    protected void init() {
//        String appLan = PreferencesManager.getInstance(mContext).get(Constants.APP_LAN);
//        prop = FileUtils.loadPropertiesFile(Constants.DOWNLOAD_PATH + Constants.FILE_PATH, appLan);
//        if (prop != null) {
//            Constants.nationalizationData(prop);
//            nationalizationData(prop);
//        } else {
//            PreferencesManager.getInstance(this).put(Constants.IS_FIRST_LOAD, true);
//            PreferencesManager.getInstance(mContext).put(appLan, 0L);
//        }
    }

    /**
     * 国际化数据
     */
    protected abstract void nationalizationData(Properties prop);

    public void addDisposable(Disposable disposable) {
        this.disposable = disposable;
        compositeDisposable.add(disposable);
    }

    public void saveDisposable(String key, Disposable s) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        mSubscriptionMap.put(key, s);
    }

    public void unDisposable(String key) {
        if (mSubscriptionMap != null) {
            Disposable disposable = mSubscriptionMap.get(key);
            if (disposable != null) {
                disposable.dispose();
                mSubscriptionMap.remove(key);
            }
        }
    }

    /**
     * 取消当前请求
     */
    public void cancel() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void clear() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /**
     * 解绑
     */
    protected void dispose() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    /**
     * 验证码倒计时
     */
    protected void codeCountDown(final TextView textView) {
        //点击后置为不可点击状态
        textView.setEnabled(false);
        //从0开始发射11个数字为：0-10依次输出，延时0s执行，每1s发射一次。
        compositeDisposable.add(Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        textView.setText(60 - aLong + "s");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        //倒计时完毕置为可点击状态
                        textView.setEnabled(true);
                        textView.setText(Constants.getCaptcha);
                    }
                })
                .subscribe());
    }


    /**
     * 注册网络状态监听广播
     */
    private void registerNetworkConnectChanged() {
        mNetWorkChangReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkChangReceiver, filter);
    }

    /**
     * 订阅rxbus普通事件
     */
    public void registerEvent() {
        if (useEventBus()) {
            Disposable disposable = RxBus.getDefault()
                    .ofType(Event.class)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Event>() {
                        @Override
                        public void accept(Event event) {
                            int eventCode = event.getCode();
                            LogUtils.d("RxBus:", "code=" + eventCode + ",data=" + event.toString());
                            switch (eventCode) {
                                case 0:
                                    netStateChangedUI(NetworkUtils.isConnected(mContext));
                                    break;
                                case 6: //切换语言
                                    prop = FileUtils.loadPropertiesFile(Constants.DOWNLOAD_PATH + Constants.FILE_PATH, PreferencesManager.getInstance(mContext).get(Constants.APP_LAN));
                                    if (prop != null) {
                                        Constants.nationalizationData(prop);
                                        nationalizationData(prop);
                                    }
                                    break;
                                default:
                                    onEvent(event);
                                    break;
                            }
                        }
                    });
            compositeDisposable.add(disposable);
        }
    }

    /**
     * 订阅rxbus粘性事件
     */
    public void registerStickyEvent() {
        if (useStickyEventBus()) {
            //注册事件
            Disposable disposable = RxBus.getDefault()
                    .ofStickyType(Event.class)
                    .observeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Event>() {
                        @Override
                        public void accept(Event event) {
                            LogUtils.d("RxBus:", "code=" + event.getCode() + ",data=" + event.toString());
                            onStickyEvent(event);
                        }
                    });
            compositeDisposable.add(disposable);
        }
    }

    /**
     * 订阅普通事件
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 订阅粘滞事件
     *
     * @return
     */
    protected boolean useStickyEventBus() {
        return false;
    }

    protected void onEvent(Event event) {

    }

    protected void onStickyEvent(Event event) {

    }

    protected void netStateChangedUI(boolean isConnected) {
        /*判断当前网络时候可用以及网络类型*/
        if (!isConnected) {
            ToastUtils.showToastOnce(this, Constants.noNetwork);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mNetWorkChangReceiver);
        clear();
        dispose();
        super.onDestroy();
    }
}