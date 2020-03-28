package com.example.baidu.retrofit.util;

import android.graphics.Bitmap;

/**
 * @author
 * @date 2020/3/23.
 * GitHub：
 * email：
 * description：
 */
public class ImagePiece {

    private int index;
    private Bitmap bitmap;


    public ImagePiece() {

    }


    public ImagePiece(int index, Bitmap bitmap) {
        this.index = index;
        this.bitmap = bitmap;
    }
    

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}