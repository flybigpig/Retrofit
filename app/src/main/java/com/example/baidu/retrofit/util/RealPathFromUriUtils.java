package com.example.baidu.retrofit.util;

/**
 * @author
 * @date 2020/1/9.
 * GitHub：
 * email：
 * description：
 */

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class RealPathFromUriUtils {

    private int MAX_SIZE = 1024;

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {

        int sdkVersion = Build.VERSION.SDK_INT;

        if (sdkVersion >= 19) { // api >= 19

            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19

            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {

        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {

        String filePath = null;

        if (DocumentsContract.isDocumentUri(context, uri)) {

            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);

            if (isMediaDocument(uri)) { // MediaProvider

                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";

                String[] selectionArgs = {id};

                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);

            } else if (isDownloadsDocument(uri)) { // DownloadsProvider

                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));

                filePath = getDataColumn(context, contentUri, null, null);

            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);

        } else if ("file".equals(uri.getScheme())) {

            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();

        }

        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};

        Cursor cursor = null;

        try {

            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);

            if (cursor != null && cursor.moveToFirst()) {

                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);

                path = cursor.getString(columnIndex);

            }

        } catch (Exception e) {

            if (cursor != null) {

                cursor.close();

            }

        }

        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {

        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private void setImageView(Uri uri, View mAutographView, Context context) {

        String path = RealPathFromUriUtils.getRealPathFromUri(context, uri);

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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mAutographView.setBackground(BitmapUtil.btimapToDrawable(selectdBitmap, context));
            }

        } catch (IOException ioe) {

        }
    }

}

