package com.example.baidu.retrofit.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class DiyCommonUtil {


    private int MAX_SIZE = 769;

    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }


    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath() + "/quickwater/";


    public static void saveBitmap2file(Bitmap bmp, Context context, Activity activity) {


        if (bmp == null) {

            return;
        }
        String savePath;


        String fileName = generateFileName() + ".JPEG";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {


            savePath = SD_PATH;


        } else {
            Toast.makeText(context, "保存失败！", Toast.LENGTH_SHORT).show();


            return;
        }


        File filePic = new File(savePath + fileName);
        try {
            if (!filePic.exists()) {


                filePic.getParentFile().mkdirs();


                filePic.createNewFile();


            }
            FileOutputStream fos = new FileOutputStream(filePic);


            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();


            fos.close();
            activity.finish();
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


        }
        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    filePic.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + savePath + fileName)));
    }

    public static Bitmap returnRotatePhoto(String originpath) {


        // 取得图片旋转角度
        int angle = readPictureDegree(originpath);


        // 把原图压缩后得到Bitmap对象
        Bitmap bmp = getCompressPhoto(originpath);


        // 修复图片被旋转的角度
        Bitmap bitmap = rotaingImageView(angle, bmp);


        // 保存修复后的图片并返回保存后的图片路径
        return bitmap;
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Log.i("111", "readPictureDegree: orientation-------->" + orientation);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:


                    degree = 90;


                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:


                    degree = 180;


                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:


                    degree = 270;


                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();


        }


        return degree;
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {

        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();


        matrix.postRotate(angle);
        try {


            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            if (bitmap != null) {
                returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;


        }
        if (bitmap != returnBm) {


            bitmap.recycle();
        }


        return returnBm;
    }

    public static Bitmap getCompressPhoto(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();


        options.inJustDecodeBounds = false;
        options.inSampleSize = 1; // 图片的大小设置为原来的十分之一


        Bitmap bmp = BitmapFactory.decodeFile(path, options);


        options = null;
        return bmp;
    }

    public void setImageView(Intent data, ImageView imageview, Context context) {
        Uri uri = data.getData();

        // 旋转问题
        String path = RealPathFromUriUtils.getRealPathFromUri(context, data.getData());

        if (path == null) {

            path = uri.getPath();

        }

        int angle = DiyCommonUtil.readPictureDegree(path);


        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = false;

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            BitmapFactory.decodeStream(inputStream, null, options);

            inputStream.close();

            int height = options.outHeight;

            int width = options.outWidth;

            int sampleSize = 1;

            int max = Math.max(height, width);

            if (max > MAX_SIZE) {

                int nw = width / 2;

                int nh = height / 2;

                while ((nw / sampleSize) > MAX_SIZE || (nh / sampleSize) > MAX_SIZE) {

                    sampleSize *= 2;

                }
            }

            options.inSampleSize = sampleSize;

            options.inJustDecodeBounds = false;

            Bitmap selectdBitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);

            selectdBitmap = DiyCommonUtil.rotaingImageView(angle, selectdBitmap);

            imageview.setImageBitmap(selectdBitmap);

        } catch (IOException ioe) {

        }
    }
}
