package com.tool.cn.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.tool.cn.BaseConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *  2017/6/19  16:27.
 *
 *
 * @version 1.0.0
 * @class PhotoUtils
 * @describe 照片处理工具类
 * 注：压缩可参考 https://github.com/Curzibn/Luban
 */
public class PhotoUtils {

//-------------------------------------------以下为图片压缩相关内容-------------------------------------------//

    /**
     * 压缩图片
     *
     * @param originalPhotoPath 原始图片文件路径
     * @param compressFile      压缩后输出的文件
     * @return 压缩后的文件
     * @throws IOException IOException
     */
    public static File compress(String originalPhotoPath, File compressFile) throws IOException {
        return compress(originalPhotoPath, compressFile, 50);
    }

    /**
     * 压缩图片
     *
     * @param originalPhotoPath 原始图片文件路径
     * @param compressFile      压缩后输出的文件
     * @param compressionValue  压缩比
     * @return 压缩后的文件
     * @throws IOException IOException
     */
    public static File compress(String originalPhotoPath, File compressFile, int compressionValue) throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(originalPhotoPath, options);
        options.inSampleSize = computeSize(options.outWidth, options.outHeight);

        options.inJustDecodeBounds = false;
        Bitmap compressBitmap = BitmapFactory.decodeFile(originalPhotoPath, options);
        if (null != compressBitmap) {
            compressBitmap = rotatingPhoto(originalPhotoPath, compressBitmap);
        }
        return saveBitmapFile(compressBitmap, compressFile, compressionValue);
    }

    /**
     * Bitmap对象保存为图片文件
     *
     * @param compressBitmap  原文件bitmap
     * @param compressFile   压缩后输出的文件
     * @param compressionValue  压缩比
     * @return
     * @throws IOException
     */
    public static File saveBitmapFile(Bitmap compressBitmap, File compressFile, int compressionValue) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (null != compressBitmap) {
            compressBitmap.compress(Bitmap.CompressFormat.JPEG, compressionValue, baos);
            compressBitmap.recycle();
        }
        FileOutputStream fos = null;
        fos = new FileOutputStream(compressFile);
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();
        baos.close();
        return compressFile;
    }

    /**
     * 压缩图片
     *
     * @param originalFile 原始图片文件
     * @param compressFile 压缩后输出的文件
     * @return 压缩后的文件
     * @throws IOException IOException
     */
    public static File compress(File originalFile, File compressFile) throws IOException {
        return compress(originalFile.getAbsolutePath(), compressFile);
    }

    /**
     * 旋转图片
     *
     * @param filePath 图片路径
     * @param bitmap   图片生成的bitmap
     * @return 旋转后的bitmap
     * @throws IOException IOException
     */
    private static Bitmap rotatingPhoto(String filePath, Bitmap bitmap) throws IOException {
        ExifInterface exifInterface = null;
        if (isJpeg(filePath)) {
            exifInterface = new ExifInterface(filePath);
        }
        if (null == exifInterface) return bitmap;
        Matrix matrix = new Matrix();
        int angle = 0;
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = 270;
                break;
        }
        matrix.postRotate(angle);
        return null == bitmap ? null : Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static boolean isJpeg(String photoPath) {
        return photoPath.endsWith(".jpeg") || photoPath.endsWith("jpg");
    }

    /**
     * 计算压缩比
     *
     * @param originalWidth  图片宽度
     * @param originalHeight 图片高度
     * @return 压缩比
     */
    public static int computeSize(int originalWidth, int originalHeight) {
        int mSampleSize;

        originalWidth = originalWidth % 2 == 1 ? originalWidth + 1 : originalWidth;
        originalHeight = originalHeight % 2 == 1 ? originalHeight + 1 : originalHeight;

        originalWidth = originalWidth > originalHeight ? originalHeight : originalWidth;
        originalHeight = originalWidth > originalHeight ? originalWidth : originalHeight;

        double scale = ((double) originalWidth / originalHeight);

        if (scale <= 1 && scale > 0.5625) {
            if (originalHeight < 1664) {
                mSampleSize = 1;
            } else if (originalHeight >= 1664 && originalHeight < 4990) {
                mSampleSize = 2;
            } else if (originalHeight >= 4990 && originalHeight < 10240) {
                mSampleSize = 4;
            } else {
                mSampleSize = originalHeight / 1280 == 0 ? 1 : originalHeight / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            mSampleSize = originalHeight / 1280 == 0 ? 1 : originalHeight / 1280;
        } else {
            mSampleSize = (int) Math.ceil(originalHeight / (1280.0 / scale));
        }
        return mSampleSize;
    }

//-------------------------------------------以下为裁剪相关内容-------------------------------------------//

    //裁剪参数
    private static final boolean DEFAULT_RETURN_DATA = false;//是否返回数据
    private static final boolean DEFAULT_SCALE = true;//是否缩放
    private static final boolean DEFAULT_FACE_DETECTION = false;//是否面部检测
    private static final boolean DEFAULT_CIRCLE_CROP = false;//圆形裁剪
    private static final int DEFAULT_OUTPUT_X = 900;//默认裁剪输出宽度
    private static final int DEFAULT_OUTPUT_Y = 600;//默认裁剪输出高度
    private static final int DEFAULT_ASPECT_X = 3;//默认裁剪宽度比例
    private static final int DEFAULT_ASPECT_Y = 2;//默认裁剪高度比例

    /**
     * 裁剪照片
     *
     * @param uri    原始路径
     * @param outUri 裁剪输出路径
     * @return 裁剪意图
     */
    /**
     * 裁剪照片
     *
     * @param imgPath    原始路径
     * @param targetPath 裁剪输出路径
     * @return 裁剪意图
     */
    public static Intent crop(Activity mContext, String imgPath, String targetPath) {
        return crop(mContext, imgPath, targetPath, DEFAULT_ASPECT_X, DEFAULT_ASPECT_Y, DEFAULT_OUTPUT_X, DEFAULT_OUTPUT_Y, DEFAULT_SCALE, DEFAULT_RETURN_DATA, DEFAULT_FACE_DETECTION, DEFAULT_CIRCLE_CROP);
    }

    public static Intent crop(Activity mContext, String imgPath, String targetPath,int aspectX, int aspectY, int outputX, int outputY) {
        return crop(mContext, imgPath, targetPath, aspectX, aspectY, outputX, outputY, DEFAULT_SCALE, DEFAULT_RETURN_DATA, DEFAULT_FACE_DETECTION, DEFAULT_CIRCLE_CROP);
    }

    private static Intent crop(Activity mContext, String imgPath, String targetPath,
                               int aspectX, int aspectY, int outputX, int outputY,
                               boolean scale, boolean returnData, boolean faceDetection, boolean circleCrop) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        File file = new File(imgPath);
        file.deleteOnExit();
        Uri imgUri = Uri.fromFile(file);
        Uri targertUri = Uri.fromFile(new File(targetPath));
        intent.setDataAndType(imgUri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", scale);
        intent.putExtra("return-data", returnData);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, targertUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", !faceDetection);
        intent.putExtra("circleCrop", circleCrop);
        mContext.startActivityForResult(intent, BaseConstants.PCROP_REQUEST_CODE);
        return intent;
    }


//-------------------------------------------以下为根据Uri获取路径相关内容-------------------------------------------//

    /**
     * 通用的根据Uri获取路径的方法，兼容shceme是content和file的情况
     *
     * @param context 上下文对象
     * @param uri     Uri
     * @return 路径
     */
    public static String getPathFromUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {//DocumentProvider
            if (isExternalStorageDocument(uri)) {//ExternalStorageProvider
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {//DownloadsProvider
                String docId = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {//MediaProvider
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                String[] selectedArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectedArgs);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {//MediaStore (and general)
            if (isGooglePhotosDocument(uri)) {//Return the remote address
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {//File
            return uri.getPath();
        }
        return "";
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = new String[]{column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (null != cursor && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return "";
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosDocument(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
