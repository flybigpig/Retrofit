package com.example.baidu.retrofit.fragment.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.Adapter.home.BannerAdapter;
import com.example.baidu.retrofit.Bean.home.BannerBean;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BaseObserver;
import com.example.baidu.retrofit.util.RetrofitUtil;
import com.tool.cn.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author
 * @date 2020/4/3.
 * GitHub：
 * email：
 * description：
 */
public class MainFragment extends BaseFragment {


    private List<View> mViews = new ArrayList<>();
    private BannerAdapter adapter;
    private List<View> Views = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home__fragment);
    }

    @Override
    protected void initView() {

//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//
//        adapter = new BannerAdapter(mViews, mContext);
//
//        ViewPager.setPageTransformer(true, new ViewPagerTransformer());
//        ViewPager.setAdapter(adapter);
//        ViewPager.setCurrentItem(0);
//        ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    @Override
    public void getHttpData() {
        getBanner();
    }

    private void getBanner() {

    }
}
