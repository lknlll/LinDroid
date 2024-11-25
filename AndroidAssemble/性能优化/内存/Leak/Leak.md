
内存泄漏


1、定义

Android系统虚拟机的垃圾回收是通过虚拟机GC机制来实现的。GC会选择一些还存活的对象作为内存遍历的根节点GC Roots，通过对GC Roots的可达性来判断是否需要回收。内存泄漏就是在当前应用周期内不再使用的对象被GC Roots引用，导致不能回收，使实际可使用内存变小。

2、使用MAT来查找内存泄漏

MAT工具可以帮助开发者定位导致内存泄漏的对象，以及发现大的内存对象，然后解决内存泄漏并通过优化内存对象，以达到减少内存消耗的目的。

使用步骤

1、在https://eclipse.org/mat/downloads.php下载MAT客户端。

2、从Android Studio进入Profile的Memory视图，选择需要分析的应用进程，对应用进行怀疑有内存问题的操作，结束操作后，主动GC几次，最后export dump文件。

3、因为Android Studio保存的是Android Dalvik/ART格式的.hprof文件，所以需要转换成J2SE HPROF格式才能被MAT识别和分析。Android SDK自带了一个转换工具在SDK的platform-tools下，其中转换语句为：

./hprof-conv file.hprof converted.hprof
复制代码

4、通过MAT打开转换后的HPROF文件。

MAT视图

在MAT窗口上，OverView是一个总体概览，显示总体的内存消耗情况和疑似问题。MAT提供了多种分析维度，其中Histogram、Dominator Tree、Top Consumers和Leak Suspects的分析维度是不同的。下面分别介绍下它们，如下所示：

1、Histogram

列出内存中的所有实例类型对象和其个数以及大小，并在顶部的regex区域支持正则表达式查找。

2、Dominator Tree

列出最大的对象及其依赖存活的Object。相比Histogram，能更方便地看出引用关系。

3、Top Consumers

通过图像列出最大的Object。

4、Leak Suspects

通过MAT自动分析内存泄漏的原因和泄漏的一份总体报告。

分析内存最常用的是Histogram和Dominator Tree这两个视图，视图中一共有四列：


Class Name：类名。

Objects：对象实例个数。

Shallow Heap：对象自身占用的内存大小，不包括它引用的对象。非数组的常规对象的Shallow Heap Size由其成员变量的数量和类型决定，数组的Shallow Heap Size由数组元素的类型（对象类型、基本类型）和数组长度决定。真正的内存都在堆上，看起来是一堆原生的byte[]、char[]、int[]，对象本身的内存都很小。因此Shallow Heap对分析内存泄漏意义不是很大。

Retained Heap：是当前对象大小与当前对象可直接或间接引用到的对象的大小总和，包括被递归释放的。即：Retained Size就是当前对象被GC后，从Heap上总共能释放掉的内存大小。


查找内存泄漏具体位置

常规方式


1、按照包名类型分类进行实例筛选或直接使用顶部Regex选取特定实例。

2、右击选中被怀疑的实例对象，选择Merge Shortest Paths to GC Root->exclude all phantom/weak/soft etc references。(显示GC Roots最短路径的强引用)

3、分析引用链或通过代码逻辑找出原因。


还有一种更快速的方法就是对比泄漏前后的HPROF数据：


1、在两个HPROF文件中，把Histogram或者Dominator Tree增加到Compare Basket。

2、在Compare Basket中单击 ! ，生成对比结果视图。这样就可以对比相同的对象在不同阶段的对象实例个数和内存占用大小，如明显只需要一个实例的对象，或者不应该增加的对象实例个数却增加了，说明发生了内存泄漏，就需要去代码中定位具体的原因并解决。


需要注意的是，如果目标不太明确，可以直接定位RetainedHeap最大的Object，通过Select incoming references查看引用链，定位到可疑的对象，然后通过Path to GC Roots分析引用链。

此外，我们知道，当Hash集合中过多的对象返回相同的Hash值时，会严重影响性能，这时可以用 Map Collision Ratio 查找导致Hash集合的碰撞率较高的罪魁祸首。

高效方式

在本人平时的项目开发中，一般会使用如下几种方式来快速对指定页面进行内存泄漏的检测（也称为运行时内存分析优化）：



1、shell命令 + LeakCanary + MAT：运行程序，所有功能跑一遍，确保没有改出问题，完全退出程序，手动触发GC，然后使用adb shell dumpsys meminfo packagename -d命令查看退出界面后Objects下的Views和Activities数目是否为0，如果不是则通过LeakCanary检查可能存在内存泄露的地方，最后通过MAT分析，如此反复，改善满意为止。



2、Profile MEMORY：运行程序，对每一个页面进行内存分析检查。首先，反复打开关闭页面5次，然后收到GC（点击Profile MEMORY左上角的垃圾桶图标），如果此时total内存还没有恢复到之前的数值，则可能发生了内存泄露。此时，再点击Profile MEMORY左上角的垃圾桶图标旁的heap dump按钮查看当前的内存堆栈情况，选择按包名查找，找到当前测试的Activity，如果引用了多个实例，则表明发生了内存泄露。



3、从首页开始用依次dump出每个页面的内存快照文件，然后利用MAT的对比功能，找出每个页面相对于上个页面内存里主要增加了哪些东西，做针对性优化。



4、利用Android Memory Profiler实时观察进入每个页面后的内存变化情况，然后对产生的内存较大波峰做分析。



此外，除了运行时内存的分析优化，我们还可以对App的静态内存进行分析与优化。静态内存指的是在伴随着App的整个生命周期一直存在的那部分内存，那我们怎么获取这部分内存快照呢？

首先，确保打开每一个主要页面的主要功能，然后回到首页，进开发者选项去打开"不保留后台活动"。然后，将我们的app退到后台，GC，dump出内存快照。最后，我们就可以将对dump出的内存快照进行分析，看看有哪些地方是可以优化的，比如加载的图片、应用中全局的单例数据配置、静态内存与缓存、埋点数据、内存泄漏等等。

##### 常见内存泄漏场景

对于内存泄漏，其本质可理解为无法回收无用的对象。这里我总结了我在项目中遇到的一些常见的内存泄漏案例（包含解决方案）。

1、资源性对象未关闭

对于资源性对象不再使用时，应该立即调用它的close()函数，将其关闭，然后再置为null。例如Bitmap等资源未关闭会造成内存泄漏，此时我们应该在Activity销毁时及时关闭。

2、注册对象未注销

例如BroadcastReceiver、EventBus未注销造成的内存泄漏，我们应该在Activity销毁时及时注销。

3、类的静态变量持有大数据对象

尽量避免使用静态变量存储数据，特别是大数据对象，建议使用数据库存储。

4、单例造成的内存泄漏

优先使用Application的Context，如需使用Activity的Context，可以在传入Context时使用弱引用进行封装，然后，在使用到的地方从弱引用中获取Context，如果获取不到，则直接return即可。

5、非静态内部类的静态实例

该实例的生命周期和应用一样长，这就导致该静态实例一直持有该Activity的引用，Activity的内存资源不能正常回收。此时，我们可以将该内部类设为静态内部类或将该内部类抽取出来封装成一个单例，如果需要使用Context，尽量使用Application Context，如果需要使用Activity Context，就记得用完后置空让GC可以回收，否则还是会内存泄漏。

6、Handler临时性内存泄漏

Message发出之后存储在MessageQueue中，在Message中存在一个target，它是Handler的一个引用，Message在Queue中存在的时间过长，就会导致Handler无法被回收。如果Handler是非静态的，则会导致Activity或者Service不会被回收。并且消息队列是在一个Looper线程中不断地轮询处理消息，当这个Activity退出时，消息队列中还有未处理的消息或者正在处理的消息，并且消息队列中的Message持有Handler实例的引用，Handler又持有Activity的引用，所以导致该Activity的内存资源无法及时回收，引发内存泄漏。解决方案如下所示：


1、使用一个静态Handler内部类，然后对Handler持有的对象（一般是Activity）使用弱引用，这样在回收时，也可以回收Handler持有的对象。

2、在Activity的Destroy或者Stop时，应该移除消息队列中的消息，避免Looper线程的消息队列中有待处理的消息需要处理。


需要注意的是，AsyncTask内部也是Handler机制，同样存在内存泄漏风险，但其一般是临时性的。对于类似AsyncTask或是线程造成的内存泄漏，我们也可以将AsyncTask和Runnable类独立出来或者使用静态内部类。

7、容器中的对象没清理造成的内存泄漏

在退出程序之前，将集合里的东西clear，然后置为null，再退出程序

8、WebView

WebView都存在内存泄漏的问题，在应用中只要使用一次WebView，内存就不会被释放掉。我们可以为WebView开启一个独立的进程，使用AIDL与应用的主进程进行通信，WebView所在的进程可以根据业务的需要选择合适的时机进行销毁，达到正常释放内存的目的。

9、使用ListView时造成的内存泄漏

在构造Adapter时，使用缓存的convertView。

[常见的内存泄露](https://mp.weixin.qq.com/s/azzM_PEgF5Qu5bvHb4k0Pw)

[广播未取消注册为什么会内存泄漏](https://juejin.cn/post/7394790046715691046)