package com.example.baidu.retrofit.Adapter.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/4/3.
 * GitHub：
 * email：
 * description：
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private Context mContext;
    private List<String> titles = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, @Nullable List<String> titles, Context luteActivity) {
        super(fm);
        this.mFragmentList = fragments;
        this.mContext = luteActivity;
        this.titles = titles;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position % mFragmentList.size());
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.size() == 0) {
            return "";
        }
        return titles.get(position);
    }
}
