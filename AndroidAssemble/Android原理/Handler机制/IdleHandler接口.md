[TOC]


## IdleHandler结构

```
    public static interface IdleHandler {
        /**
         * Called when the message queue has run out of messages and will now
         * wait for more.  
         * Return true to keep your idle handler active, 
         * false
         * to have it removed.  
         * 
         */
        boolean queueIdle();
    }
```
queueIdle()
返回值为 false，即只会执行一次；
返回值为 true，即每次当消息队列内没有需要立即执行的消息时，都会触发该方法。

## next()中处理的时机

IdleHandler用于MessageQueue 出现空闲的时候执行


```
    Message next() {
        // Return here if the message loop has already quit and been disposed.
        // This can happen if the application tries to restart a looper after quit
        // which is not supported.
        final long ptr = mPtr;
        if (ptr == 0) {
            return null;
        }
 
        int pendingIdleHandlerCount = -1; // -1 only during first iteration
        int nextPollTimeoutMillis = 0;
        for (;;) {
            if (nextPollTimeoutMillis != 0) {
                Binder.flushPendingCommands();
            }
 
            nativePollOnce(ptr, nextPollTimeoutMillis);
 
            synchronized (this) {
                // Try to retrieve the next message.  Return if found.
                final long now = SystemClock.uptimeMillis();
                Message prevMsg = null;
                Message msg = mMessages;
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null && !msg.isAsynchronous());
                }
                if (msg != null) {
                    if (now < msg.when) {
                        // Next message is not ready.  Set a timeout to wake up when it is ready.
                        nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                    } else {
                        // Got a message.
                        mBlocked = false;
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        if (DEBUG) Log.v(TAG, "Returning message: " + msg);
                        msg.markInUse();
                        return msg;
                    }
                } else {
                    // No more messages.
                    nextPollTimeoutMillis = -1;
                }
 
                // Process the quit message now that all pending messages have been handled.
                if (mQuitting) {
                    dispose();
                    return null;
                }
 
                // If first time idle, then get the number of idlers to run.
                // Idle handles only run if the queue is empty or if the first message
                // in the queue (possibly a barrier) is due to be handled in the future.
                if (pendingIdleHandlerCount < 0
                        && (mMessages == null || now < mMessages.when)) {
                    pendingIdleHandlerCount = mIdleHandlers.size();
                }
                if (pendingIdleHandlerCount <= 0) {
                    // No idle handlers to run.  Loop and wait some more.
                    mBlocked = true;
                    continue;
                }
 
                if (mPendingIdleHandlers == null) {
                    mPendingIdleHandlers = new IdleHandler[Math.max(pendingIdleHandlerCount, 4)];
                }
                mPendingIdleHandlers = mIdleHandlers.toArray(mPendingIdleHandlers);
            }
 
            // Run the idle handlers.
            // We only ever reach this code block during the first iteration.
            for (int i = 0; i < pendingIdleHandlerCount; i++) {
                final IdleHandler idler = mPendingIdleHandlers[i];
                mPendingIdleHandlers[i] = null; // release the reference to the handler
 
                boolean keep = false;
                try {
                    keep = idler.queueIdle();
                } catch (Throwable t) {
                    Log.wtf(TAG, "IdleHandler threw exception", t);
                }
 
                if (!keep) {
                    synchronized (this) {
                        mIdleHandlers.remove(idler);
                    }
                }
            }
 
            // Reset the idle handler count to 0 so we do not run them again.
            pendingIdleHandlerCount = 0;
 
            // While calling an idle handler, a new message could have been delivered
            // so go back and look again for a pending message without waiting.
            nextPollTimeoutMillis = 0;
        }
    }
```

- next()中大的同步块里进行消息相关的逻辑，没有任何一个return的逻辑被触发，那么就进行IdleHandler的逻辑；

- int pendingIdleHandlerCount = -1; // -1 only during first iteration

- 如果pendingIdleHandlerCount小于0（注意其在第一次进入for循环是被初始化为-1）且没更多的消息需要处理，设置pendingIdleHandlerCount=mIdleHandlers.size()；

- 如果pendingIdleHandlerCount还是<=0的话，表示没有idle handler需要执行，设置mBlocked为true，接着进入下次循环。

- 退出同步块后，接下来就是根据mIdleHandlers来初始化mPendingIdleHandlers。我们就剩下最后一件事了，那就是run Idle handlers。一个for循环用来做这就事情，在循环内如果IdleHandler没必要保留，则会从mIdleHandlers中移除。最后重置pendingIdleHandlerCount为0,

> 只有在 pendingIdleHandlerCount 为 -1 时，才会尝试执行 mIdleHander；
pendingIdlehanderCount 在 next() 中初始时为 -1，执行一遍后被置为 0，所以不会重复执行；
 
> queueIdle()执行的代码同样不能太耗时，因为它是同步执行的，如果太耗时肯定会影响后面的 message 执行


## 流程图

![avatar](https://github.com/lknlll/LinDroid/blob/LinDroid/blog/pic/next%E6%96%B9%E6%B3%95%E9%80%BB%E8%BE%91.png?raw=true) 

## Framework及三方框架的应用

TODO

Instrumentation.ActivityGoing 在 Activity onCreate() 执行前添加；
Instrumentation.Idler 调用的时机就比较多了，是键盘相关的调用；
TextToSpeechService.SynthThread 是在 TTS 合成完成之后发送广播；

1. ActivityThread.handleResumeActivity()

```


    @Override
    public void handleResumeActivity(IBinder token, boolean finalStateRequest, boolean isForward,
            String reason) {
        // If we are getting ready to gc after going to the background, well
        // we are back active so skip it.
        unscheduleGcIdler();
        mSomeActivitiesChanged = true;
        //...

        //该方法最终会执行 onResume方法
        final ActivityClientRecord r = performResumeActivity(token, finalStateRequest, reason);
        if (r == null) {
            // We didn't actually resume the activity, so skipping any follow-up actions.
            return;
        }
        
        //...

        r.nextIdle = mNewActivities;
        mNewActivities = r;
        if (localLOGV) Slog.v(TAG, "Scheduling idle handler for " + r);
        Looper.myQueue().addIdleHandler(new Idler());
    }
```
handleResumeActivity() 方法中末尾会执行 Looper.myQueue().addIdleHandler(new Idler())，这时onResume()方法执行完，界面已经显示出来了

这里看下Idle内部

```
    private class Idler implements MessageQueue.IdleHandler {
        @Override
        public final boolean queueIdle() {
            ActivityClientRecord a = mNewActivities;
            //···
            if (a != null) {
                mNewActivities = null;
                IActivityManager am = ActivityManager.getService();
                ActivityClientRecord prev;
                do {
                    //打印一些日志
                    if (localLOGV) Slog.v(
                        TAG, "Reporting idle of " + a +
                        " finished=" +
                        (a.activity != null && a.activity.mFinished));
                    if (a.activity != null && !a.activity.mFinished) {
                        try {
                            //AMS 进行一些资源的回收
                            am.activityIdle(a.token, a.createdConfig, stopProfiling);
                            a.createdConfig = null;
                        } catch (RemoteException ex) {
                            throw ex.rethrowFromSystemServer();
                        }
                    }
                    prev = a;
                    a = a.nextIdle;
                    prev.nextIdle = null;
                } while (a != null);
            }
            if (stopProfiling) {
                mProfiler.stopProfiling();
            }
            //确认Jit 可以使用，否则抛出异常
            ensureJitEnabled();
            return false;
        }
    }
```


2. ActivityThread强行GC

在ActivityThread中，有一个`GcIdler`内部类，实现了IdleHandler接口。

它在queueIdle方法被回调时，会做强行GC的操作（即调用BinderInternal的forceGc方法），但强行GC的前提是，与上一次强行GC至少相隔5秒以上。 

当AMS(ActivityManagerService)中的这两个方法被调用之后：

- doLowMemReportIfNeededLocked，内存不够。
- activityIdle，ActivityThread的handleResumeActivity方法(Activity的onResume方法也是在这方法里回调)调用的。

会收到`GC_WHEN_IDLE`消息
ActivityThread中的H收到`GC_WHEN_IDLE`消息后，会执行scheduleGcIdler

```
    //
    void scheduleGcIdler() {
        if (!mGcIdlerScheduled) {
            mGcIdlerScheduled = true;
            //添加GC任务
            Looper.myQueue().addIdleHandler(mGcIdler);
        }
        mH.removeMessages(H.GC_WHEN_IDLE);
    }
```

```
    //GC任务
    final class GcIdler implements MessageQueue.IdleHandler {
        @Override
        public final boolean queueIdle() {
            doGcIfNeeded();
            //执行后，就直接删除
            return false;
        }
    }
    // 判断是否需要执行垃圾回收。
    void doGcIfNeeded() {
        mGcIdlerScheduled = false;
        final long now = SystemClock.uptimeMillis();
        //获取上次GC的时间
        if ((BinderInternal.getLastGcTime()+MIN_TIME_BETWEEN_GCS) < now) {
            //Slog.i(TAG, "**** WE DO, WE DO WANT TO GC!");
            BinderInternal.forceGc("bg");
        }
    }
```

3. LeakCanary TODO

LeakCanary在[ReferenceCleaner](https://github.com/square/leakcanary/blob/2227781e104410d181681fa0abb48ff0f1436236/plumber-android-core/src/main/java/leakcanary/internal/ReferenceCleaner.kt)中用到了IdleHandler，源码看起来是在onViewDetachedFromWindow()函数中注册了IdleaHandler，注册这个IdleHandler的目的是为了清除Android ims的bug

第三方库的使用 - LeakCanary
https://juejin.cn/post/6918941568359481352#heading-3

4. Glide TODO
5. Matrix TODO

https://github.com/Tencent/matrix

IdleHandlerLagTracer.java

WarmUpScheduler.java

AndroidHeapDumper.java

LooperMonitor.java

```
    @Override
    public boolean queueIdle() {
        if (SystemClock.uptimeMillis() - lastCheckPrinterTime >= CHECK_TIME) {
            resetPrinter();
            lastCheckPrinterTime = SystemClock.uptimeMillis();
        }
        return true;
    }
```

在queueIdle方法中返回了true，说明这个IdleHandler会被重复调用，每次调用queueIdle()方法时，会去调用resetPrinter方法来检查Looper中的printer对象是不是微信自定义的LooperPrinter

## 在项目中的应用

获取View的height和width；

发送一个返回 true 的 IdleHandler，在里面让某个 View 不停闪烁，这样当用户发呆时就可以诱导用户点击这个View

> 如果是必要的操作，也需要考虑主线程一直不空闲的超时处理

## 通过Hook监控

主线程的IdleHandler执行超过5s同样也是会报ANR

`private final ArrayList<IdleHandler> mIdleHandlers = new ArrayList<IdleHandler>();
`

所以 Hook mIdleHandlers列表，将当前的MessageQueue中的mIdleHandlers列表替换成自己的列表，将原来的IdleHandler包入自己的，就可以监控执行时间并上报风险；



```

class IdleHandlerMonitor {

    private HandlerThread idleHandlerThread;
    private Handler idleHandlerHandler;
    private static String mIdleHandler = null;

    IdleHandlerMonitor() {
        // 1、创建子线程的handler（idleHandlerHandler），方便后续在子线程发送消息不被主线程卡住影响
        if (Build.VERSION.SDK_INT >= 23) {
            this.idleHandlerThread = new HandlerThread("IdleHandlerThread");
            this.idleHandlerThread.start();
            this.idleHandlerHandler = new Handler(this.idleHandlerThread.getLooper());
            this.detectIdleHandler();
        }
    }

    @RequiresApi(api = 23)
    private void detectIdleHandler() {
        // 2、修改MessageQueue中的mIdleHandlers变量，传入自定义的List
        try {
            MessageQueue mainQueue = Looper.getMainLooper().getQueue();
            Field field = MessageQueue.class.getDeclaredField("mIdleHandlers");
            field.setAccessible(true);
            CustomArrayList<MessageQueue.IdleHandler> myIdleHandlerArrayList = new CustomArrayList();
            field.set(mainQueue, myIdleHandlerArrayList);
        } catch (Throwable var4) {
            var4.printStackTrace();
        }
    }

    private class CustomArrayList<T> extends ArrayList {

        public boolean add(Object o) {
            if (o instanceof MessageQueue.IdleHandler) {
                // 3、将原来的IdleHandler包装进自己的CustomIdleHandler
                CustomIdleHandler customIdleHandler = new CustomIdleHandler((MessageQueue.IdleHandler) o);
                return super.add(customIdleHandler);
            }
            return super.add(o);
        }

        public boolean remove(@Nullable Object o) {
            if (o instanceof CustomIdleHandler) {
                return super.remove(((CustomIdleHandler) o));
            }
            return super.remove(o);
        }
    }

    private class CustomIdleHandler implements MessageQueue.IdleHandler {
        private MessageQueue.IdleHandler idleHandler;

        CustomIdleHandler(MessageQueue.IdleHandler idleHandler) {
            this.idleHandler = idleHandler;
        }

        @Override
        public boolean queueIdle() {
            mIdleHandler = this.idleHandler.toString();
            idleHandlerHandler.removeCallbacks(idleHanlderRunnable);
            idleHandlerHandler.postDelayed(idleHanlderRunnable, 3000L);
            // 4、将包装起来的IdleHandler取出来，执行queueIdle，包装前设置多一项3s的延时任务。
            // 只要queueIdle在3s内没执行完，将执行当前的idleHanlderRunnable
            boolean ret = this.idleHandler.queueIdle();
            idleHandlerHandler.removeCallbacks(idleHanlderRunnable);
            return ret;
        }
    }

    // 5、报告输出当前Idle信息超时通知
    private static Runnable idleHanlderRunnable = () -> {
        Log.e("TAG", "[queueIdle] more then 3000L \n message=" + mIdleHandler);
    };
}

```