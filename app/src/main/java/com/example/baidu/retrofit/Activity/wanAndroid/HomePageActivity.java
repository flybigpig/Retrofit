package com.example.baidu.retrofit.Activity.wanAndroid;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.baidu.retrofit.Activity.Rx2Activity;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.fragment.home.AndroidFragment;
import com.example.baidu.retrofit.fragment.home.FuliFragment;
import com.example.baidu.retrofit.fragment.home.HomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends Rx2Activity {


    @BindView(R.id.tabLayout)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private HomeFragment homeFragment;
    private HomeFragment androidFragment;
    private FuliFragment mFuliFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        super.init();
        androidFragment = new AndroidFragment().getInstance("android");
        mFuliFragment = new FuliFragment().getInstance("fuli");

        fragments.add(androidFragment);
        fragments.add(mFuliFragment);

        titles.add("Android");
        titles.add("Fuli");
//        titles.add("Picture");
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });

        tab.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void getHttp() {


    }

    @Override
    protected void nationalizationData(Properties prop) {

    }


    private void hideAllFragment(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        if (!homeFragment.isHidden())
            ft.hide(homeFragment);
        ft.commitAllowingStateLoss();
    }

}
