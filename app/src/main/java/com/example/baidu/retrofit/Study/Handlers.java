package com.example.baidu.retrofit.Study;

import android.os.Handler;

public class Handlers extends Handler {

    /*

     关于为什么 Looper 中的 loop() 方法不会造成主线程阻塞的原因就分析完了, 主要有两点原因:

    1.耗时操作本身并不会导致主线程卡死, 导致主线程卡死的真正原因是耗时操作之后的触屏操作, 没有在规定的时间内被分发。

    2.Looper 中的 loop()方法, 他的作用就是从消息队列MessageQueue 中不断地取消息, 然后将事件分发出去

     https://user-gold-cdn.xitu.io/2019/2/26/16927e6098cf257b?imageView2/0/w/1280/h/960/format/webp/ignore-error/1


      // 核心机制
     去看下handler机制就明白了，网上一大把。

      1.handler机制是使用pipe来实现的

      2.主线程没有消息处理时阻塞在管道的读端

      3.binder线程会往主线程消息队列里添加消息，然后往管道写端写一个字节，这样就能唤醒主线程从管道读端返回，也就是说queue.next()会调用返回

      4.dispatchMessage()中调用onCreate, onResume



    epoll+pipe，有消息就依次执行，没消息就block住，让出CPU，
    等有消息了，epoll会往pipe中写一个字符，把主线程从block状态唤起，主线程就继续依次执行消息，怎么会死循环呢…


     */


}
