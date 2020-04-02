package com.example.baidu.retrofit.Adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Bean.StudyBean;
import com.example.baidu.retrofit.R;

import java.util.List;

public class ListActivityAdapter extends BaseQuickAdapter<StudyBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    private Context mContext;

    public ListActivityAdapter(Context application, List<StudyBean> data) {
        super(R.layout.item_main, data);
        this.mContext = application;
        setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StudyBean studyBean = (StudyBean) adapter.getData().get(position);
        switch (position) {

        }

    }

    @Override
    protected void convert(BaseViewHolder helper, StudyBean item) {
        helper.setText(R.id.tv_item, item.getTitle());
    }
}
