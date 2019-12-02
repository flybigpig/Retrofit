package com.example.baidu.retrofit.Activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.baidu.retrofit.Adapter.GanhuoApiAdapter;
import com.example.baidu.retrofit.Adapter.ListActivityAdapter;
import com.example.baidu.retrofit.Api;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.Bean.StudyBean;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.DividerGridItemDecoration;
import com.example.baidu.retrofit.util.RetrofitUtil;
import com.example.baidu.retrofit.util.StaggeredDividerItemDecoration;
import com.example.baidu.retrofit.util.Versionchecklib;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends Rx2Activity {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private Api apis;
    private String TAG = "Mainactivity";
    private ListActivityAdapter activityAdapter;
    private List<StudyBean> listString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void nationalizationData(Properties prop) {

    }

    @Override
    protected void init() {
        super.init();
        listString = new ArrayList<>();
        activityAdapter = new ListActivityAdapter(null);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置分隔线
        recycleView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //设置Adapter
        recycleView.setAdapter(activityAdapter);
        initData();
    }

    private void initData() {

        listString.add(new StudyBean("recycleView加载更多", "", "url"));

        listString.add(new StudyBean("图片流式布局", "", "url"));

        listString.add(new StudyBean("+", "", "url"));


        activityAdapter.setNewData(listString);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    protected void getHttp() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
