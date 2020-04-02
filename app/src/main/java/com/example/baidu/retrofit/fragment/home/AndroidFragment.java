package com.example.baidu.retrofit.fragment.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.baidu.retrofit.Adapter.home.HomeAdapter;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.dialog.WaitingDialog;
import com.example.baidu.retrofit.util.DividerGridItemDecoration;
import com.tool.cn.fragment.BaseFragment;

import butterknife.BindView;

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
    private int PAGE = 1;
    private int mPageSize = 20;
    private WaitingDialog mWaitingDialog;

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
        mWaitingDialog = new WaitingDialog(getActivity());

        mHomeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getAndroidArtical(mPageSize, PAGE);
            }
        }, recycleView);
    }

    @Override
    public void getHttpData() {
        mWaitingDialog.show();
        getAndroidArtical(mPageSize, PAGE);
    }

    public void getAndroidArtical(int pageSize, int page) {

        
    }
}
