

activity/fragment与adapter之间通信
https://blog.csdn.net/Jordas_Lee/article/details/103287252

singleTop在栈里和不在栈里

[Activity 的7个生命周期](https://juejin.cn/post/6963543808348782628)



### Activity只调用onPause()，而不调用onStop()的情况

1、Activity上显示了一个Dialog样式的Activity(这里可以扩展为只要Activity上的新Activity没有把原Activity全部挡住，就可以实现只走onPause()而不走onStop())；

Activity的theme属性配置为Dialog样式：android:theme="@android:style/Theme.Dialog"

2、Activity上显示了一个透明样式的Activity；

Activity的theme属性配置为透明样式：android:theme="@android:style/Theme.Translucent"


### 单Activity多Fragment的好处


切换方便,,可以使你能够将activity分离成多个可重用的组件，每个都有它自己的生命周期和UI,
非常灵活,可以轻松得创建动态灵活的UI设计，可以适应于不同的屏幕尺寸。从手机到平板电脑.
为什么不用activity,这个答案是解决Activity间的切换不流畅，轻量切换
为什么不能用view,感觉效果是一样的, 答案:可以拥有自己的startActivityForResult回调.而view就没有了
不用去管view的复杂层级.可以随意组合
application生命周期没有销毁的回调,activity是有生命周期,而fragment的生命周期是依附在activity的,那么.方便统一管理,以及界面多层悬浮问题可以不用写多套.写在activity就可以进行操作


### 生命周期

[Fragment生命周期和状态](https://mp.weixin.qq.com/s/zC-7gr2evtbXghbQbGIhOw)


[Activity和Fragment生命周期](https://mp.weixin.qq.com/s/1RNKKw0vp_iKiecfnrfzPA)


### 切换动画

sunMars