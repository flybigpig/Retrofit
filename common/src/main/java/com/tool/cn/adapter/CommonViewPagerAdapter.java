package com.tool.cn.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  2017/3/15  11:21.
 *
 *
 * @version 1.0.0
 * @class CommonViewPagerAdapter
 * @describe 公共的ViewPager适配器
 */
public class CommonViewPagerAdapter extends FragmentPagerAdapter {

    protected String TAG = getClass().getSimpleName();
    private String[] title;
    private List<Fragment> fragmentList;

    public CommonViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CommonViewPagerAdapter(FragmentManager fm,  List<Fragment> fragmentList,String[] titles) {
        super(fm);
        title = titles;
        this.fragmentList =fragmentList;
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    public void addFragments(Fragment... fragment) {
        Collections.addAll(fragmentList, fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title == null ? "" : title[position];
    }

}
