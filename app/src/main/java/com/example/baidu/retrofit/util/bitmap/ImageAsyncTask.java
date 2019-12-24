package com.example.baidu.retrofit.util.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * @author
 * @date 2019/12/24.
 * GitHub：
 * email：
 * description： 异步加载图片
 */
public class ImageAsyncTask extends AsyncTask<Integer, Integer, Bitmap> {

    private WeakReference imageViewReference;
    public int data = 0;
    private Context context;

    public ImageAsyncTask(ImageView imageView, Context context) {
        this.context = context;
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference(imageView);
    }


    @Override
    protected Bitmap doInBackground(Integer... integers) {
        data = integers[0];
        return LoadBigBitMap.decodeSampledBitmapFromResource(context.getResources(), data, 100, 100);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = (ImageView) imageViewReference.get();
            final ImageAsyncTask bitmapWorkerTask =
                    AsyncDrawable.getBitmapWorkerTask(imageView);
            if (this == bitmapWorkerTask && imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
