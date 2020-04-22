package com.example.baidu.retrofit.fragment.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.baidu.retrofit.Adapter.home.ArticalAdapter;
import com.example.baidu.retrofit.Bean.home.ArticleBean;
import com.example.baidu.retrofit.Bean.home.DataBean;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.View.HwLoadingView;
import com.example.baidu.retrofit.util.RetrofitUtil;
import com.tool.cn.fragment.BaseFragment;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author
 * @date 2020/4/22.
 * GitHub：
 * email：
 * description：
 */
public class ArticalFragment extends BaseFragment {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.loading)
    HwLoadingView loading;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private ArticalAdapter mArticalAdapter;

    private static int page = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artical_fragment);
    }

    @Override
    protected void initView() {

        recycleView.setLayoutManager(new LinearLayoutManager(mContext));

//        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recycleView.setItemAnimator(new DefaultItemAnimator());

        mArticalAdapter = new ArticalAdapter(mContext, null);

        mArticalAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getArticalPage(page);
            }
        }, recycleView);

        recycleView.setAdapter(mArticalAdapter);

        swipeRefresh.setColorSchemeResources(R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.colorPrimary,
                R.color.mediumturquoise, R.color.darkviolet);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                getArticalPage(page);
            }
        });
    }

    @Override
    public void getHttpData() {
        getArticalPage(page);
    }

    private void getArticalPage(int page) {
        RetrofitUtil.getTestService().getWanArtical(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean<DataBean>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleBean<DataBean> dataBeanArticleBean) {
                        loading.stop();
                        swipeRefresh.setRefreshing(false);
                        loading.setVisibility(View.GONE);
                        ArticalFragment.this.page++;
                        int page = dataBeanArticleBean.getData().getCurPage();
                        int mPagesize = dataBeanArticleBean.getData().getPageCount();
                        if (page == ArticalFragment.this.page && page == 0) {
                            mArticalAdapter.setNewData(dataBeanArticleBean.getData().getDatas());
                        } else {
                            mArticalAdapter.addData(dataBeanArticleBean.getData().getDatas());
                        }
                        if (mPagesize <= 20) {
                            mArticalAdapter.loadMoreEnd();
                        } else {
                            mArticalAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefresh.setRefreshing(false);
                        mArticalAdapter.loadMoreEnd();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        page = 0;
    }
}
