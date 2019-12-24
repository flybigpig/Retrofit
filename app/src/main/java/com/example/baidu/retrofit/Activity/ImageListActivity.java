package com.example.baidu.retrofit.Activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.baidu.retrofit.Adapter.FirstAdapter;
import com.example.baidu.retrofit.Adapter.StaggeredHomeAdapter;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.StaggeredDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageListActivity extends Rx2Activity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private FirstAdapter firstAdapter;
    private List<GanhuoNews> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        super.init();
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        recycleView.addItemDecoration(new StaggeredDividerItemDecoration(mContext, 6));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        mList.add(new GanhuoNews());
        firstAdapter = new FirstAdapter(mList);
        recycleView.setAdapter(firstAdapter);
    }

    @Override
    protected void getHttp() {

    }

    @Override
    protected void nationalizationData(Properties prop) {

    }
}
