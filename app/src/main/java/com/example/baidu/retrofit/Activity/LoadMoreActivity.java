package com.example.baidu.retrofit.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.baidu.retrofit.Adapter.GanhuoApiAdapter;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.DividerGridItemDecoration;
import com.example.baidu.retrofit.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 加载更多
 * 需要了解原理
 */
public class LoadMoreActivity extends Rx2Activity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private List<GanhuoNews> listGanHuos;
    private GanhuoApiAdapter ganHuoAdapter;
    private int PAGE = 1;
    private int mPageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
    }

    @Override
    protected void init() {
        super.init();
        listGanHuos = new ArrayList<>();
        ganHuoAdapter = new GanhuoApiAdapter(null);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置分隔线
        recycleView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //设置Adapter
        recycleView.setAdapter(ganHuoAdapter);
        ganHuoAdapter.setEnableLoadMore(false);
        ganHuoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

            @Override
            public void onLoadMoreRequested() {
                getGanHuos(10, PAGE);
            }
        }, recycleView);
        swipeRefresh.setColorSchemeResources(R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.colorPrimary,
                R.color.mediumturquoise, R.color.darkviolet);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PAGE = 1;
                getGanHuos(11, PAGE);
            }
        });
    }

    @Override
    protected void getHttp() {
        getGanHuos(20, PAGE);

    }

    /**
     * 查询新闻
     */
    private void getGanHuos(final int count, final int page) {
        RetrofitUtil.getTestService().getNews(count, PAGE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<List<GanhuoNews>>((Rx2Activity) mContext) {
            @Override
            public void onPageLoading(int pageSize, int totalCount) {
                mPageSize = 15;

            }

            @Override
            public void onSuccess(List<GanhuoNews> ganhuoNews) {
                swipeRefresh.setRefreshing(false);
                if (ganhuoNews.size() > 0) {
                    if (PAGE == 1) {
                        ganHuoAdapter.setNewData(ganhuoNews);
                    } else {
                        ganHuoAdapter.addData(ganhuoNews);
                    }
                    if (mPageSize < count) {
                        ganHuoAdapter.loadMoreEnd();
                    } else {
                        ganHuoAdapter.loadMoreComplete();
                        PAGE++;
                    }
                } else {
                    ganHuoAdapter.loadMoreEnd();
                }
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
                ganHuoAdapter.loadMoreFail();
                swipeRefresh.setRefreshing(false);
                ganHuoAdapter.getData().clear();
                ganHuoAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void nationalizationData(Properties prop) {

    }
}
