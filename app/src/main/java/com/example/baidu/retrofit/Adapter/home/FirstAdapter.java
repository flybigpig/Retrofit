package com.example.baidu.retrofit.Adapter.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Activity.wanAndroid.ArticalHistoryActivity;
import com.example.baidu.retrofit.Bean.home.GongZhongHao;
import com.example.baidu.retrofit.R;

import java.util.List;

/**
 * @author
 * @date 2020/1/3.
 * GitHub：
 * email：
 * description：
 */
public class FirstAdapter extends BaseQuickAdapter<GongZhongHao, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


    public FirstAdapter(@Nullable List<GongZhongHao> data) {
        super(R.layout.item_android, data);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GongZhongHao gongZhongHao) {
        baseViewHolder.setText(R.id.title, gongZhongHao.getName());
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GongZhongHao gongZhongHao = (GongZhongHao) adapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", gongZhongHao);
        Intent intent = new Intent(mContext, ArticalHistoryActivity.class);
        intent.putExtra("Bundle", bundle);
        mContext.startActivity(intent);
    }
}
