



##### 如何进行单元测试，如何保证App稳定

参考回答：

要测试Android应用程序，通常会创建以下类型自动单元测试

    本地测试：只在本地机器JVM上运行，以最小化执行时间，这种单元测试不依赖于Android框架，或者即使有依赖，也很方便使用模拟框架来模拟依赖，以达到隔离Android依赖的目的，模拟框架如Google推荐的Mockito；

    Android官网-建立本地单元测试
    https://developer.android.com/training/testing/unit-testing/local-unit-tests.html

    检测测试：真机或模拟器上运行的单元测试，由于需要跑到设备上，比较慢，这些测试可以访问仪器（Android系统）信息，比如被测应用程序的上下文，一般地，依赖不太方便通过模拟框架模拟时采用这种方式；

    Android官网-建立仪表单元测试
    https://developer.android.com/training/testing/unit-testing/instrumented-unit-tests.html

注意：单元测试不适合测试复杂的UI交互事件

推荐文章：Android 单元测试只看这一篇就够了
https://juejin.im/post/5b57e3fbf265da0f47352618

App的稳定主要决定于整体的系统架构设计，同时也不可忽略代码编程的细节规范，正所谓“千里之堤，溃于蚁穴”，一旦考虑不周，看似无关紧要的代码片段可能会带来整体软件系统的崩溃，所以上线之前除了自己本地化测试之外还需要进行Monkey压力测试

少部分面试官可能会延伸，如Gradle自动化测试、机型适配测试等

##### 插件化

插件化是指将 APK 分为宿主和插件的部分。把需要实现的模块或功能当做一个独立的提取出来，在 APP 运行时，我们可以动态的载入或者替换插件部分，减少宿主的规模

宿主： 就是当前运行的APP。

插件： 相对于插件化技术来说，就是要加载运行的apk类文件。

热修复则是从修复bug的角度出发，强调的是在不需要二次安装应用的前提下修复已知的bug

![avatar](https://github.com/lknlll/LinDroid/blob/LinDroid/blog/pic/hotfix.png?raw=true) 

类加载机制

Android中常用的两种类加载器，DexClassLoader和PathClassLoader，它们都继承于BaseDexClassLoader，两者区别在于PathClassLoader只能加载内部存储目录的dex/jar/apk文件。DexClassLoader支持加载指定目录(不限于内部)的dex/jar/apk文件

插件通信：通过给插件apk生成相应的DexClassLoader便可以访问其中的类，可分为单DexClassLoader和多DexClassLoader两种结构。

若使用多ClassLoader机制，主工程引用插件中类需要先通过插件的ClassLoader加载该类再通过反射调用其方法。插件化框架一般会通过统一的入口去管理对各个插件中类的访问，并且做一定的限制。

若使用单ClassLoader机制，主工程则可以直接通过类名去访问插件中的类。该方式有个弊端，若两个不同的插件工程引用了一个库的不同版本，则程序可能会出错。

资源加载

原理在于通过反射将插件apk的路径加入AssetManager中并创建Resource对象加载资源，有两种处理方式：

合并式：addAssetPath时加入所有插件和主工程的路径；由于AssetManager中加入了所有插件和主工程的路径，因此生成的Resource可以同时访问插件和主工程的资源。但是由于主工程和各个插件都是独立编译的，生成的资源id会存在相同的情况，在访问时会产生资源冲突。

独立式：各个插件只添加自己apk路径，各个插件的资源是互相隔离的，不过如果想要实现资源的共享，必须拿到对应的Resource对象。

Android动态加载技术 简单易懂的介绍方式
https://segmentfault.com/a/1190000004062866#articleHeader1

深入理解Android插件化技术
https://yq.aliyun.com/articles/361233?utm_content=m_40296

为什么要做热更新
https://www.cnblogs.com/baiqiantao/p/9160806.html



##### WebView的性能优化 ?



参考回答：

一个加载网页的过程中，native、网络、后端处理、CPU都会参与，各自都有必要的工作和依赖关系；让他们相互并行处理而不是相互阻塞才可以让网页加载更快：

WebView初始化慢，可以在初始化同时先请求数据，让后端和网络不要闲着。

常用 JS 本地化及延迟加载，使用第三方浏览内核

后端处理慢，可以让服务器分trunk输出，在后端计算的同时前端也加载网络静态资源。

脚本执行慢，就让脚本在最后运行，不阻塞页面解析。

同时，合理的预加载、预缓存可以让加载速度的瓶颈更小。

WebView初始化慢，就随时初始化好一个WebView待用。

DNS和链接慢，想办法复用客户端使用的域名和链接。




推荐文章：WebView性能、体验分析与优化
https://tech.meituan.com/2017/06/09/webviewperf.html


Android WebView常见的安全漏洞和解决方案
https://juejin.cn/post/6844904151256678408

进程调度
https://paul.pub/android-process-priority/


跨端、动态化、插件化热更、Framework、音视频、逆向加固、单元测试等