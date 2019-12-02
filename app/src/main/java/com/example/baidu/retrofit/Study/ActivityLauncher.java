package com.example.baidu.retrofit.Study;

public class ActivityLauncher {
    /*

    AMS:

    点击启动app,调用AMS的StartActivity,(检查权限或者注册文件)

    调用ActivityThread去判断进程存在与否，（Zygote 去创建一个进程，执行main方法，进入loop,

    AMS负责管理系统所有Activity，所以应用startActivity 最终会通过Binder调用到AMS的startActivity方法，AMS启动一个Activity之前会做一些检查，例如权限、
    是否在清单文件注册等，然后就可以启动了，AMS是一个系统服务，在单独进程，所以要将生命周期告诉应用，又涉及到跨进程调用，这个跨进程同样采用Binder，
    媒介是通过ActivityThread的内部类ApplicationThread，AMS将生命周期跨进程传到ApplicationThread，然后ApplicationThread 再分发给ActivityThread内部的
    Handler，这时候生命周期已经回调到应用主线程了，回调Activity的各个生命周期方法

    Activity启动流程大致如下：

       1. Context -> startActivity
       2. AMS -> startActivity
       3. 进程不存在则通知 Zygote 启动进程，启动完进程，执行ActivityThread的main方法，进入loop循环，通过Handler分发消息。
       4. ApplicationThread -> scheduleLaunchActivity
       5. ActivityThread -> handleLaunchActivity
       6. 其它生命周期回调


    从第四部开始解析：


     4-->

        ApplicationThread#scheduleLaunchActivity

        sendMessage(ActivityClientRecord,"") ; // 发送对象

    5  -->


        ActivityThread的内部类H

        //1. 启动一个Activity，涉及到创建Activity对象，最终返回Activity对象
        Activity a = Activity a = performLaunchActivity(r, customIntent);


        private void handleLaunchActivity(ActivityClientRecord r, Intent customIntent, String reason) {
        ...
        // Make sure we are running with the most recent config.
        handleConfigurationChanged(null, null);

        // Initialize before creating the activity //初始化WindowManagerGlobal
        WindowManagerGlobal.initialize();

        //1. 启动一个Activity，涉及到创建Activity对象，最终返回Activity对象
        Activity a = Activity a = performLaunchActivity(r, customIntent);

        if (a != null) {
            r.createdConfig = new Configuration(mConfiguration);
            reportSizeConfigurations(r);
            Bundle oldState = r.state;
        //2. Activity 进入onResume方法
            handleResumeActivity(r.token, false, r.isForward,
                    !r.activity.mFinished && !r.startsNotResumed, r.lastProcessedSeq, reason);

            if (!r.activity.mFinished && r.startsNotResumed) {
            //3. 如果没能在前台显示，就进入onPuse方法
                performPauseActivityIfNeeded(r, reason);

            }
        } else {
            // If there was an error, for any reason, tell the activity manager to stop us.
        //4. activity启动失败，则通知AMS finish掉这个Activity
            try {
                ActivityManagerNative.getDefault()
                    .finishActivity(r.token, Activity.RESULT_CANCELED, null,
                            Activity.DONT_FINISH_TASK_WITH_ACTIVITY);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }
    }

        注释1：performLaunchActivity，开始启动Activity了
        注释2：Activity进入Resume状态，handleResumeActivity
        注释3：如果没能在前台显示，那么进入pause状态，performPauseActivityIfNeeded
        注释4，如果启动失败，通知AMS去finishActivity。













     */



}
