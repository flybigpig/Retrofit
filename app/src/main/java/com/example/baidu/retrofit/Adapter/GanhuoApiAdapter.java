package com.example.baidu.retrofit.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;

import java.util.List;

public class GanhuoApiAdapter extends BaseQuickAdapter<GanhuoNews, BaseViewHolder> {

    public GanhuoApiAdapter(@Nullable List<GanhuoNews> data) {
        super(R.layout.item_ganhuo, data);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    protected void convert(BaseViewHolder helper, GanhuoNews item) {
        helper.setText(R.id.tv_item, item.getUrl());
    }


}
