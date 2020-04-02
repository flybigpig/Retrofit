package com.example.baidu.retrofit.Activity.other;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Adapter.FirstAdapter;
import com.example.baidu.retrofit.Adapter.StaggeredHomeAdapter;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 图片加载
 */
public class FirstActivity extends Rx2Activity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private FirstAdapter firstAdapter;
    private StaggeredHomeAdapter mStaggeredHomeAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void getHttp() {

    }

    @Override
    protected void nationalizationData(Properties prop) {

    }

    @Override
    protected void init() {
        super.init();
        // 创建StaggeredGridLayoutManager实例
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        recycleView.setItemAnimator(null);
//        // 绑定布局管理器
//        recycleView.setLayoutManager(layoutManager);
//        recycleView.addItemDecoration(new StaggeredDividerItemDecoration(mContext,6));
//        recycleView.setItemAnimator(new DefaultItemAnimator());
//        firstAdapter = new FirstAdapter(mList);
//        recycleView.setAdapter(firstAdapter);

    }

    /**
     *
     */
    private void getImage() {
//        RetrofitUtil.getInstance().getTestService()
//                .getFuli(10, 1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<GanhuoNews>>((Rx2Activity) mContext, false) {
//
//                    @Override
//                    public void onSuccess(List<GanhuoNews> ganhuoNews) {
//                        firstAdapter.setNewData(ganhuoNews);
//                    }
//                });
    }
}
