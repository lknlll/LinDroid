##### 总结

https://github.com/JsonChao/Awesome-Android-Interview

https://github.com/yangchong211/YCBlogs

https://www.jianshu.com/p/375ad14096b3

https://www.jianshu.com/u/921c778fb5e1

210225

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

App launch 过程中切换了哪些进程

多进程啥好处，绑定的进程崩溃是不是会导致App主进程崩溃

annotation 哪几类，都作用在哪些阶段

rxjava 如何实现线程切换的

Handler 如何实现sendMessage 以后 只有自己能接收，target Handler是咋回事

sendMessageDelay 之后的消息是怎么处理，一个队列还是两个队列

开源框架原理说一个熟悉的

FileProvider 为啥要出，解决什么问题，带来哪些好处

ANR 怎么处理，有设备环境看trace文件，没有设备环境什么方案

蓝牙的协议（业务涉及到硬件间通讯的）

Gradle 插件写过啥（upload maven 这种task 不算）

Gradle 打包都做了什么

Manifest 文件有什么作用，如何实现Activity不在manifest中注册还能打开

组件化router怎么实现，阿里Arouter里面有个Json 路由表什么时机生成的

implementation 和 api 的module间穿透性

想同时依赖两个不同版本的support 包 怎么做

彬哥

https://github.com/Mp5A5/Good-Salary

个人复盘

https://www.jianshu.com/p/de220733fdfa

---

##### Java

final 关键字

www.cnblogs.com/dotgua/p/6357951.html

二进制计算

https://www.jianshu.com/p/7118815bddde

ArrayList sort

https://blog.csdn.net/kingdtl/article/details/19554461

---

##### Android

Handler 

https://www.jianshu.com/p/e37e2db2b28e

Handler从发出消息到回到自己

https://www.jianshu.com/p/f7cabfe19720

内存

https://gank.io/post/5e79880393b891c522d3bde6

ANR-WatchDog

https://blog.csdn.net/aaliweipeng/article/details/105340247

audiomanager

https://www.runoob.com/w3cnote/android-tutorial-audiomanager.html

VideoView

https://blog.csdn.net/liangtianmeng/article/details/107619255

下载 Aria

https://github.com/AriaLyy/Aria

获取当前时间与星期几

https://blog.csdn.net/zhangli_/article/details/51382517

WebSocket 

https://blog.csdn.net/sbsujjbcy/article/details/52839540

帧动画

https://blog.csdn.net/huweiliyi/article/details/105669298

Glide 4

https://blog.csdn.net/guolin_blog/article/details/78582548

gradle groovy

https://blog.csdn.net/u012982629/article/details/81121717

埋点

https://github.com/ccj659/JJEvent

libpng

https://blog.csdn.net/qq_21334991/article/details/79035222

http://www.libpng.org/pub/png/libpng.html

Assets目录下 文件或文件夹的复制

https://blog.csdn.net/SharkMarine/article/details/25784423

https://blog.csdn.net/weixin_40391500/article/details/79063678

SystemProperties手机系统主要信息

https://www.jianshu.com/p/6d3e883d0448

AsyncTaskLoader

局域网设备发现与通信

https://blog.csdn.net/gogo_wei/article/details/83118329

OpenGLES系列

https://blog.csdn.net/junzia/column/info/15997

设置CPU核心数和频率

https://www.jianshu.com/p/111f79ab2106

UVCCamera

---

###### JNI 

查看手机CPU框架

https://blog.csdn.net/chen1234520nnn/article/details/76021026

