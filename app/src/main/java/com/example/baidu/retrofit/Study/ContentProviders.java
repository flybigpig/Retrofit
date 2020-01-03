package com.example.baidu.retrofit.Study;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class ContentProviders extends ContentProvider {


    public static void main(String[] args) {
        String string = "abc";
        String s = string = "123";
        System.out.println(s + " " + string);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 数据库
     */
    static class sqlData extends SQLiteOpenHelper {
        /*

         dbHelper.getWritableDatabase(); // 会创建一个新的数据库

         */

        public sqlData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            更新数据库
        }


    }

    private void insert() {
//        SQLiteDatabase db = sqlData.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        // 开始组装第一条数据
//        values.put("name", "The Da Vinci Code");
//        values.put("author", "Dan Brown");
//        values.put("pages", 454);
//        values.put("price", 16.96);
//        db.insert("Book", null, values); // 插入第一条数据
//        db.delete("Book", "pages > ?", new String[] { "500" });
    }


//    ContentResolver


}
