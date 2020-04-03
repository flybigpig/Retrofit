package com.example.baidu.retrofit.Activity.wanAndroid;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Adapter.home.BannerAdapter;
import com.example.baidu.retrofit.Adapter.home.FragmentAdapter;
import com.example.baidu.retrofit.Bean.home.BannerBean;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.fragment.home.FirstFragment;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.RetrofitUtil;
import com.google.android.material.tabs.TabLayout;
import com.tool.cn.utils.GlideImageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePageActivity extends Rx2Activity {


    @BindView(R.id.tabLayout)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpager_1)
    ViewPager viewpager1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int index = (viewpager1.getCurrentItem() + 1) % views.size();
                    viewpager1.setCurrentItem(index);
                    break;
            }
        }
    };

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private FragmentAdapter mAdapter;
    private BannerAdapter mAdapter1;
    private List<View> views = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        super.init();

        fragments.add(new FirstFragment());

        titles.add("公众号");

        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles, mContext);

        viewPager.setAdapter(mAdapter);
        tab.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);

        timer();
    }

    private void timer() {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                //do something
                //每隔1s循环执行run方法
                if (views.size() > 0) {
                    mHandler.sendEmptyMessageDelayed(0, 10000);
                }
                mHandler.postDelayed(this, 4000);

            }
        };
        //主线程中调用：
        mHandler.postDelayed(r, 4000);//延时100毫秒
    }

    @Override
    protected void getHttp() {
        getBanner();
    }


    @Override
    protected void nationalizationData(Properties prop) {

    }

    private void getBanner() {
        RetrofitUtil.getTestService().getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BannerBean>>((Rx2Activity) mContext, false, "getBanner") {

                    @Override
                    public void onSuccess(List<BannerBean> bannerBeans) {
                        for (int i = 0; i < bannerBeans.size(); i++) {
                            //通过系统提供的实例获得一个LayoutInflater对象
                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            //第一个参数为xml文件中view的id，第二个参数为此view的父组件，可以为null，android会自动寻找它是否拥有父组件
                            View view = inflater.inflate(R.layout.home_banner, null);
                            views.add(view);
                            ImageView banner = views.get(i).findViewById(R.id.banner);
                            if (!TextUtils.isEmpty(bannerBeans.get(i).getImagepath())) {
                                Log.d("TAGPATH", bannerBeans.get(i).getImagepath());
                            } else {
                                Log.d("TAGPATH", bannerBeans.get(i).getTitle());
                            }

                            GlideImageManager.loadImage(mContext, bannerBeans.get(i).getImagepath(), banner);

                        }

                        mAdapter1 = new BannerAdapter(views, mContext);
                        viewpager1.setAdapter(mAdapter1);
                        mAdapter1.notifyDataSetChanged();
                    }
                });
    }


    private void hideAllFragment(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
//        if (!homeFragment.isHidden())
//            ft.hide(homeFragment);
//        ft.commitAllowingStateLoss();
    }


}
