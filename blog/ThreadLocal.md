ThreadLocal 

线程本地变量，

```
class ThreadLocalTest {
    //会出现内存泄漏的问题，下文会描述
    private static ThreadLocal<String> mThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        mThreadLocal.set("线程main");
        new Thread(new A()).start();
        new Thread(new B()).start();
        System.out.println(mThreadLocal.get());
    }

    static class A implements Runnable {

        @Override
        public void run() {
            mThreadLocal.set("线程A");
            System.out.println(mThreadLocal.get());
        }
    }

    static class B implements Runnable {

        @Override
        public void run() {
            mThreadLocal.set("线程B");
            System.out.println(mThreadLocal.get());
        }
    }
}
```

这段代码控制台输出什么？

main  
线程A  
线程B  

原理：

ThreadLocal提供线程局部变量。这些变量不同于它们的正常变量，即每一个线程访问自身的局部变量时，都有它自己的，独立初始化的副本。该变量通常是与线程关联的私有静态字段，列如用于ID或事物ID。

![avatar](https://github.com/lknlll/LinDroid/blob/LinDroid/blog/pic/ThreadLocalIntro.png) 

ThreadLocal把一个对象保存在指定的线程中，也就是创建线程局部变量；作用域是当前线程；对象保存后，只能在指定线程中获取保存的数据，对于其他线程来说则无法获取到数据。

Android系统在 Handler 机制中使用了它来保证每一个线程中都有一个独立的 Looper 对象

Looper源码注释中的示例如下

```
class LooperThread extends Thread {
    public Handler mHandler;
    public void run() {
        Looper.prepare();
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                // process incoming messages here
            }
        };
        Looper.loop();
    }
}
```

Looper中有一个static final 的sThreadLocal

```
    // sThreadLocal.get() will return null unless you've called prepare().
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();

```

在调用Looper.prepare的时候new 一个Looper对象set给sThreadLocal

```
    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }
```

这个set和HashMap set不一样  
方法内部会获取当前线程中的ThreadLocalMap，  
获取后进行判断，如果不为空，就调用ThreadLocalMap的set方法, 其中key为当前ThreadLocal对象
也就是ThreadLocal中所有的数据操作都与线程中的ThreadLocalMap有关

```
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);//getMap方法是获取的当前Thread t的ThreadLocalMap
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
```

ThreadLocalMap是为了维护线程私有值创建的自定义哈希映射。  
其中线程的私有数据都是非常大且使用寿命长的数据  

变量存本地的好处？

第一是为了把常用的数据放入线程中提高了访问的速度，  
第二是如果数据是非常大的，避免了该数据频繁的创建，不仅解决了存储空间的问题，也减少了不必要的IO消耗

相关，ThreadLocalMap内部，内存泄漏  
https://blog.csdn.net/qq_34664695/article/details/107386891

https://www.jianshu.com/p/2a34d30806d4