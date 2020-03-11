package com.example.baidu.retrofit.fragment.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Activity.wanAndroid.HomePageActivity;
import com.example.baidu.retrofit.Adapter.home.HomeAdapter;
import com.example.baidu.retrofit.Bean.home.AndroidBean;
import com.example.baidu.retrofit.MyApplication;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.DividerGridItemDecoration;
import com.example.baidu.retrofit.util.RetrofitUtil;
import com.tool.cn.fragment.BaseFragment;
import com.tool.cn.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author
 * @date 2020/1/3.
 * GitHub：
 * email：
 * description：
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private HomeAdapter mHomeAdapter;
    private String REPORT_FRAGMENT_TAG="home";

    public HomeFragment getInstance(String type) {
        HomeFragment f = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment);
    }


    @Override
    protected void initView() {
        mHomeAdapter = new HomeAdapter(null);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置分隔线
        recycleView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        //设置增加或删除条目的动画
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //设置Adapter
        recycleView.setAdapter(mHomeAdapter);
        if (mContext == null) {
            mContext = getActivity();

        }
    }

    @Override
    public void getHttpData() {
        Bundle bundle = getArguments();
        Log.d(TAG, "getHttpData: " + bundle.getString("type"));
        if (mContext != null) {
            RetrofitUtil.getTestService().getArticle(10, 1).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<AndroidBean>>((Rx2Activity) mContext, false, "getAndroidHome") {
                        @Override
                        public void onSuccess(List<AndroidBean> androidBeans) {
                            mHomeAdapter.setNewData(androidBeans);
                        }
                    });
        }

    }


    private void test() {

//        android.app.FragmentManager manager = getActivity().getFragmentManager();
//        if (manager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {
//            manager.beginTransaction().add(new HomeFragment(), REPORT_FRAGMENT_TAG).commit();
//            // Hopefully, we are the first to make a transaction.
//            manager.executePendingTransactions();
//        }

//        RxPermissionsFragment rxPermissionsFragment = findRxPermissionsFragment(fragmentManager);
//        boolean isNewInstance = rxPermissionsFragment == null;
//        if (isNewInstance) {
//            rxPermissionsFragment = new RxPermissionsFragment();
//            fragmentManager
//                    .beginTransaction()
//                    .add(rxPermissionsFragment, TAG)
//                    .commitNow();
//        }
//        return rxPermissionsFragment;
    }


}
