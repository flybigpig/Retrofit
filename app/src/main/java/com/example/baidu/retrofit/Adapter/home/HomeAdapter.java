package com.example.baidu.retrofit.Adapter.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Activity.wanAndroid.WebViewActivity;
import com.example.baidu.retrofit.Bean.home.AndroidBean;
import com.example.baidu.retrofit.R;

import java.util.List;

/**
 * @author
 * @date 2020/1/3.
 * GitHub：
 * email：
 * description：
 */
public class HomeAdapter extends BaseQuickAdapter<AndroidBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


    public HomeAdapter(@Nullable List<AndroidBean> data) {
        super(R.layout.item_android, data);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, AndroidBean item) {
        helper.setText(R.id.title, item.getDesc());
        ImageView imageView = helper.getView(R.id.image);
//        if (item.getImages() != null && item.getImages().size() > 0) {
//            GlideImageManager.loadImage(mContext, item.getImages().get(0), imageView);
//        } else {
//            helper.setGone(R.id.image, false);
//            GlideImageManager.loadImage(mContext, R.drawable.ic_placeholer, imageView);
//        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AndroidBean androidBean = (AndroidBean) adapter.getData().get(position);
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("url", androidBean.getUrl());
        mContext.startActivity(intent);
    }
}
