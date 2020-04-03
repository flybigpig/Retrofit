package com.example.baidu.retrofit.Activity.wanAndroid;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Adapter.home.ArticalAdapter;
import com.example.baidu.retrofit.Bean.home.ArticleBean;
import com.example.baidu.retrofit.Bean.home.DataBean;
import com.example.baidu.retrofit.Bean.home.DatasBean;
import com.example.baidu.retrofit.Bean.home.GongZhongHao;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArticalHistoryActivity extends Rx2Activity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private GongZhongHao mGongZhongHao;
    private int Page = 1;
    private ArticalAdapter mArticalAdapter;
    private List<DatasBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_history);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void nationalizationData(Properties prop) {

    }

    @Override
    protected void init() {
        super.init();
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        mGongZhongHao = bundle.getParcelable("item");

        recycleView.setLayoutManager(new LinearLayoutManager(this));

//        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recycleView.setItemAnimator(new DefaultItemAnimator());

        mArticalAdapter = new ArticalAdapter(this, null);

        recycleView.setAdapter(mArticalAdapter);
    }

    @Override
    protected void getHttp() {
        super.getHttp();
        if (mGongZhongHao.getId() > 0) {
            getGongZhongHaoHistory(mGongZhongHao.getId(), Page);
        }

    }

    private void getGongZhongHaoHistory(int id, int page) {

        RetrofitUtil.getTestService().getArticalHistory(mGongZhongHao.getId(), page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean<DataBean>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleBean<DataBean> dataBeanArticleBean) {
                        datas = dataBeanArticleBean.getData().getDatas();
                        mArticalAdapter.setNewData(dataBeanArticleBean.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
