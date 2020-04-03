package com.example.baidu.retrofit.Adapter.home;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.BitmapUtils;

import java.util.ArrayList;
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
    private List<Integer> heights;

    public FuliAdapter(Context context, @Nullable List<GanhuoNews> data) {
        super(R.layout.item_fuli);
        this.data = data;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams lp = holder.getView(R.id.fuli).getLayoutParams();

        lp.height = heights.get(position);

        holder.getView(R.id.fuli).setLayoutParams(lp);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GanhuoNews ganhuoNews) {
        ImageView img = baseViewHolder.getView(R.id.fuli);
        img.setImageBitmap(BitmapUtils.drawableToBitmap(mContext.getResources().getDrawable(R.mipmap.ic_launcher)));

//        GlideImageManager.loadImage(context, ganhuoNews.getUrl(), img);
    }


    private List randomHeight() {
        heights = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            heights.add((int) ((int) 100 + Math.random() * 300));
        }
        return heights;
    }
}
