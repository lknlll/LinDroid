###### Android 异步消息机制 Handler 相关问题

- postDelayed()和sendMessageDelay 之后的消息是怎么处理，一个队列还是两个队列

- 阻塞时新的比当前延迟短的消息怎么处理

- sendMessage 以后 只有自己能接收是为什么

- Handler中的时间计算用了哪种API，为什么

---

首先postDelayed(Runnable r, long delayMillis)就是Runnable经getPostMessage(r)处理成Message以后调和sendMessageDelayed;

post()/postDelayed()/sendMessage()->sendMessageDelayed()->sendMessageAtTime()->enqueueMessage()

postAtTime()->sendMessageAtTime()->enqueueMessage()

postAtFrontOfQueue()->sendMessageAtFrontOfQueue()->enqueueMessage()

---

各种发消息api 最终收口于

Handler.enqueueMessage(@NonNull MessageQueue queue, @NonNull Message msg,long uptimeMillis)

这里面msg.target = this; 这块是可以确保多个Handler的情况下自己发的消息自己Handle

继而->MessageQueue.enqueueMessage(Message msg, long when)

Looper.loop()中 通过可能阻塞的方法MessageQueue.next()获得一个消息

MessageQueue.next()中进行delay消息的阻塞，新插入消息符合唤醒条件时的唤醒；

next方法的细节如下：

内部有一个死循环，会在获取到合适Message的时候跳出，没有合适消息的时候就阻塞

> now 这里的时间用的是uptimeMillis()表示自系统启动时开始计数，以毫秒为单位。  
返回的是从系统启动到现在这个过程中的处于非休眠期的时间。  
当系统进入深度睡眠(CPU关闭，屏幕显示器不显示，设备等待外部输入)时，或者空闲或其他省电机制的影响，此时时钟停止，但是该时钟不会被时钟调整。
>System.currentTimeMillis()获取的时间，可以通过System.setCurrentTimeMillis(long)方法，进行修改，那么在某些情况下，一旦被修改，时间间隔就不准了

这个方法是大多数间隔时间的基础，例如Thread.sleep(millis)方法、Object.wait(millis)方法、System.nanoTime()都是基于此方法的

nativePollOnce() 是一个阻塞，它的时间nextPollTimeoutMillis，就是由now < msg.when的情况下计算的；

这里的msg是mMessages, 可以理解为头部消息；

注释中说“下一个消息尚未准备好，设置一个超时，来在就绪时唤醒”

也就是头部Message 有 delay 的情况下，就会结束这一趟的for循环，走下一趟时nativePollOnce就按照计算好的时间进行阻塞；

``` 
    Message next() {

        /*省略*/

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
                /*省略*/
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
                        return msg; //**只有这里真正返回实际的Message给Looper去用**
                    }
                } else {
                    // No more messages.
                    nextPollTimeoutMillis = -1;
                }

                /*省略*/
                
                if (pendingIdleHandlerCount <= 0) {
                    // No idle handlers to run.  Loop and wait some more.
                    mBlocked = true;
                    continue;
                }
            }
            /*省略*/
        }
    }
```

阻塞的情况下如何应对新的阻塞更短的消息？

上面提过 新来消息 收口于 MessageQueue.enqueueMessage(Message msg, long when)，看一下细节如下：

首先Message p做为已有的头部消息；

when < p.when 的时候, 交换了放入message与原来消息队列头部P的位置，

并且 needWake = mBlocked; 

上文 next()中阻塞的时候mBlocked=true，当needWake =true的时候nativeWake(mPtr)唤醒

上文 next()中被nativePollOnce阻塞的 下文部分按照新的mMessage开始执行

    boolean enqueueMessage(Message msg, long when) {
        if (msg.target == null) {
            throw new IllegalArgumentException("Message must have a target.");
        }
        if (msg.isInUse()) {
            throw new IllegalStateException(msg + " This message is already in use.");
        }

        synchronized (this) {
            if (mQuitting) {
                IllegalStateException e = new IllegalStateException(
                        msg.target + " sending message to a Handler on a dead thread");
                Log.w(TAG, e.getMessage(), e);
                msg.recycle();
                return false;
            }

            msg.markInUse();
            msg.when = when;
            Message p = mMessages;
            boolean needWake;
            if (p == null || when == 0 || when < p.when) {
                // New head, wake up the event queue if blocked.
                msg.next = p;
                mMessages = msg;
                needWake = mBlocked;
            } else {
                // Inserted within the middle of the queue.  Usually we don't have to wake
                // up the event queue unless there is a barrier at the head of the queue
                // and the message is the earliest asynchronous message in the queue.
                needWake = mBlocked && p.target == null && msg.isAsynchronous();
                Message prev;
                for (;;) {
                    prev = p;
                    p = p.next;
                    if (p == null || when < p.when) {
                        break;
                    }
                    if (needWake && p.isAsynchronous()) {
                        needWake = false;
                    }
                }
                msg.next = p; // invariant: p == prev.next
                prev.next = msg;
            }

            // We can assume mPtr != 0 because mQuitting is false.
            if (needWake) {
                nativeWake(mPtr);
            }
        }
        return true;
    }
