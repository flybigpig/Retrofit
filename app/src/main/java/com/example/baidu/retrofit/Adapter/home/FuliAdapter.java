package com.example.baidu.retrofit.Adapter.home;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;
import com.tool.cn.utils.GlideImageManager;

import java.util.List;

/**
 * @author
 * @date 2020/4/2.
 * GitHub：
 * email：
 * description：
 */
public class FuliAdapter extends BaseQuickAdapter<GanhuoNews, BaseViewHolder> {

    private Context context;
    private List<GanhuoNews> data;

    public FuliAdapter(Context context, @Nullable List<GanhuoNews> data) {
        super(R.layout.item_fuli);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GanhuoNews ganhuoNews) {
        ImageView img = baseViewHolder.getView(R.id.fuli);
        GlideImageManager.loadImage(context, ganhuoNews.getUrl(), img);
    }
}
