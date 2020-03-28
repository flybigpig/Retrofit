package com.example.baidu.retrofit.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.R;
import com.example.baidu.retrofit.util.bitmap.AsyncDrawable;
import com.example.baidu.retrofit.util.bitmap.ImageAsyncTask;
import com.example.baidu.retrofit.util.bitmap.LoadBigBitMap;

import java.util.List;

public class GanhuoApiAdapter extends BaseQuickAdapter<GanhuoNews, BaseViewHolder> {

    private int resId = R.drawable.ic_placeholer;
    private Context mContext;

    public GanhuoApiAdapter(Context context, @Nullable List<GanhuoNews> data) {
        super(R.layout.item_ganhuo, data);
        this.mContext = context;
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    protected void convert(BaseViewHolder helper, GanhuoNews item) {
        helper.setText(R.id.tv_item, item.getUrl());
        ImageView image = helper.getView(R.id.image);
        // 第一种加载图片
//        image.setImageBitmap(LoadBigBitMap.decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.ic_placeholer, 50, 50));
        // 第二种异步加载
        if (LoadBigBitMap.cancelPotentialWork(resId, image, mContext)) {
            Bitmap mbitmap = LoadBigBitMap.drawableToBitmap(mContext.getResources().getDrawable(R.drawable.ic_placeholer));
            final ImageAsyncTask task = new ImageAsyncTask(image, mContext);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mContext.getResources(), mbitmap, task);
            image.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
//        ImageAsyncTask async = new ImageAsyncTask(image, mContext);
//        async.execute(R.mipmap.ic_launcher);
    }


}
