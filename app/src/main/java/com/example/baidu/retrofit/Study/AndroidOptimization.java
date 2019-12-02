package com.example.baidu.retrofit.Study;

public class AndroidOptimization {
    /**
     *
     * android 优化
     *

     1 布局方面

     减少绘制层数

     include

     viewstub

     2 绘制优化


     绘制优化是指View的onDraw方法要避免执行大量的操作，这主要体现在两个方面。

     首先，onDraw中不要创建新的局部对象，这是因为onDraw方法可能会被频繁调用，这样就会在一瞬间产生大量的临时对象，这不仅占用了过多的内存而且还会导致系统更加频繁gc，
     降低了程序的执行效率。

     另外一方面，onDraw方法中不要做耗时的任务，也不能执行成千上万次的循环操作，尽管每次循环都很轻量级，但是大量的循环仍然十分抢占CPU的时间片，
     这会造成View的绘制过程不流畅。按照Google官方给出的性能优化典范中的标准，View的绘制帧率保证60fps是最佳的，这就要求每帧的绘制时间不超过16ms（16ms = 1000 / 60），
     虽然程序很难保证16ms这个时间，但是尽量降低onDraw方法的复杂度总是切实有效的

     3. 内存泄露优化：

     分析原因:

     内存泄漏产生的原因在Android中大致分为以下几种：
     1.static变量引起的内存泄漏

     因为static变量的生命周期是在类加载时开始 类卸载时结束，也就是说static变量是在程序进程死亡时才释放，如果在static变量中 引用了Activity 那么 这个Activity由于被引用，便会随static变量的生命周期一样，一直无法被释放，造成内存泄漏。

     解决办法：
     在Activity被静态变量引用时，使用 getApplicationContext 因为Application生命周期从程序开始到结束，和static变量的一样。

     2.线程造成的内存泄漏

     类似于上述例子中的情况，线程执行时间很长，及时Activity跳出还会执行，因为线程或者Runnable是Acticvity内部类，因此握有Activity的实例(因为创建内部类必须依靠外部类)，因此造成Activity无法释放。
     AsyncTask 有线程池，问题更严重

     解决办法：

     1.合理安排线程执行的时间，控制线程在Activity结束前结束。

     2.将内部类改为静态内部类，并使用弱引用WeakReference来保存Activity实例 因为弱引用 只要GC发现了 就会回收它 ，因此可尽快回收

     3.BitMap占用过多内存

     bitmap的解析需要占用内存，但是内存只提供8M的空间给BitMap，如果图片过多，并且没有及时 recycle bitmap 那么就会造成内存溢出。

     解决办法：

     及时recycle 压缩图片之后加载图片

     4.资源未被及时关闭造成的内存泄漏

     比如一些Cursor 没有及时close 会保存有Activity的引用，导致内存泄漏

     解决办法：

     在onDestory方法中及时 close即可

     5.Handler的使用造成的内存泄漏
     由于在Handler的使用中，handler会发送message对象到 MessageQueue中 然后 Looper会轮询MessageQueue 然后取出Message执行，但是如果一个Message长时间没被取出执行，那么由于 Message中有 Handler的引用，而 Handler 一般来说也是内部类对象，Message引用 Handler ，Handler引用 Activity 这样 使得 Activity无法回收。

     解决办法：
     依旧使用 静态内部类+弱引用的方式 可解决

     6.带参数的单例


     https://images2015.cnblogs.com/blog/690927/201606/690927-20160620172701897-2109008457.png

     如果我们在在调用Singleton的getInstance()方法时传入了Activity。那么当instance没有释放时，这个Activity会一直存在。因此造成内存泄露。
     解决方法：

     可以将new Singleton(context)改为new Singleton(context.getApplicationContext())即可，这样便和传入的Activity没关系了。


     7. 属性动画导致的内存泄露

     果在Activity中播放此类动画且没有在onDestroy中去停止动画，那么动画会一直播放下去，尽管已经无法在界面上看到动画效果了，并且这个时候Activity的View会被动画持有，
     而View又持有了Activity，最终Activity无法释放。下面的动画是无限动画，
     会泄露当前Activity，解决方法是在Activity的onDestroy中调用animator.cancel()来停止动画。


     响应速度，ANR



     ListView和Bitmap优化

     要分为三个方面：

     首先要采用ViewHolder并避免在getView中执行耗时操作；

        View view = null;//getView方法要返回的View
         if(convertView == null){//如果当前没有可以复用的View
            view = LayoutInflater.from(context).inflate(resourceId,null);//那么就从XML文件生成一个View
         }else{//否则
            view = convertView;//就使用可以复用的View
         }


        其次要根据列表的滑动状态来控制任务的执行频率，比如当列表快速滑动时显然是不太适合开启大量的异步任务的；

        最后可以尝试开启硬件加速来使Listview的滑动更加流畅。注意Listview的优化策略完全适用于GridView。

    Bitmap：

     (1)将BitmapFactory.Option的inJustDecodeBounds参数设为true并加载图片
     (2)从BitmapFactory.Option中取出图片的原始宽高，它们对应于outWidth和outHeight参数
     (3) 根据采样率的规则并结合目标View的所需大小计算出采样率inSampleSize。
     (4)将BitmapFactory.Option的inJustDecodeBounds参数设为false，然后重新加载图片。





     public static Bitmap decodeSampleFromResources(Resource res, int resId, int reqWidth, int reqHeight) {

         final BitmapFactory.Options options = new BitmapFactory.Options();
         options.inJustDecodeBounds = true;
         BitmapFactory.decodeResource(res, resId, options);

         options.inJustDecodeBoundles = false;
         return BitmapFactory.decodeResource(res, resId, options);
     }

     public static int calculateSampleSize

     {

         final int height = options.outHeight;
         final int width = options.outWidth;
         int inSampleSize = 1;

         if (height > resHeight || width > resWidth) {
             final int halfHeight = height / 2;
             final int halfwidth = width / 2;

             while ((halfHeight / inSampleSize) >= reqHeigh && (halfWidth / inSampleSize) >= reqWidth) {
                 inSampleSize *= 2;
            }
         }
         return inSampleSize;
     }


     线程优化

     线程优化的思想是采用线程池，避免程序中存在大量的Thread。线程池可以重用内部的线程，从而避免了线程的创建和销毁所带来的性能开销，
     同时线程池还能有效地控制线程池的最大并发数，避免大量的线程因互相抢占系统资源从而导致阻塞现象的发生。因此在实际开发中，我们要尽量采用线程池，
     而不是每次都要创建一个Thread对象，关于线程池的详细介绍请参考第11章的内容。



     本节介绍的是一些性能优化的小建议，通过它们可以在一定程度上提高性能。

     1.避免创建过多的对象；
     2.不要过多使用枚举，枚举占用的内存空间要比整型大；
     3.常量请使用static final来修饰；
     4.使用一些Android特有的数据结构，比如SparseArray和Pair等，它们都具有更好的性能；
     5.适当使用软引用和软引用；
     6.采用内存缓存和磁盘缓存；
     7.尽量采用静态内部类，这样可以避免潜在的由于内部类而导致的内存泄露。

     *
     */

}
