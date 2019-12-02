package com.example.commonlibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.security.MessageDigest;


/**
 *  2016/11/15 11:46.
 *
 *
 * @version 1.0.0
 * @class GlideImageManager
 * @describe Glide图片加载管理类
 * 可能遇到的问题:
 * 1、placeholder()导致的图片变形问题。
 * 问题描述：使用.placeholder()方法在某些情况下会导致图片显示的时候出现图片变形的情况。这是因为Glide默认开启的crossFade动画导致的TransitionDrawable绘制异常，详细描述和讨论可以看一下这个#363 issue。根本原因就是你的placeholder图片和你要加载显示的图片宽高比不一样，而Android的TransitionDrawable无法很好地处理不同宽高比的过渡问题，这的确是个Bug，是Android的也是Glide的。
 * 解决办法：使用.dontAnimate()方法禁用过渡动画，或者使用animate()方法自己写动画，再或者自己修复TransitionDrawable的问题。
 * 1、ImageView的资源回收问题。
 * 问题描述：默认情况下，Glide会根据with()使用的Activity或Fragment的生命周期自动调整资源请求以及资源回收。但是如果有很占内存的Fragment或Activity不销毁而仅仅是隐藏视图，那么这些图片资源就没办法及时回收，即使是GC的时候。
 * 解决办法：可以考虑使用WeakReference，如：
 * final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
 * ImageView target = imageViewWeakReference.get();
 * if (target != null) {
 * Glide.with(context).load(uri).into(target);
 * }
 * 3、ImageView的setTag问题。
 * 问题描述：如果使用Glide的into(imageView)为ImageView设置图片的同时使用ImageView的setTag(final Object tag)方法，将会导致java.lang.IllegalArgumentException: You must not call setTag() on a view Glide is targeting异常。因为Glide的ViewTarget中通过view.setTag(tag)和view.getTag()标记请求的，由于Android 4.0之前Tag存储在静态map里，如果Glide使用setTag(int key, final Object tag)方法标记请求会导致内存泄露，所以Glide默认使用view.setTag(tag)标记请求，你就不能重复调用了。
 * 解决办法：如果你需要为ImageView设置Tag，必须使用setTag(int key, final Object tag)及getTag(int key)方法，其中key必须是合法的资源ID以确保key的唯一性，典型做法就是在资源文件中声明type="id"的item资源。
 * 4、有些情况下图片不显示的问题。
 * 问题描述：有些情况下在预览大图的时候不显示图片。
 * 解决办法：可以加上override(800, 800)试试。
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class GlideImageManager {


    public static int default_img;
    public static int default_err_img;
    public static int default_circle_img;
    public static int default_circle_err_img;
    public static final int NOT_THUMBNAIL = -1;//不使用缩略图标记
    // 4.0 往下支持options
//    RequestOptions options = new RequestOptions().placeholder(default_img)//加载成功之前占位图
//            .error(default_img)//加载错误之后的错误图
//            .override(400, 400) //指定图片的尺寸
//            // 指定图片的缩放类型为fitCenter（等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
//            .fitCenter()//指定图片的缩放类型为centerCrop（等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
//            .centerCrop().circleCrop()   //指定图片的缩放类型为centerCrop（圆形）
//            .skipMemoryCache(true)//跳过内存缓存
//            .diskCacheStrategy(DiskCacheStrategy.ALL)  //缓存所有版本的图像
//            .diskCacheStrategy(DiskCacheStrategy.NONE) //跳过磁盘缓存
//            .diskCacheStrategy(DiskCacheStrategy.DATA) //只缓存原来分辨率的图片
//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //只缓存最终的图片
//            ;

    /**
     * 初始化，主要是设置默认图片
     *
     * @param defaultImg       默认图片
     * @param defaultCircleImg 默认圆形图片
     */
    public static void init(@DrawableRes int defaultImg, @DrawableRes int defaultCircleImg) {
        init(defaultImg, defaultImg, defaultCircleImg, defaultCircleImg);
    }

    /**
     * 初始化，主要是设置默认图片
     *
     * @param defaultImg          默认图片
     * @param defaultErrImg       默认错误图片
     * @param defaultCircleImg    默认圆形图片
     * @param defaultCircleErrImg 默认圆形错误图片
     */
    public static void init(@DrawableRes int defaultImg, @DrawableRes int defaultErrImg,
                            @DrawableRes int defaultCircleImg, @DrawableRes int defaultCircleErrImg) {
        default_img = defaultImg;
        default_err_img = defaultErrImg;
        default_circle_img = defaultCircleImg;
        default_circle_err_img = defaultCircleErrImg;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadImage(Context context, String url, ImageView iv) {

        RequestOptions options = new RequestOptions()
                .placeholder(default_img)//加载成功之前占位图
                .error(default_img)//加载错误之后的错误图
                .centerCrop()  //指定图片的缩放类型为centerCrop（圆形）
                .dontAnimate();


        Glide.with(context).load(url)
                .apply(options)
                .into(iv);


    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void setloadImageListener(Context context, String url, ImageView iv, int radius, RequestListener<Drawable> requestListener) {

        RequestOptions options = new RequestOptions()
                .placeholder(default_img)//加载成功之前占位图
                .error(default_img)//加载错误之后的错误图
                .centerCrop()  //指定图片的缩放类型为centerCrop（圆形）
                .transform(CenterCrop.class, new Transformation<CenterCrop>() {
                    @NonNull
                    @Override
                    public Resource<CenterCrop> transform(@NonNull Context context, @NonNull Resource<CenterCrop> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                })
                .dontAnimate();

        Glide.with(context).load(url)
                .apply(options)
                .listener(requestListener)
                .into(iv);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void setloadCircleImageListener(Context context, String url, ImageView iv, RequestListener<Drawable> requestListener) {

        RequestOptions options = new RequestOptions()
                .placeholder(default_circle_img)//加载成功之前占位图
                .error(default_circle_err_img)//加载错误之后的错误图
                .circleCrop() //指定图片的缩放类型为centerCrop（圆形）
                .transform(CircleCrop.class, new Transformation<CircleCrop>() {
                    @NonNull
                    @Override
                    public Resource<CircleCrop> transform(@NonNull Context context, @NonNull Resource<CircleCrop> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                })
                .dontAnimate();

        Glide.with(context).load(url)
                .apply(options)
                .listener(requestListener)
                .into(iv);
    }

    /**
     * 加载设置优先级的图片
     *
     * @param context
     * @param url
     * @param iv
     * @param priority
     */
    public static void loadImage(Context context, String url, ImageView iv, Priority priority) {


        RequestOptions options = new RequestOptions()
                .placeholder(default_img)//加载成功之前占位图
                .error(default_err_img)//加载错误之后的错误图
                .priority(priority)
                .dontAnimate();


        Glide.with(context).load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * 下载图片
     *
     * @param context
     * @param url
     */
    public static void loadImage(Context context, String url, SimpleTarget simpleTarget) {

        RequestOptions options = new RequestOptions()
                .placeholder(default_img)//加载成功之前占位图
                .error(default_err_img)//加载错误之后的错误图
                .dontAnimate();

        Glide.with(context).asBitmap().load(url)   // 去掉了toBytes()
                .apply(options)
                .into(simpleTarget);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param defaultImg
     * @param errImg
     * @param iv
     */
    public static void loadImage(Context context, String url, @DrawableRes int defaultImg, @DrawableRes int errImg, ImageView iv) {
        loadImage(context, url, errImg, defaultImg, iv, Priority.NORMAL, DiskCacheStrategy.AUTOMATIC);
    }

    /**
     * 加载设置优先级的图片
     *
     * @param context
     * @param url
     * @param errImg
     * @param defaultImg
     * @param iv
     * @param priority          优先级：Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL(默认)，Priority.LOW
     * @param diskCacheStrategy 缓存策略：DiskCacheStrategy.SOURCE：缓存原始数据，DiskCacheStrategy.RESULT：缓存变换(如缩放、裁剪等)后的资源数据(默认)，DiskCacheStrategy.NONE：什么都不缓存，DiskCacheStrategy.ALL：缓存SOURC和RESULT
     */
    public static void loadImage(Context context, String url, @DrawableRes int errImg, @DrawableRes int defaultImg, ImageView iv, Priority priority, DiskCacheStrategy diskCacheStrategy) {


        RequestOptions options = new RequestOptions()
                .priority(priority)
                .placeholder(defaultImg)//加载成功之前占位图
                .error(errImg)//加载错误之后的错误图
                .diskCacheStrategy(diskCacheStrategy)
                .dontAnimate();

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载gif图片(缓存原始数据)
     *
     * @param context
     * @param url
     * @param iv      diskCacheStrategy设置缓存策略:
     */
    public static void loadGifImage(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(default_img)//加载成功之前占位图
                .error(default_err_img)//加载错误之后的错误图
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontAnimate();
        Glide.with(context)
                .asGif()
                .load(url)
                .into(iv);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadCircleImage(Context context, String url, ImageView iv) {

        RequestOptions options = new RequestOptions()
                .placeholder(default_circle_img)//加载成功之前占位图
                .error(default_circle_err_img)//加载错误之后的错误图
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontAnimate()
                .circleCrop()
                .transform(CircleCrop.class, new Transformation<CircleCrop>() {
                    @NonNull
                    @Override
                    public Resource<CircleCrop> transform(@NonNull Context context, @NonNull Resource<CircleCrop> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载圆形图片
     *
     * @param context    上下文对象
     * @param url        图片链接
     * @param defaultImg 默认图片
     * @param iv         ImageView
     */
    public static void loadCircleImage(Context context, String url, @DrawableRes int defaultImg, ImageView iv) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultImg)//加载成功之前占位图
                .error(defaultImg)//加载错误之后的错误图
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontAnimate()
                .signature(new Key() {
                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                })//为了解决储备粮项目中用户头像地址不变导致修改图片后不刷新的问题
                .circleCrop()
                .transform(CircleCrop.class, new Transformation<CircleCrop>() {
                    @NonNull
                    @Override
                    public Resource<CircleCrop> transform(@NonNull Context context, @NonNull Resource<CircleCrop> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载圆形图片
     *
     * @param context    上下文对象
     * @param url        图片链接
     * @param defaultImg 默认图片
     * @param iv         ImageView
     */
    public static void loadCircleImageWithSignature(Context context, String url, @DrawableRes int defaultImg, ImageView iv) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultImg)//加载成功之前占位图
                .error(defaultImg)//加载错误之后的错误图
                .dontAnimate()
                .circleCrop()
                .signature(new Key() {
                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                })
                .transform(CircleCrop.class, new Transformation<CircleCrop>() {
                    @NonNull
                    @Override
                    public Resource<CircleCrop> transform(@NonNull Context context, @NonNull Resource<CircleCrop> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载圆形图片
     *
     * @param context    上下文对象
     * @param url        图片链接
     * @param defaultImg 默认图片
     * @param size       图片大小
     * @param iv         ImageView
     */
    public static void loadCircleImage(Context context, String url, @DrawableRes int defaultImg, int size, ImageView iv) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultImg)//加载成功之前占位图
                .error(defaultImg)//加载错误之后的错误图
                .dontAnimate()
                .override(size)
                .circleCrop()
                .signature(new Key() {
                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                })
                .transform(CircleCrop.class, new Transformation<CircleCrop>() {
                    @NonNull
                    @Override
                    public Resource<CircleCrop> transform(@NonNull Context context, @NonNull Resource<CircleCrop> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载圆形图片
     *
     * @param context    上下文对象
     * @param url        图片链接
     * @param defaultImg 默认图片
     * @param size       图片大小
     * @param iv         ImageView
     */
    public static void loadCircleImageWithSignature(Context context, String url, @DrawableRes int defaultImg, int size, ImageView iv) {

        RequestOptions options = new RequestOptions()
                .placeholder(defaultImg)//加载成功之前占位图
                .error(defaultImg)//加载错误之后的错误图
                .dontAnimate()
                .override(size)
                .circleCrop()
                .signature(new Key() {
                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                })
                .transform(CircleCrop.class, new Transformation<CircleCrop>() {
                    @NonNull
                    @Override
                    public Resource<CircleCrop> transform(@NonNull Context context, @NonNull Resource<CircleCrop> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载圆形资源图片
     *
     * @param context    上下文对象
     * @param resourceId 图片资源文件
     * @param iv         imageView
     */
    public static void loadCircleImage(Context context, @DrawableRes int resourceId, ImageView iv) {

        RequestOptions options = new RequestOptions()
                .placeholder(resourceId)//加载成功之前占位图
                .error(resourceId)//加载错误之后的错误图
                .circleCrop()
                .transform(CircleCrop.class, new Transformation<CircleCrop>() {
                    @NonNull
                    @Override
                    public Resource<CircleCrop> transform(@NonNull Context context, @NonNull Resource<CircleCrop> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        Glide.with(context)
                .load(resourceId)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载圆角图片
     *
     * @param context 上下文对象
     * @param url     图片链接
     * @param iv      imageView
     * @param radius  半径
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv, int radius) {

        RequestOptions options = new RequestOptions()
                .placeholder(default_circle_img)//加载成功之前占位图
                .error(default_circle_err_img)//加载错误之后的错误图
                .circleCrop()
                .transform(RoundedCorners.class, new Transformation<RoundedCorners>() {
                    @NonNull
                    @Override
                    public Resource<RoundedCorners> transform(@NonNull Context context, @NonNull Resource<RoundedCorners> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        Glide.with(context).load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载圆角资源图片
     *
     * @param context
     * @param resourceId
     * @param iv
     * @param radius
     */
    @Deprecated
    public static void loadRoundCornerImageDp(Context context, @DrawableRes int resourceId, ImageView iv, int radius) {
//        Glide.with(context).load(resourceId).dontAnimate().placeholder(default_img).error(default_err_img).transform(new GlideRoundTramsform(context, radius)).into(iv);
//        Glide.with(context).load(resourceId).placeholder(default_img).error(default_err_img).bitmapTransform(new RoundedCornersTransformation(context,radius,0, RoundedCornersTransformation.CornerType.ALL)).into(iv);
        RequestOptions options = new RequestOptions()
                .placeholder(resourceId)//加载成功之前占位图
                .error(resourceId)//加载错误之后的错误图
                .circleCrop()
                .transform(RoundedCorners.class, new Transformation<RoundedCorners>() {
                    @NonNull
                    @Override
                    public Resource<RoundedCorners> transform(@NonNull Context context, @NonNull Resource<RoundedCorners> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        // Glide.with(context).asBitmap().load(resourceId).bitmapTransform(new RoundedCornersTransformation(context, radius, 0, RoundedCornersTransformation.CornerType.ALL)).into(iv);

        Glide.with(context)
                .asBitmap()
                .load(resourceId)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载圆角资源图片
     *
     * @param context
     * @param resourceId
     * @param iv
     * @param radiusPixel
     */
    public static void loadRoundCornerImage(Context context, @DrawableRes int resourceId, ImageView iv, int radiusPixel) {

        RequestOptions options = new RequestOptions()
                .placeholder(resourceId)//加载成功之前占位图
                .error(resourceId)//加载错误之后的错误图
                .circleCrop()
                .transform(RoundedCorners.class, new Transformation<RoundedCorners>() {
                    @NonNull
                    @Override
                    public Resource<RoundedCorners> transform(@NonNull Context context, @NonNull Resource<RoundedCorners> resource, int outWidth, int outHeight) {
                        return null;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });

        Glide.with(context)
                .load(resourceId)
                .apply(options)
                .into(iv);
    }

    /**
     * 加载File类型图片
     *
     * @param context
     * @param file
     * @param iv
     */
    public static void loadImage(Context context, File file, ImageView iv) {
        Glide.with(context).load(file).into(iv);
    }

    /**
     * 加载资源图片
     *
     * @param context
     * @param resourceId
     * @param iv
     */
    public static void loadImage(Context context, @DrawableRes int resourceId, ImageView iv) {
        Glide.with(context).load(resourceId).into(iv);
    }

    /**
     * 清除内存缓存
     *
     * @param context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存
     *
     * @param applicationContext
     */
    public static void clearDiskCache(Context applicationContext) {
        // 必须在后台线程中调用，建议同时clearMemory()
        Glide.get(applicationContext).clearDiskCache();
        clearMemory(applicationContext);
    }


    /**
     * 获取圆形Bitmap
     */
    private static class GlideCircleTransform extends BitmapTransformation {

        public GlideCircleTransform(Context context) {
            super();
        }

        public GlideCircleTransform(BitmapPool bitmapPool) {
            super();
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            //return null;
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            // 如果BitmapPool中找不到符合该条件的Bitmap，get()方法会返回null，就需要我们自己创建Bitmap
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                // 如果想让Bitmap支持透明度，就需要使用ARGB_8888
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            //创建最终Bitmap的Canvas
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            // 将原始Bitmap处理后画到最终Bitmap中
            canvas.drawCircle(r, r, r, paint);
            // return我们新的Bitmap就行,Glide会自动帮我们回收原始Bitmap。
            return result;
        }

//        @Override
//        public String getId() {
//            //return null;
//            return getClass().getName();
//        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }

    /**
     * 获取圆角的Bitmap
     */
    private static class GlideRoundTramsform extends BitmapTransformation {
        private static float radius = 0f;

        public GlideRoundTramsform(Context context) {
            super();
        }

        public GlideRoundTramsform(Context context, int dp) {
            super();
            radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        public GlideRoundTramsform(BitmapPool bitmapPool) {
            super();
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            //return null;
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }
            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

//        @Override
//        public String getId() {
//            //return null;
//            return getClass().getName() + Math.round(radius);
//        }

//        @NonNull
//        @Override
//        public Resource<CenterCrop> transform(@NonNull Context context, @NonNull Resource<CenterCrop> resource, int outWidth, int outHeight) {
//            return null;
//        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }

    /**
     * 加载base64地址格式图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageForTarget(Context context, String url, ImageView imageView) {
        byte[] decode = null;
        if (isBase64Img(url)) {
            url = url.split(",")[1];
            decode = Base64.decode(url, Base64.DEFAULT);
        }


        RequestOptions options = new RequestOptions()
                .placeholder(default_img)//加载成功之前占位图
                .error(default_err_img)//加载错误之后的错误图
                .circleCrop()
                .dontAnimate();

        Glide.with(context)
                .load(decode == null ? url : decode)
                .apply(options)
                .into(imageView);
//        BitmapTypeRequest bitmapTypeRequest = Glide.with(context).load(decode == null ? url : decode).asBitmap();
//        bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.RESULT);
//        bitmapTypeRequest.dontAnimate();
//        bitmapTypeRequest.into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                imageView.setImageBitmap(resource);
//            }
//        });
    }

    public static boolean isBase64Img(String imgurl) {
        if (!TextUtils.isEmpty(imgurl) && (imgurl.startsWith("data:image/png;base64,")
                || imgurl.startsWith("data:image/*;base64,") || imgurl.startsWith("data:image/jpg;base64,"))) {
            return true;
        }
        return false;
    }

}
