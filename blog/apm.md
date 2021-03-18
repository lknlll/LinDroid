冷启动热启动：栈顶是否是当前进程，

HandlerThread

启动类型的

1.启动时间：总时长、各阶段平均时长   (支持)

实现方式：通过AOP进行三个阶段统计，支持如下：

阶段1 ： Application attachBaseContext Start -> onCreate End (Applicaiton 启动阶段)
阶段2 ： Application onCreate End -> MainActivity(首页) onCreate Start (Application 跳转首页耗时)
阶段3 ： MainActivity(首页) onCreate Start -> onWindowFocusChanged End (首页加载时长)
启动耗时  = (MainActivity onWindowFocusChanged End) - (Application attachBaseContext Start)

2.卡顿监控：主线程卡顿采用了Looper.getMainLooper().setMessageLogging方式来监控主线程处理消息超时时，采样当前堆栈来确认方法耗时情况，如下：

支持：总体、单机型、单系统版本卡顿比率 

3.网络监控： 利用Asm AOP技术Hook 网络请求接口

支持 ：上行数据量、下行数据量、响应时长、 http 错误率（状态码）   响应错误（超时等）

不支持 ：建立连接最慢主机排行、 DNS 最慢区域排行、 DNS 劫持监控、内容劫持监控

4.UI 监控（页面监控）：

支持 ：首次渲染   页面打开 / 渲染时长(activity的contentView的绘制完毕) 、平均帧率、内存增量、消耗流量、 UI 线程阻塞次数   （数据有，展示没有），内存和CPU监控

5.Webview监控：主要监控数据（支持）

totalLoadingTime（页面加载总时间） = loadEventEnd - navigationStart;
networkRequestTime(网络请求耗时) = responseEnd - navigationStart;
domTime（DOM加载时间）= loadEventEnd - responseEnd;
waitingTime（白屏时间即用户等待时间）= domLoading - navigationStart;

6.日志工具：通过打点数据到本地缓存后，定时进行数据的上报，可以根据白名单进行设置上报（支持）

7.电量监控