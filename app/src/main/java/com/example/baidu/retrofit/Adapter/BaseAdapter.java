package com.example.baidu.retrofit.Adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Bean.BaseBean;

import java.util.List;

/**
 *
 *
 */

public class BaseAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener,BaseQuickAdapter.OnItemClickListener {

    public BaseAdapter(int layoutResId, @Nullable List<BaseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
