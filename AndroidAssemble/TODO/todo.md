
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

7.手写算法（选择冒泡必须要会）
http://www.jianshu.com/p/ae97c3ceea8d

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
一般说自己会的就ok，不要只记得名字就一轮嘴说出来，不然有你好受。
http://blog.csdn.net/jason0539/article/details/23297037/
此处延伸：Double Check的写法被要求写出来。

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