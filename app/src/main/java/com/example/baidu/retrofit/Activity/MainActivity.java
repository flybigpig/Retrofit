package com.example.baidu.retrofit.Activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baidu.retrofit.Adapter.ListActivityAdapter;
import com.example.baidu.retrofit.Api;
import com.example.baidu.retrofit.Bean.StudyBean;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.DividerGridItemDecoration;
import com.tool.cn.utils.GlideImageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Rx2Activity {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.header_image)
    ImageView headerImage;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.drawlayout)
    DrawerLayout drawlayout;

    private Api apis;
    private String TAG = "Mainactivity";
    private ListActivityAdapter activityAdapter;
    private List<StudyBean> listString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
        String url = "https://c-ssl.duitang.com/uploads/item/201511/20/20151120185405_8dJPm.thumb.700_0.jpeg";
        GlideImageManager.loadCircleImage(mContext, url, headerImage);
    }

    private void initData() {

        listString.add(new StudyBean("recycleView加载更多", "", "url"));

        listString.add(new StudyBean("图片流式布局", "", "url"));

        listString.add(new StudyBean("贝塞尔曲线", "", "url"));

        listString.add(new StudyBean("ImageSwitcer", "", "url"));

        listString.add(new StudyBean("GanHuo", "", "url"));

        listString.add(new StudyBean("Share", "", "url"));


        activityAdapter.setNewData(listString);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    protected void getHttp() {

    }


}
