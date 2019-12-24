package com.example.baidu.retrofit.Adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.bitmap.AsyncDrawable;
import com.example.baidu.retrofit.util.bitmap.ImageAsyncTask;
import com.example.baidu.retrofit.util.bitmap.LoadBigBitMap;
import com.tool.cn.utils.GlideImageManager;

import java.util.List;
import java.util.Random;

public class FirstAdapter extends BaseQuickAdapter<GanhuoNews, BaseViewHolder> {


    public FirstAdapter(@Nullable List data) {
        super(R.layout.item_first, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GanhuoNews item) {
        String[] urls = new String[]{
                "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg",
                "https://ww1.sinaimg.cn/large/0065oQSqly1g2hekfwnd7j30sg0x4djy.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqgy1fwyf0wr8hhj30ie0nhq6p.jpg",
                "https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg"};
        Random random = new Random();
        int max = 9;

        int min = 0;
        int s = random.nextInt(max);
        Glide.with(mContext).load(urls[s]).into((ImageView) helper.getView(R.id.iv_image));
    }
}
