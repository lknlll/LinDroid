

Hook是一种用于改变API执行结果的技术，能够将系统的API函数执行重定向（应用的触发事件和后台逻辑处理是根据事件流程一步步地向下执行。而Hook的意思，就是在事件传送到终点前截获并监控事件的传输，像个钩子钩上事件一样，并且能够在钩上事件时，处理一些自己特定的事件，例如逆向破解App） 

Android 中的 Hook 机制，大致有两个方式：

    要 root 权限，直接 Hook 系统，可以干掉所有的 App。

    无 root 权限，但是只能 Hook 自身app，对系统其它 App 无能为力。

插桩是以静态的方式修改第三方的代码，也就是从编译阶段，对源代码（中间代码）进行编译，而后重新打包，是静态的篡改； 而Hook则不需要再编译阶段修改第三方的源码或中间代码，是在运行时通过反射的方式修改调用，是一种动态的篡改

Android插件化原理解析——Hook机制之动态代理

https://zhaomenghuan.js.org/blog/android-plugin-framework-proxy-hook.html
http://weishu.me/2016/01/28/understand-plugin-framework-proxy-hook/

android 插桩基本概念
https://blog.csdn.net/fei20121106/article/details/51879047

Android逆向之旅
http://www.520monkey.com/

Android Hook Activity 的几种姿势
https://blog.csdn.net/gdutxiaoxu/article/details/81459910