
Java 内存分配模型

JVM 将整个内存划分为了几块，分别如下所示：
https://blog.csdn.net/sihai12345/article/details/109465925

1）、方法区：存储类信息、常量、静态变量等。=> 所有线程共享

2）、虚拟机栈：存储局部变量表、操作数栈等。

3）、本地方法栈：不同与虚拟机栈为 Java 方法服务、它是为 Native 方法服务的。

4）、堆：内存最大的区域，每一个对象实际分配内存都是在堆上进行分配的，，而在虚拟机栈中分配的只是引用，这些引用会指向堆中真正存储的对象。此外，堆也是垃圾回收器（GC）所主要作用的区域，并且，内存泄漏也都是发生在这个区域。=> 所有线程共享

5）、程序计数器：存储当前线程执行目标方法执行到了第几行。



Android 5.0以下，使用的是Dalvik虚拟机，5.0及以上，则使用的是ART虚拟机。


###### Android 内存分配模型

在Android系统中，堆实际上就是一块匿名共享内存。Android虚拟机仅仅只是把它封装成一个 mSpace，由底层C库来管理，并且仍然使用libc提供的函数malloc和free来分配和释放内存。

大多数静态数据会被映射到一个共享的进程中。常见的静态数据包括Dalvik Code、app resources、so文件等等。

在大多数情况下，Android通过显示分配共享内存区域（如Ashmem或者Gralloc）来实现动态RAM区域能够在不同进程之间共享的机制。例如，Window Surface在App和Screen Compositor之间使用共享的内存，Cursor Buffers在Content Provider和Clients之间共享内存。

上面说过，对于Android Runtime有两种虚拟机，Dalvik 和 ART，它们分配的内存区域块是不同的，下面我们就来简单了解下。

Dalvik


Linear Alloc

Zygote Space

Alloc Space


ART


Non Moving Space

Zygote Space

Alloc Space

Image Space

Large Obj Space


不管是Dalvik还是ART，运行时堆都分为 LinearAlloc（类似于ART的Non Moving Space）、Zygote Space 和 Alloc Space。Dalvik中的Linear Alloc是一个线性内存空间，是一个只读区域，主要用来存储虚拟机中的类，因为类加载后只需要只读的属性，并且不会改变它。把这些只读属性以及在整个进程的生命周期都不能结束的永久数据放到线性分配器中管理，能很好地减少堆混乱和GC扫描，提升内存管理的性能。Zygote Space在Zygote进程和应用程序进程之间共享，Allocation Space则是每个进程独占。Android系统的第一个虚拟机由Zygote进程创建并且只有一个Zygote Space。但是当Zygote进程在fork第一个应用程序进程之前，会将已经使用的那部分堆内存划分为一部分，还没有使用的堆内存划分为另一部分，也就是Allocation Space。但无论是应用程序进程，还是Zygote进程，当他们需要分配对象时，都是在各自的Allocation Space堆上进行。

当在ART运行时，还有另外两个区块，即 ImageSpace和Large Object Space。


Image Space：存放一些预加载类，类似于Dalvik中的Linear Alloc。与Zygote Space一样，在Zygote进程和应用程序进程之间共享。

Large Object Space：离散地址的集合，分配一些大对象，用于提高GC的管理效率和整体性能。


注意：Image Space的对象只创建一次，而Zygote Space的对象需要在系统每次启动时，根据运行情况都重新创建一遍。

##### Dalvik 与 ART 区别


1）、Dalivk 仅固定一种回收算法。

2）、ART 回收算法可运行期选择。

3）、ART 具备内存整理能力，减少内存空洞。
 

##### Android系统的内存管理

https://blog.csdn.net/sz_chrome/article/details/106862916


##### 不同步骤运行在JVM什么区

[Java代码怎样运行的](https://juejin.cn/post/7107594538427875335)