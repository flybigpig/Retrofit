package com.example.baidu.retrofit.util.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * @author
 * @date 2019/12/24.
 * GitHub：
 * email：
 * description： 高效加载大图
 */
public class LoadBigBitMap {
    /**
     * Options中有个属性inJustDecodeBounds。我们可以充分利用它，来避免大图片的溢出问题。他是什么原理呢？
     * <p>
     * API这样说：如果该 值设为true那么将不返回实际的bitmap，也不给其分配内存空间这样就避免内存溢出了。但是允许我们查询图片的信息这其中就包括图片大小信息（
     * <p>
     * options.outHeight (图片原始高度)和option.outWidth(图片原始宽度)）。
     * <p>
     * Options中有个属性inSampleSize。我们可以充分利用它，实现缩放。
     * <p>
     * 如果被设置为一个值> 1,要求解码器解码出原始图像的一个子样本,返回一个较小的bitmap,以节省存储空间。
     * <p>
     * 例如,inSampleSize = = 2，则取出的缩略图的宽和高都是原始图片的1/2，图片大小就为原始大小的1/4。
     * <p>
     * 对于任何值< = 1的同样处置为1。
     * <p>
     * 那么相应的方法也就出来了，通过设置 inJustDecodeBounds为true，获取到outHeight(图片原始高度)和 outWidth(图片的原始宽度)，然后计算一个inSampleSize(缩放值)，
     * <p>
     * 然后就可以取图片了，这里要注意的是，inSampleSize 可能小于0，必须做判断。
     */


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);

        //mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 处理并发
     */
    public static boolean cancelPotentialWork(int data, ImageView imageView, Context context) {
        final ImageAsyncTask imageAsyncTask = AsyncDrawable.getBitmapWorkerTask(imageView);

        if (imageAsyncTask != null) {
            final int bitmapData = imageAsyncTask.data;
            if (bitmapData == 0 || bitmapData != data) {
                // Cancel previous task
                imageAsyncTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }


    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
