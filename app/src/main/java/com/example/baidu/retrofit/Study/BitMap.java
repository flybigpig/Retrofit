package com.example.baidu.retrofit.Study;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.load.engine.Resource;

/**
 * 图片高效加载 与缓存策略
 *
 */
public class BitMap {



    public static Bitmap decodeSampledBitmapFromResoruce(Resources res, int resId,
                                                         int reqWidth, int reqHeight){
        // 获取 BitmapFactory.Options，这里面保存了很多有关 Bitmap 的设置
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置 true 轻量加载图片信息
        options.inJustDecodeBounds = true;
        // 由于上方设置false，这里轻量加载图片
        BitmapFactory.decodeResource(res,resId,options);
        // 计算采样率
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        // 设置 false 正常加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth,int reqHeight){
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;
        // 宽或高大于预期就将采样率 *=2 进行缩放
        if(width > reqWidth || height > reqHeight){
            final int halfHeight = height/2;
            final int halfWidth = width/2;
            while((halfHeight / inSampleSize) >= reqHeight &&
                    (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     *  缓存策略
     *
     */
}
