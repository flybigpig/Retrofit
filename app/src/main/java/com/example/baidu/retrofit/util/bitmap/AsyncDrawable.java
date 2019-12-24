package com.example.baidu.retrofit.util.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.baidu.retrofit.util.DiskLruCache;

import java.lang.ref.WeakReference;

/**
 * @author
 * @date 2019/12/24.
 * GitHub：
 * email：
 * description：
 */
public class AsyncDrawable extends BitmapDrawable {

    private static WeakReference bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap,
                         ImageAsyncTask bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference =
                new WeakReference(bitmapWorkerTask);
    }

    public static ImageAsyncTask getBitmapWorkerTask() {
        return (ImageAsyncTask) bitmapWorkerTaskReference.get();
    }

    public static ImageAsyncTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}