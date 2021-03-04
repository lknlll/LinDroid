postDelayed()和sendMessageDelay 之后的消息是怎么处理，一个队列还是两个队列

sendMessage 以后 只有自己能接收是为什么

Handler中的时间计算用了哪种API，为什么

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

>now 这里的时间用的是uptimeMillis()表示自系统启动时开始计数，以毫秒为单位。  
返回的是从系统启动到现在这个过程中的处于非休眠期的时间。  
当系统进入深度睡眠(CPU关闭，屏幕显示器不显示，设备等待外部输入)时，或者空闲或其他省电机制的影响，此时时钟停止，但是该时钟不会被时钟调整。

这个方法是大多数间隔时间的基础，例如Thread.sleep(millis)方法、Object.wait(millis)方法、System.nanoTime()都是基于此方法的

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
                        return msg; //**只有这里真正返回实际的Message给Looper去用**
                    }
                } else {
                    // No more messages.
                    nextPollTimeoutMillis = -1;
                }

                /*省略*/
            }
            /*省略*/
        }
    }
```