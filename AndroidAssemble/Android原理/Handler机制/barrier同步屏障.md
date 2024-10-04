
[TOC]

Android 4.1.1(API 16)，增加了对异步消息和同步屏障消息的支持

**为什么要有同步屏障：**

让某些异步消息的特殊任务得以更快被执行的机制


## 屏幕刷新案例

刷新率60Hz 表示屏幕在一秒内刷新 60 次，也就是每隔 16.6ms 刷新一次

view 绘制的起点是在 viewRootImpl.requestLayout()，但不会马上开始进行绘制任务，而是会给主线程设置一个同步屏障，并设置 ASYNC 信号监听；当 ASYNC信号的到来，会发送一个异步消息到主线程 Handler，执行我们上一步设置的绘制监听任务，并移除同步屏障

绘制流程里的ViewRootImpl.requestLayout()片段

```
    @Override
    public void requestLayout() {
        if (!mHandlingLayoutInLayoutRequest) {
            //校验主线程
            checkThread();
            mLayoutRequested = true;
            //调用这个方法启动绘制流程
            scheduleTraversals();
        }
    }

```

检查是否主线程后，在调用scheduleTraversals()的时候 postSyncBarrier添加同步消息屏障；
doTraversal()手动调用MessageQueue.removeSyncBarrier(int token)方法移除。token是postSyncBarrier()方法返回的。

```
    @UnsupportedAppUsage
    void scheduleTraversals() {
        if (!mTraversalScheduled) {
            mTraversalScheduled = true;
            //1. 往主线程的Handler对应的MessageQueue发送一个同步屏障消息
            mTraversalBarrier = mHandler.getLooper().getQueue().postSyncBarrier();
            //2.将mTraversalRunnable保存到Choreographer中
            mChoreographer.postCallback(
                    Choreographer.CALLBACK_TRAVERSAL, mTraversalRunnable, null);
            if (!mUnbufferedInputDispatch) {
                scheduleConsumeBatchedInput();
            }
            notifyRendererOfFramePending();
            pokeDrawLockIfNeeded();
        }
    }    
    ...
     //在doTraversal方法中移除同步消息屏障
     void doTraversal() {
        if (mTraversalScheduled) {
            mTraversalScheduled = false;
            //移除同步屏障
            mHandler.getLooper().getQueue().removeSyncBarrier(mTraversalBarrier);
            ...
        }
    }

```

正常插入消息会调用enqueueMessage方法，同时将handler赋值给message的target。

```
    //将消息插入消息队列
    private boolean enqueueMessage(@NonNull MessageQueue queue, @NonNull Message msg,
            long uptimeMillis) {
        msg.target = this;
        msg.workSourceUid = ThreadLocalWorkSource.getUid();
        //进行判断是否将消息设置为异步消息
        if (mAsynchronous) {
            msg.setAsynchronous(true);
        }
        return queue.enqueueMessage(msg, uptimeMillis);
    }

```
转发给MessageQueue的enqueueMessage()，下面片段展示了target为空也就是这个message没有对应的handler则会报异常。

```
boolean enqueueMessage(Message msg, long when) {
        if (msg.target == null) {
            throw new IllegalArgumentException("Message must have a target.");
        }
        if (msg.isInUse()) {
            throw new IllegalStateException(msg + " This message is already in use.");
        }
        ...
        // 如果需要唤醒，则唤醒
        if (needWake) {
            nativeWake(mPtr);
        }
}

```

## 同步屏障的发送
```
    //MessageQueue#postSyncBarrier
    private int postSyncBarrier(long when) {
        synchronized (this) {
            final int token = mNextBarrierToken++;
            //1、屏障消息和普通消息的区别是屏障消息没有tartget。
            final Message msg = Message.obtain();
            msg.markInUse();
            msg.when = when;
            msg.arg1 = token;

            Message prev = null;
            Message p = mMessages;
            //2、根据时间顺序将屏障插入到消息链表中适当的位置
            if (when != 0) {
                while (p != null && p.when <= when) {
                    prev = p;
                    p = p.next;
                }
            }
            if (prev != null) { // invariant: p == prev.next
                msg.next = p;
                prev.next = msg;
            } else {
                msg.next = p;
                mMessages = msg;
            }
            //3、返回一个序号，通过这个序号可以撤销屏障
            return token;
        }
    }
```


## 同步屏障消息处理
下面的next()片面展示了同步屏障的处理

```

    @UnsupportedAppUsage
    Message next() {
        for (;;) {
            nativePollOnce(ptr, nextPollTimeoutMillis);
            synchronized (this) {
                Message msg = mMessages;
                //如果msg.target为空，也就是说是一个同步屏障消息，则进入这个判断里面
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    //在这个while循环中，找到最近的一个异步消息
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null && !msg.isAsynchronous());
                }
                //找到了异步消息
                if (msg != null) {
                    //如果消息的处理时间小于当前时间 则等待
                    if (now < msg.when) {
                        // Next message is not ready.  Set a timeout to wake up when it is ready.
                        nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                    } else {
                        // Got a message.
                        //处理消息
                        mBlocked = false;
                        //将异步消息移除
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        if (DEBUG) Log.v(TAG, "Returning message: " + msg);
                        msg.markInUse();
                        //返回异步消息
                        return msg;
                    }
                } else {
                    // No more messages.
                    //没有找到异步消息则进入阻塞状态，等待被唤醒
                    nextPollTimeoutMillis = -1;
                }
                ...
    }

```
**插入普通消息会唤醒消息队列，但是插入屏障不会。**

## 消息屏障和IdleHandler

消息队列是空的时候，插入一个屏障，会触发IdleHandler么？

答：不会，插入屏障，并不会唤醒线程



如果删除了屏障，消息队列空了，会触发IdleHandler么？

答：不会。处理完消息，发现目前没有消息要处理(也可以是触发时间没到)，触发IdleHandler，处理完毕。会再次检查是否有消息要处理，因为IdleHandler触发时，有可能有新的消息插入消息队列。如果没有，进入休眠，再次被唤醒时，不会再次(重复)触发IdleHandler。简单的说就是消息队列处理完最后一个消息，休眠之前调用过IdleHandler，休眠之后被唤醒，还是没有要处理的消息，不会重复调用IdleHandler



如果消息队列只有一个屏障消息，插一个普通消息会触发IdleHandler么？

答：有可能。IdleHandler的触发条件是，消息队列为空，或者第一条消息的触发时间还没到。所以如果屏障的超时时间还没有到，也就是目前还没有消息要处理，会触发IdleHandler



如果消息队列只有一个屏障消息，插入一个异步消息会触发IdleHandler么？

答：有可能，关键看屏障的触发时间到了没有，如果没有到，就会触发IdleHandler，反之就不会。
