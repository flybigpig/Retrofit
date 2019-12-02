package com.example.baidu.retrofit.Study;


import android.drm.DrmStore;
import android.util.Log;

import com.example.baidu.retrofit.util.BaseObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Rxjava 操作符
 * <p>
 * https://blog.csdn.net/lyabc123456/article/details/90146398#create_12
 */

public class RxJavaFunction {

    private String TAG = "RxJavaFunction";

    /**
     * 快速创建1个被观察者对象，每隔指定时间发送1个事件（从0开始无限递增1的整数序列）
     */
    private void interval() {
        //interval操作符,创建以1秒为事件间隔发送整数序列的Observable
        Observable.interval(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    /**
     * ObservableEmitter  CreateEmitter(ObservableCreate类的静态内部类)，在CreateEmitter创建时持有了Observer对象
     * <p>
     * 在CreateEmitter类的onNext、onError、onComplete方法中分别调用了它持有的Observer对象的onNext、onError、onComplete方法：
     */
    private void create() {
        //通过Observable.create() 创建一个被观察者 Observable 对象
        Observable.create(new ObservableOnSubscribe<Integer>() {
            /** 在subscribe（）里定义需要发送的事件 */
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter类对象产生 & 发送事件
                // ObservableEmitter类负责定义事件发射器 & 向观察者发送事件
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {//订阅一个观察者对象

            // 默认最先调用复写的 onSubscribe（）
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }


            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }

        });

    }

    public void just() {
        // 1. 创建时传入整型1、2、3、4, 快速创建被观察者对象（Observable）最多只能发送10个以下事件
        // 在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)
        Observable.just(1, 2, 3, 4).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }

        });
    }

    /**
     *
     */
    public void fromArray() {
        // 1. 设置需要传入的数组, 快速创建被观察者对象（Observable） & 可发送10个以上事件（数组形式）
        // 可用于对数组进行遍历
        // 注：若直接传递一个list集合进去，否则会直接把list当做一个数据元素发送
        Integer[] items = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Observable.fromArray(items).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    /**
     * 迭代器，传入集合
     */
    public void fromIterable() {
        // 1. 设置一个集合
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + 1);
        }
        // 2. 通过fromIterable()将集合中的对象 / 数据发送出去
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }


    /**
     * 延迟创建 Observable
     */
    public void differ() {
        // 通过defer 定义被观察者对象 注：此时被观察者对象还没创建
        Observable<Long> observable = Observable.defer(new Callable<ObservableSource<? extends Long>>() {
            @Override
            public ObservableSource<? extends Long> call() throws Exception {
                Log.d(TAG, "Observable创建的时间戳: " + System.currentTimeMillis());
                return Observable.just(System.currentTimeMillis());
            }
        });

        Log.d(TAG, "observable.subscribe的时间戳: " + System.currentTimeMillis());
        //观察者开始订阅 注：此时，才会调用defer() 创建被观察者对象(Observable)
        observable.subscribe(new Observer<Long>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "接收到的时间戳是" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }


    public void timer() {
        //延时2s发送一个事件, 会发送一个long类型数值0，等同于延时2s后调用一次onNext(0)触发事件
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }


    public void intervalRange() {
        //延时3s后，每隔2s发送一个事件，事件序列：从5开始递增，总共发送10个事件
        //前四个参数含义为：long start, long count, long initialDelay, long period
        Observable.intervalRange(5, 10, 3, 2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    public void range() {
        // 从2开始发送10个事件, 每次发送的事件递增1
        // final int start, final int count
        // 注意：参数是int型的，count必须大于0，且满足start + (count - 1) <= Integer.MAX_VALUE
        Observable.range(2, 10)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }


    /**
     * 对被观察者发送的每1个事件都通过指定的函数处理，从而变换成另外一种事件, 可用于数据类型转换
     */
    public void map() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 被观察者发送事件, 参数为整型1、2、3
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
            //使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer + "的参数从 整型" + integer + " 变换成 字符串类型" + integer;
            }
        }).subscribe(new Consumer<String>() {

            //观察者接收到是变换后的事件: 字符串类型
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }


    /**
     * 将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送.
     * 将Observable每一次发射的事件都转换成一个新的Observable，最好将这些Observable合并成一个新的Observable发送给观察者，
     * 但不能保证观察者是按照原始序列的顺序收到事件的。
     * 无序的将被观察者发送的整个事件序列进行变换。
     * ————————————————
     * 版权声明：本文为CSDN博主「川峰」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/lyabc123456/article/details/90146398
     */
    public void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
            // 采用flatMap（）变换操作符
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                // 通过flatMap中将被观察者生产的事件序列先进行拆分，
                // 将每个事件转换为一个新的发送三个String的事件，最终合并，再发送给被观察者
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    /**
     * 类似于flatMap()，但是保证观察者收到的事件顺序是严格按照原始事件序列发送的顺序。
     */
    public void concatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
            //采用concatMap（）变换操作符
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                // 通过concatMap中将被观察者生产的事件序列先进行拆分，
                // 将每个事件转换为一个新的发送三个String的事件，最终合并，再发送给被观察者
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    /**
     *
     */
    public void buffer() {
        //每次从1, 2, 3, 4, 5发送3个数据，每次发送往后移动一个数量的位置
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1) // 设置缓存区大小 & 步长,
                // 缓存区大小: 每次从被观察者中获取的事件数量
                // 步长: 每次需要发送新事件时需要往后移动的位置
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        Log.d(TAG, " 缓存区里的事件数量 = " + stringList.size());
                        for (Integer value : stringList) {
                            Log.d(TAG, " 事件 = " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * 组合多个被观察者一起发送数据，合并后按时间线并行执行
     */
    public void merge() {
        // merge（）：组合多个被观察者（≤4个）一起发送数据
        // 注：合并后按照时间线并行执行
        Observable.merge(
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS)) // 从2开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

    }

    /**
     *合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列）进行发送
     * 新的事件序列的长度由长度较短的那个Observable决定
     *
     */
    public void zip() {
        Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);
        Observable<String> observable2 = Observable.just("A", "B", "C");
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return s + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    /**
     *
     * 作用：当多个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 其他Observable发送的每个数据结合，结合的规则由combineLatest()传入的函数决定，最终基于该函数的结果发送数据。
     *
     * 与Zip()的区别：Zip() = 按个数合并，即1对1合并；CombineLatest() = 按时间合并，即在同一个时间点上合并。
     * ————————————————
     * 版权声明：本文为CSDN博主「川峰」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/lyabc123456/article/details/90146398
     *
     */
    public void combineLatest() {
        Observable.combineLatest(
                Observable.just(1L, 2L, 3L), // 第1个发送数据事件的Observable
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), // 第2个发送数据事件的Observable：从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(Long o1, Long o2) throws Exception {
                        // o1 = 第1个Observable发送的最新（最后）1个数据
                        // o2 = 第2个Observable发送的每1个数据
                        // 合并的逻辑 = 相加
                        // 即第1个Observable发送的最后1个数据 与 第2个Observable发送的每1个数据进行相加
                        Log.e(TAG, "合并的数据是： " + o1 + " " + o2);
                        return o1 + o2;
                    }
                }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long s) throws Exception {
                Log.e(TAG, "合并的结果是： " + s);
            }
        });
    }

    /**
     *
     * subscribeOn() & observeOn()操作符
     *
     * 作用：线程调度，subscribeOn()指定被观察者发送事件的工作线程，observeOn()指定观察者响应事件的工作线程。
     *
     * 在 RxJava模型中，被观察者 （Observable） / 观察者（Observer）的工作线程 = 创建自身的线程，即，若被观察者 （Observable） / 观察者（Observer）
     * 在主线程被创建，那么他们的工作（生产事件 / 接收& 响应事件）就会发生在主线程。所以在在Android中默认被观察者和观察者是运行在相同的工作线程（即定义他们的线程
     * ，也就是默认的UI主线程中）。
     *
     * 在Android中，要实现在子线程中实现耗时的网络操作等，然后回到主线程实现 UI操作，那么对应的RxJava中，可理解为：
     *
     * 被观察者 （Observable） 在 子线程 中生产事件（如实现耗时操作等等）
     * 观察者（Observer）在 主线程 接收 & 响应事件（即实现UI操作）
     * ————————————————
     * 版权声明：本文为CSDN博主「川峰」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/lyabc123456/article/details/90146398
     *
     */




    /**
     *
     * do操作符
     * 作用：在某个事件的生命周期中调用
     * do操作符有一系列：
     *
     * 方法	含义
     * doOnEach()	Observable每次发送事件前调用1次（包括完成和错误的事件）
     * doOnNext()	执行Next事件前调用
     * doAfterNext()	执行Next事件后调用
     * doOnError()	发送错误事件前调用
     * doOnCompleted()	正常发送事件完毕调用
     * doOnTerminate()	执行终止前调用(包括正常和异常终止的情况)
     * doAfterTerminate()	执行终止后调用
     * doFinally()	最后执行
     * doOnSubscribe	观察者订阅时调用
     * ————————————————
     * 版权声明：本文为CSDN博主「川峰」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/lyabc123456/article/details/90146398
     *
     */

    /**
     *
     * 过滤
     *
     */
    public void filter() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
            }
        }).filter(new Predicate<Integer>() {
            // 根据test()的返回值 对被观察者发送的事件进行过滤 & 筛选
            // 返回true，则继续发送, 返回false，则不发送（即过滤）
            @Override
            public boolean test(Integer integer) throws Exception {
                //过滤整数≤3的事件
                return integer > 3;
            }
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "过滤后得到的事件是："+ value  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }


    /**
     *  all
     *：判断发送的每项数据是否都满足设置的函数条件, 若满足返回 true；否则返回 false
     */
    public void all() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        // 该函数用于判断Observable发送的10个数据是否都满足integer<=10
                        return (integer <= 10);
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                // 输出返回结果, 若满足返回 true；否则返回 false
                Log.d(TAG, "result is " + aBoolean);
            }
        });
    }
}
