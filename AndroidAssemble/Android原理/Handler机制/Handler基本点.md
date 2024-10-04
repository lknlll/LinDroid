
[TOC] 

##### 基本使用

1.Handler实例
2.Message实例（handler.post(runnable)Runnable 会被封装进一个 Message） 

> 建议Message msg = Message.obtain() 如果有已经存在的可以重用，避免分配内存

3.Message赋值
4.发送Handler.sendMessage()
5.接收handleMessage

##### 角色

1.Handler:把Message添加到MessageQueue里，处理Looper发送过来的Message。
2.Looper:关联线程以及消息的分发，取出MessageQueue的Message，将Message发送到Handler，其loop()方法中不断调用messageQueue的next()方法，当有消息就处理，否则阻塞在messageQueue的next()方法中。
3.Message：消息，要发送和处理的信息。
4.MessageQueue：消息队列，储存Handler发送过来的Message，通过next()方法无限循环，不断判断是否有消息，有就返回这条消息并移除，底层基本数据结构 - 单链表

近似于：生产者消费者模式；



##### 启动时机

App启动，UI线程初始化Looper，创建MessageQueue，

##### 数量和关联

一个Thread中可以有多个Handler，但只能有一个Looper，一个MessageQueue，和多个Message。

借用了ThreadLocal来保证Looper、MessageQueue在当前线程的唯一性

Handler 跟线程的关联是靠 Looper 来实现，没有Looper的Handler会报异常

```
    public Handler(Callback callback, boolean async) {
        //检查当前的线程是否有 Looper
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                "Can't create handler inside thread that has not called Looper.prepare()");
        }
        //Looper 持有一个 MessageQueue
        mQueue = mLooper.mQueue;
    }

```
##### 线程的切换

Handler.handleMessage() 所在的线程是 Looper.loop() 方法被调用的线程，也可以说成 Looper 所在的线程，并不是创建 Handler 的线程

##### 角色和工作流程图

Roles

![avatar](https://github.com/lknlll/LinDroid/blob/LinDroid/blog/pic/handlerRoles.png?raw=true) 

WorkFlow

![avatar](https://github.com/lknlll/LinDroid/blob/LinDroid/blog/pic/handlerWorkFlow.png?raw=true) 


##### 主线程Looper的关键环节

```
//初始化阶段
ActivityThread.main();//初始化入口

Looper.prepareMainLooper(); //初始化

Looper.prepare(false); //设置不可关闭

Looper.sThreadLocal.set(new Looper(quitAllowed)); //ThreadLocal

Looper.mQueue = new MessageQueue(quitAllowed); //和MessageQueue绑定

Looper.mThread = Thread.currentThread();//线程绑定

//工作阶段

Looper.loop();//循环

    myLooper().mQueue.next(); //循环获取MessageQueue中的消息

    nativePollOnce(); //阻塞队列

    native -> pollInner() //jni阻塞的实现epoll机制

    native -> epoll_wait();

Handler.dispatchMessage(msg);//消息分发


```

###### 两个死循环

loop 方法中存在一个 for(;;) 死循环，如果该方法中 queue.next() 方法返回 null ，那么直接 return 退出整个死循环，整个 ActivityThread.main() 方法也就结束了，整个程序也就退出了

queue.next() 这个方法中也有一个 for(;;) 死循环，里面有一个关键方法 nativePollOnce()阻塞等待直到下一条消息可用，enqueueMessage时有nativeWake进行唤醒；

nativePollOnce()入参

    阻塞方法，主要是通过 native 层的 epoll 监听文件描述符的写入事件来实现的。
    nextPollTimeoutMillis = -1，一直阻塞不会超时。
    nextPollTimeoutMillis = 0，不会阻塞，立即返回。
    nextPollTimeoutMillis > 0，最长阻塞nextPollTimeoutMillis毫秒(超时)，如果期间有程序唤醒会立即返回。


###### next()取消息分析

 - code:

```
//MessageQueue
Message next() {
    //...
    for (;;) {
        //...
        //
        nativePollOnce(ptr, nextPollTimeoutMillis);

        synchronized (this) {
            // Try to retrieve the next message.  Return if found.
            final long now = SystemClock.uptimeMillis();
            Message prevMsg = null;
            Message msg = mMessages;
            //...
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
        }

        // Run the idle handlers. 
        //...
    }
}

```

MessageQueue并没有使用一个集合把所有的消息都保存起来，它只使用了一个mMessages对象表示当前待处理的消息。消息列表就是由mMessages组成的单链表结构。

##### Handler 的Callback

Handler 的构造方法中有几个要求传入 Callback

```
//Handler.dispatchMessage(msg) 
public void dispatchMessage(Message msg) {
  //这里的 callback 是 Runnable
  if (msg.callback != null) {
    handleCallback(msg);
  } else {
    //如果 callback 处理了该 msg 并且返回 true， 就不会再回调 handleMessage
    if (mCallback != null) {
      if (mCallback.handleMessage(msg)) {
        return;
      }
    }
    handleMessage(msg);
  }
}

```
Todo
场景：Hook `ActivityThread.mH`， 在 ActivityThread 中有个成员变量 mH ，它是个 Handler，应用：插件化框架；拦截Activity生命周期， 通过调用ActivityManager的finishActivity结束掉生命周期抛出异常的Activity;
[Cockroach](https://github.com/android-notes/Cockroach)

##### WHY Leak and Ref link
将 Handler 定义成静态的内部类，在内部持有Activity的弱引用，并在Acitivity的onDestroy()中调用handler.removeCallbacksAndMessages(null)及时移除所有消息。

Handler在Activity主线程中发送消息导致Leak的持有情况分析

首先说匿名内部类Handler的实例为何会持有外部类的引用;

内部类虽然和外部类写在同一个文件中，但是编译后还是会生成不同的class文件，其中内部类的构造函数中会传入外部类的实例，然后就可以通过this$0访问外部类的成员。

更好理解的方式，因为在内部类中可以调用外部类的方法，变量等等，所以肯定会持有外部类的引用的

Activity的匿名内部类的实例mHandler持有引用了，而Handler的引用是被Message持有了，Message引用是被MessageQueue持有，所以较为完整的引用链是：

主线程 —> threadlocal —> Looper —> MessageQueue —> Message —> Handler —> Activity

GC Root也就是主线程，主线程一直在运行肯定不不会被JVM回收

##### Message池管理

设计模式：享元模式

当我们创建一个 Message 使用完进行回收后，并没有把这个 Message 对象销毁，而是放到了一个消息池，这个池子最大能容纳 50 个 Message 对象。数据结构是 **单链表** ，当需要创建 Message 对象使用时，可以查看下池子是否为空，如果不是，就取一个拿出来使用。这样可以避免创建更多的 Message 对象，一定程度上达到节省内存和申请对象带来的开销。

```

// frameworks/base/core/java/android/os/Message.java

// 下面几个变量是管理Message池子的一些工具变量
/** @hide */
public static final Object sPoolSync = new Object();
// sPool是池子的表头元素
private static Message sPool;
// 池子中的Message元素个数
private static int sPoolSize = 0;
// 最大能存放50个Message元素
private static final int MAX_POOL_SIZE = 50;

// Android 5.0之后这个值都为true
private static boolean gCheckRecycle = true;


/**
 * Return a new Message instance from the global pool. Allows us to
 * avoid allocating new objects in many cases.
 */
public static Message obtain() {
    // 如果池子不是空的，则将其表头Message拿出来返回
    synchronized (sPoolSync) {
        if (sPool != null) {
            Message m = sPool;
            sPool = m.next;
            m.next = null;
            // 清楚使用标志
            m.flags = 0; // clear in-use flag
            // 个数减一
            sPoolSize--;
            return m;
        }
    }
    // 如果池子是空的，就需要创建新的对象
    return new Message();
}

//回收
public void recycle() {
    // 如果回收正在使用的msg，则抛出异常
    if (isInUse()) {
        if (gCheckRecycle) {
            throw new IllegalStateException("This message cannot be recycled because it "
                    + "is still in use.");
        }
        return;
    }
    recycleUnchecked();
}

void recycleUnchecked() {
    // Mark the message as in use while it remains in the recycled object pool.
    // Clear out all other details.
    // 回收过程中设置为正在使用状态
    flags = FLAG_IN_USE;
    // 成员变量值重置
    what = 0;
    arg1 = 0;
    arg2 = 0;
    obj = null;
    replyTo = null;
    sendingUid = UID_NONE;
    workSourceUid = UID_NONE;
    when = 0;
    target = null;
    callback = null;
    data = null;

    // 如果当前线程池的Message数量小于MAX_POOL_SIZE，
    // 则将当前回收的Message放入表头，sPoolSize递增
    synchronized (sPoolSync) {
        if (sPoolSize < MAX_POOL_SIZE) {
            next = sPool;
            sPool = this;
            sPoolSize++;
        }
    }
}

```

##### Java层异常拦截的一种思路

分析异常的堆栈信息可以发现，都经由point 1位置的Looper.loop()

```

    java.lang.RuntimeException: 我崩溃了
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3639)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3796)
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:103)
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2214)
        at android.os.Handler.dispatchMessage(Handler.java:106)
        at android.os.Looper.loopOnce(Looper.java:201)//point 2
        at android.os.Looper.loop(Looper.java:288)//point 1
        at android.app.ActivityThread.main(ActivityThread.java:7842)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1003)
    Caused by: java.lang.RuntimeException: 我崩溃了

```

在Application中try catch mainLooper的loop方法
```

new Handler(Looper.getMainLooper()).post(() -> {
    while (true) {
        try {
            Looper.loop();
        } catch (Exception e) {
            //保存日志并上报..
        }
        //catch (Throwable throwable){ }//想要连Error(如OOM)都一起拦截就用Throwable
    }
});


```

##### runWithScissors

Android 4.2 (API 17)增加，用于子线程提交同步任务

runWithScissors()方法接受一个Runnable和超时时间，调用此方法提交一个任务后：
1、若消息发送线程和Handler创建线程是同一线程，那么执行Runnable的run方法
2、若消息发送线程和Handler创建线程不在同一线程，可以理解为子线程向主线程提交了一个任务，任务提交后，子线程会进入休眠状态等待唤醒，一直等到任务执行结束
注意！！！该方法不但被@hide修饰，在代码注释也向开发者告知这是个危险方法，不建议使用，因为runWithScissors()方法有两个严重缺陷：
1、无法取消已提交的任务，即使消息的发送线程已经死亡，主线程仍然会取出消息队列的任务执行，但这时候运行的程序是不符合我们的预期的
2、可能会造成死锁：子线程向主线程(创建Handler的线程)提交了延迟任务后，子线程是处于等待被唤醒的状态，此时若主线程退出了loop循环并清空了消息队列，那子线程提交的任务就永远不会被唤醒执行，该任务持有的锁永远不会被释放，造成死锁

TODO
https://cloud.tencent.com/developer/article/1746579