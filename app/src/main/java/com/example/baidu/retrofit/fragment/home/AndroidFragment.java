package com.example.baidu.retrofit.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Adapter.home.HomeAdapter;
import com.example.baidu.retrofit.Bean.home.AndroidBean;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.DividerGridItemDecoration;
import com.example.baidu.retrofit.util.RetrofitUtil;
import com.tool.cn.fragment.BaseFragment;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author
 * @date 2020/1/6.
 * GitHub：
 * email：
 * description：
 */
public class AndroidFragment extends BaseFragment {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private HomeAdapter mHomeAdapter;

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
}
