
1.Activity的启动过程（不要回答生命周期）
http://blog.csdn.net/luoshengyang/article/details/6689748

2.Activity的启动模式以及使用场景
（1）manifest设置，（2）startActivity flag
http://blog.csdn.net/CodeEmperor/article/details/50481726
此处延伸：栈(First In Last Out)与队列(First In First Out)的区别

3.Service的两种启动方式
（1）startService()，（2）bindService()
http://www.jianshu.com/p/2fb6eb14fdec

4.Broadcast注册方式与区别
（1）静态注册(minifest)，（2）动态注册
http://www.jianshu.com/p/ea5e233d9f43
此处延伸：什么情况下用动态注册

5.HttpClient与HttpUrlConnection的区别
http://blog.csdn.net/guolin_blog/article/details/12452307
此处延伸：Volley里用的哪种请求方式（2.3前HttpClient，2.3后HttpUrlConnection）

6.http与https的区别
http://blog.csdn.net/whatday/article/details/38147103
此处延伸：https的实现原理

8.进程保活（不死进程）
http://www.jianshu.com/p/63aafe3c12af
此处延伸：进程的优先级是什么（下面这篇文章，都有说）
https://segmentfault.com/a/1190000006251859

9.进程间通信的方式
（1）AIDL，（2）广播，（3）Messenger
AIDL : https://www.jianshu.com/p/a8e43ad5d7d2
https://www.jianshu.com/p/0cca211df63c
Messenger : http://blog.csdn.net/lmj623565791/article/details/47017485
此处延伸：简述Binder ， http://blog.csdn.net/luoshengyang/article/details/6618363/

10.加载大图
http://blog.csdn.net/lmj623565791/article/details/49300989

11.三级缓存（各大图片框架都可以扯到这上面来）
（1）内存缓存，（2）本地缓存，（3）网络
内存：http://blog.csdn.net/guolin_blog/article/details/9526203
本地：http://blog.csdn.net/guolin_blog/article/details/28863651

12.MVP框架（必问）
http://blog.csdn.net/lmj623565791/article/details/46596109
此处延伸：手写mvp例子，与mvc之间的区别，mvp的优势

13.讲解一下Context
http://blog.csdn.net/lmj623565791/article/details/40481055

14.JNI
http://www.jianshu.com/p/aba734d5b5cd
此处延伸：项目中使用JNI的地方，如：核心逻辑，密钥，加密逻辑

15.java虚拟机和Dalvik虚拟机的区别
http://www.jianshu.com/p/923aebd31b65

16.线程sleep和wait有什么区别
http://blog.csdn.net/liuzhenwen/article/details/4202967

18.保存Activity状态
onSaveInstanceState()
http://blog.csdn.net/yuzhiboyi/article/details/7677026

19.WebView与js交互（调用哪些API）
http://blog.csdn.net/cappuccinolau/article/details/8262821/

20.内存泄露检测，内存性能优化
http://blog.csdn.net/guolin_blog/article/details/42238627
Service:只有当任务正在执行的时候才应该让Service运行起来


此处延伸：
（1）内存溢出（OOM）和内存泄露（对象无法被回收）的区别。
（2）引起内存泄露的原因

21.布局优化
http://blog.csdn.net/guolin_blog/article/details/43376527

22.自定义view和动画
以下两个讲解都讲得很透彻，这部分面试官多数不会问很深，要么就给你一个效果让你讲原理。
（1）http://www.gcssloop.com/customview/CustomViewIndex
（2）http://blog.csdn.net/yanbober/article/details/50577855

23.设计模式（单例，工厂，观察者。作用，使用场景）

http://blog.csdn.net/jason0539/article/details/23297037/
此处延伸：Double Check的写法

（仿有道精品课）RxJava+OkHttp+Retrofit+Dagger2+MVP框架(kotlin版本)
https://juejin.im/post/5c6e601cf265da2dc675b69e#comment

编译优化系列
https://juejin.cn/user/4265760848090664/posts

面试
https://juejin.cn/post/7291094611484475450

@author yibaoshan
https://juejin.cn/user/2225067266683095/posts

Activity.finish() 之后 10s 才 onDestroy
https://juejin.cn/post/6898588053451833351

Carson Android
https://carsonho.blog.csdn.net/?type=blog

qingmei2-blogs
https://github.com/qingmei2/blogs

潇风寒月
https://github.com/xfhy/Android-Notes/tree/master



3.快速排序和插入排序

4.个大致有序的数组如何排序，最快时间复杂度

5.如何自定义实现一个FlexLayout

6.RecyclerView的回收复用机制

7.如何实现RecyclerView的局部更新

8.说说对泛型的了解，Retrofit中的泛型是怎么解析的

9.说说对binder机制的了解

10.进程的状态，线程的状态，两者有区别吗

11.synchronized锁住对象的理解

12.Fragment replace生命周期变化

13.TCP和UDP有什么区别？ 讲讲招手挥手过程

15.Dalvik和Hotspot虚拟机了解吗？有什么区别？


6.热修复、插件化都用过，遇到过哪些坑？可以讲讲原理吗

斗鱼一面
1.Java多态的理解

2.HashMap原理


6.说说插件化的原理，资源的插件化id重复如何解决？

7.Recyclerview优化

8.Handler消息机制

9.用过哪些第三方库，OKHTTP原理说一下，热修复原理呢？

10.对屏幕刷新机制的了解，双重缓冲，三重缓冲，黄油模型


12.为什么要用线程池（扯到线程分配资源的过程，和进程分配资源过程的区别）

13.GC内核清理用什么算法，老年代有什么算法？

14.TCP 三次握手四次挥手，第一次和第三次如果没收到回应会怎样？



1.构造方法有哪些？重载与重写的区别？

2.线程有哪些状态？

3.WebView安全问题，做过WebView性能优化吗

4.RecyclerView 缓存结构

5.RecyclerView回收复用机制，回收什么？复用什么？回收到哪里去，复用从哪里拿？

6.Handler休眠是怎样的？epoll的原理是什么？如何实现延时消息，如果移除一个延时消息会解除休眠吗？

7.手势操作ActionCancel后怎么取消

8.熟悉AIDL？支持哪些数据类型？说说oneway的作用？怎么理解单向调用？

9.说说https怎么防止被抓包吧


腾讯（offer）

腾讯技术面
1.介绍一下你们项目的架构

2.Rxjava是怎么实现线程切换的

3.Rxjava自定义操作符

4.ARouter的原理

5.ARouter怎么实现接口调用

6.ARouter怎么实现页面拦截

7.MVP怎么处理内存泄漏

8.OkHttp怎么实现连接池

9.如果让你来实现一个网络框架，你会考虑什么

10.你做过什么性能优化的工作

11.热修复的原理，资源的热修复的原理,会不会有资源冲突的问题

12.ViewPager中嵌套ViewPager怎么处理滑动冲突

13.android源码中有哪些设计模式

14.说说binder机制的原理

15.retrofit怎么做post请求

16.界面优化的一些方法，ConstraintLayout实现三等分,ConstraintLayout动画.

17.synchronize用法，volatile用法，两者的区别和场景

18.做过进程保活吗？

19.App 是如何沙箱化，为什么要这么做？

20.讲讲 bindService 的过程，你当初是怎么优化后台服务进程的？

21.弱网环境你如何做的网络优化？

22.ConcurrentHashMap 的实现原理