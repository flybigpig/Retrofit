package com.example.baidu.retrofit.Adapter.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @author
 * @date 2020/3/30.
 * GitHub：
 * email：
 * description：
 */
public class BannerAdapter extends PagerAdapter {

    private List<View> mList;
    private Context mContext;

    public BannerAdapter(List<View> list, Context context) {
        super();
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    //getCount():返回要滑动的VIew的个数
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    //instantiateItem()：做了两件事，第一：将当前视图添加到container中，
    //第二：返回当前View
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(mList.get(position));
        return mList.get(position);
    }

    //	destroyItem（）：从当前container中删除指定位置（position）的View;
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        // TODO Auto-generated method stub
        //	super.destroyItem(container, position, object);
        container.removeView(mList.get(position));

    }
}