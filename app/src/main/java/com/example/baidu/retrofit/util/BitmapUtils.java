package com.example.baidu.retrofit.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author
 * @date 2020/3/4.
 * GitHub：
 * email：
 * description：
 */
public class BitmapUtils {

    public static Bitmap photoTuya;

    private static BitmapUtils btimapUtils;
    private Context context;

    private BitmapUtils(Context context) {
        this.context = context;
    }

    public static BitmapUtils getBtimapUtil(Context context) {
        if (btimapUtils == null) {
            synchronized (BitmapUtil.class) {
                btimapUtils = new BitmapUtils(context);
            }
        }
        return btimapUtils;
    }

    /**
     * 文件转Bitmap
     */
    public Bitmap fileToBitmap(String filePath) {
        File file = new File(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         *压缩长宽各为一半避免图片过大装载不了
         */
        options.inPurgeable = true;
        options.inSampleSize = 2;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Bitmap转文件
     */
    public File bitmapToFile(Bitmap bitmap, String saveFilePath) {
        File file = new File(saveFilePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数组转Bitmap
     */
    public Bitmap btyesToBtimap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Btimap转数组
     */
    public byte[] btimapToBtyes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Bitmap转Drawable
     */
    public static Drawable btimapToDrawable(Bitmap bitmap, Context context) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * Drawable转Bitmap
     */
//    public Bitmap drawableToBitmap(Drawable drawable) {
//        int w = drawable.getIntrinsicWidth();
//        int h = drawable.getIntrinsicHeight();
//        // 取 drawable 的颜色格式
//        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
//        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
//        //建立对应 bitmap 的画布
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, w, h);
//        // 把 drawable 内容画到画布中
//        drawable.draw(canvas);
//        return bitmap;
//    }

    /**
     * 带圆角的绘制转Bitmap
     */
    public Bitmap creatRoundedBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 带倒影的绘制Bitmap
     */
    public Bitmap createReflectionBitmap(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        /**
         * 获取矩阵变换
         * */
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);
        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(),
                0, bitmapWithReflection.getHeight() + reflectionGap,
                0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 andkeeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    public static boolean saveImageToGallery(Context context, Bitmap bmp) {

        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);

            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Drawable BitmapToDrawable(Bitmap bitmap, Context context) {
        BitmapDrawable drawbale = new BitmapDrawable(context.getResources(),
                bitmap);
        return drawbale;
    }

    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public static Bitmap big(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);  //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return resizeBmp;
    }


}
