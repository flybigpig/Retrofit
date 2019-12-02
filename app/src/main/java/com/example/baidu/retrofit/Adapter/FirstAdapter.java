package com.example.baidu.retrofit.Adapter;

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
import com.tool.cn.utils.GlideImageManager;

import java.util.List;

public class FirstAdapter extends BaseQuickAdapter<GanhuoNews, BaseViewHolder> {


    public FirstAdapter(@Nullable List data) {
        super(R.layout.item_first, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GanhuoNews item) {
        Glide.with(mContext).load(item.getUrl()).into((ImageView) helper.getView(R.id.iv_image));
    }
}
