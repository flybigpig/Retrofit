package com.example.baidu.retrofit.fragment.home;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Adapter.home.FuliAdapter;
import com.example.baidu.retrofit.Bean.GanhuoNews;
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
 * @date 2020/4/2.
 * GitHub：
 * email：
 * description：
 */
public class FuliFragment extends BaseFragment {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private FuliAdapter mFuliAdapter;
    private int PAGE = 1;
    private int mPageSize = 20;

    public FuliFragment getInstance(String type) {
        FuliFragment f = new FuliFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuli_fragment);
    }

    @Override
    protected void initView() {
        if (mContext == null) {
            mContext = getActivity();
        }
        mFuliAdapter = new FuliAdapter(getActivity().getApplicationContext(), null);

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置分隔线
        recycleView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        //设置增加或删除条目的动画
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //设置Adapter
        recycleView.setAdapter(mFuliAdapter);
        if (mContext == null) {
            mContext = getActivity();

        }
    }

    @Override
    public void getHttpData() {
        getFuliImage(20, 1);
    }

    private void getFuliImage(int pageSize, int page) {
//        RetrofitUtil.getTestService().getFuli(20, 1).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<GanhuoNews>>((Rx2Activity) mContext, false, "getFuli") {
//
//                    @Override
//                    public void onPageLoading(int pageSize, int totalCount) {
//                        super.onPageLoading(pageSize, totalCount);
//                    }
//
//                    @Override
//                    public void onSuccess(List<GanhuoNews> ganhuoNews) {
//                        Log.d("Retrofit", ganhuoNews.size() + "");
//                        if (ganhuoNews.size() > 0) {
//                            if (PAGE == 1) {
//                                mFuliAdapter.setNewData(ganhuoNews);
//                            } else {
//                                mFuliAdapter.addData(ganhuoNews);
//                            }
//                            if (0 >= 1) {
//                                mFuliAdapter.loadMoreEnd();
//                            } else {
//                                mFuliAdapter.loadMoreComplete();
//                                PAGE++;
//                            }
//                        } else {
//                            mFuliAdapter.loadMoreEnd();
//                        }
//                    }
//
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//
//                        mFuliAdapter.setNewData(null);
//                        mFuliAdapter.notifyDataSetChanged();
//                    }
//                });
    }
}
