package com.example.baidu.retrofit.util.home;

import android.view.View;

import androidx.viewpager.widget.ViewPager;


/**
 * @author
 * @date 2020/3/30.
 * GitHub：
 * email：
 * description：
 */
public class ViewPagerTransformer implements ViewPager.PageTransformer {


    @Override
    public void transformPage(View page, float position) {
        //最重要的就是这里了
        float v = Math.abs(position);
        float v1 = (float) (0.2 * (v * v));
        page.setScaleY(1 - v1);
        page.setScaleX(1 - v1);
    }
}
