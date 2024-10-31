# <center>Activity绘制流程-WindowViewRootImpl</center>

[TOC]

#### Activity的层级及参于绘制的各角色关系

![avatar](https://github.com/lknlll/LinDroid/blob/LinDroid/blog/pic/view_activity.png?raw=true)

- PhoneWindow ：该类继承于 Window 类，是 Window 类的具体实现，即我们可以通过该类具体去绘制窗口，该类内部包含了一个 DecorView 对象，该 DectorView 对象是所有应用窗口的根 View。每一个 activity 都会有拥有一个 PhoneWindow

- DecorView 是一个应用窗口的根容器，它本质上是一个 FrameLayout。DecorView 有唯一一个子 View，它是一个垂直 LinearLayout，包含两个子元素，一个是 TitleView（ ActionBar 的容器），另一个是 ContentView（窗口内容的容器）。

    - ContentView ：是一个 FrameLayout（android.R.id.content)，我们平常用的 setContentView 就是设置它的子 View 。

- WindowManager : 是一个接口，里面常用的方法有：添加View，更新View和删除View。主要是用来管理 Window 的。WindowManager 具体的实现类是WindowManagerImpl。最终，WindowManagerImpl 会将业务交给 WindowManagerGlobal 来处理。

- WindowManagerService (WMS) ： 负责管理各 app 窗口的创建，更新，删除， 显示顺序。运行在 system_server 进程。

- ViewRootImpl ：拥有 DecorView 的实例，通过该实例来控制 DecorView 绘制。ViewRootImpl 的一个内部类 W，实现了 IWindow 接口，IWindow 接口是供 WMS 使用的，WSM 通过调用 IWindow 一些方法，通过 Binder 通信的方式，最后执行到了 W 中对应的方法中。同样的，ViewRootImpl 通过 IWindowSession 来调用 WMS 的 Session 一些方法。Session 类继承自 IWindowSession.Stub，每一个应用进程都有一个唯一的 Session 对象与 WMS 通信。


#### Window，ViewRootImpl，DecorView的关系

一个 Activity包含一个Window，Window是一个抽象基类，是 Activity 和整个 View 系统交互的接口，只有一个子类实现类PhoneWindow，提供了一系列窗口的方法，比如设置背景，标题等。

一个PhoneWindow 对应一个 DecorView 跟 一个 ViewRootImpl，DecorView 是ViewTree 里面的顶层布局，是继承于FrameLayout，包含两个子View，一个id=statusBarBackground 的 View 和 LineaLayout，LineaLayout 里面包含 title 跟 content，title就是平时用的TitleBar或者ActionBar，content也是 FrameLayout，activity通过 setContentView()加载布局的时候加载到这个View上。

ViewRootImpl 实现了建立 DecorView 和 Window 之间的联系。

#### Activity启动过程对View的发起

##### 创建PhoneWindow

Activity启动时在此创建实例：ActivityThread.performLaunchActivity，创建Activity后进行attach进行一系列绑定

    //ActivityThread.java

    private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
         Activity activity = null;
         ...
         java.lang.ClassLoader cl = appContext.getClassLoader();
         activity = mInstrumentation.newActivity(cl, component.getClassName(), r.intent);
         Application app = r.packageInfo.makeApplication(false, mInstrumentation);
         ...
         activity.attach(appContext, this, getInstrumentation(), r.token,
                        r.ident, app, r.intent, r.activityInfo, title, r.parent,
                        r.embeddedID, r.lastNonConfigurationInstances, config,
                        r.referrer, r.voiceInteractor, window, r.configCallback,
                        r.assistToken);
    }



Activity 的 attach() 方法中，会创建一个 PhoneWindow 对象。PhoneWindow 是 Window 的一个子类，它负责管理应用程序窗口的外观和行为，PhoneWindow被创建并赋值给 Activity 的成员变量mWindow，紧接着会为PhoneWindow设置一个WindowManager

```
//Activity
final void attach(Context context, ActivityThread aThread,
        Instrumentation instr, IBinder token, int ident,
        Application application, Intent intent, ActivityInfo info,
        CharSequence title, Activity parent, String id,
        NonConfigurationInstances lastNonConfigurationInstances,
        Configuration config, String referrer, IVoiceInteractor voiceInteractor,
        Window window, ActivityConfigCallback activityConfigCallback, IBinder assistToken,
        IBinder shareableActivityToken) {
    attachBaseContext(context);

    mFragments.attachHost(null /*parent*/);
    mActivityInfo = info;

    //PhoneWindow实例化Window
    mWindow = new PhoneWindow(this, window, activityConfigCallback);
    mWindow.setWindowControllerCallback(mWindowControllerCallback);
    
    mWindow.setCallback(this);
    
    mWindow.setOnWindowDismissedCallback(this);
    mWindow.getLayoutInflater().setPrivateFactory(this);
    //....
    //为PhoneWindow设置WindowManager
    mWindow.setWindowManager(
        (WindowManager)context.getSystemService(Context.WINDOW_SERVICE),
        mToken, mComponent.flattenToString(),
        (info.flags & ActivityInfo.FLAG_HARDWARE_ACCELERATED) != 0);
        
    mWindowManager = mWindow.getWindowManager();
}
```
Activity本身实现了Window.Callback接口

```
public class Activity extends ContextThemeWrapper
        implements LayoutInflater.Factory2,
        Window.Callback, ... {

```

attach()中 `mWindow.setCallback(this);` 将activity赋值给window的callback，供后续使用



##### 初始化DecorView

在Activity onCreate()调用setContentView()，如果是AppCompatActivity，会转发到AppCompatDelegate的setContentView()，AppCompatDelegate是一组abstract接口，它的setContentView()会交由AppCompatDelegateImpl具体实现，会进行以下几个关键步骤：


    ensureSubDecor();
        //获取Window并将初始化过的DecorView绑定给Window然后返回
        createSubDecor();
            //获取AppCompatDelegateImpl的Window对象mWindow
            attachToWindow(((Activity) mHost).getWindow());
            //交由Window的具体实现PhoneWindow获取DecorView
            mWindow.getDecorView();
                //PhoneWindow中具体对DecorView进行操作
                installDecor();
            //根据用户选择的主题来设置一些显示特性，包括标题，actionbar 等。
            //根据不同特性来初始化 subDecor；对 subDecor 内部的子 View 进行初始化。
            // Now set the Window's content view with the decor
            mWindow.setContentView(subDecor);

在初始化DecorView之前，先要经由以上流程确保Window的正确性

    //PhoneWindow
    private void installDecor() {
        mForceDecorInstall = false;
        if (mDecor == null) {
            //在内部根据可用的Context和PhoneWindow.this构造生成DecorView
            //new DecorView(Context context, int featureId, PhoneWindow window,
            WindowManager.LayoutParams params)
            mDecor = generateDecor(-1);
            mDecor.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            mDecor.setIsRootNamespace(true);
            if (!mInvalidatePanelMenuPosted && mInvalidatePanelMenuFeatures != 0) {
                mDecor.postOnAnimation(mInvalidatePanelMenuRunnable);
            }
        } else {
            // 这样 DecorView 就持有了window
            mDecor.setWindow(this);
        }
      //......
    }

以上体现了DecorView和Window的关系：

**DecorView中保存了一份Window的引用**

##### DecorView添加到WindowManager中

在 Activity 的 onResume() 方法之后，会将 DecorView 添加到 WindowManager 中。这将导致 DecorView 显示在屏幕上。

触发点是在，ActivityThread中的handleResumeActivity()方法

```

@Override
public void handleResumeActivity(ActivityClientRecord r, boolean finalStateRequest,
        boolean isForward, boolean shouldSendCompatFakeFocus, String reason) {
    //...

    // 执行Activity onResume
    if (!performResumeActivity(r, finalStateRequest, reason)) {
        return;
    }

    //...

    if (r.window == null && !a.mFinished && willBeVisible) {
        // PhoneWindow
        r.window = r.activity.getWindow();
        View decor = r.window.getDecorView();
        decor.setVisibility(View.INVISIBLE);
        ViewManager wm = a.getWindowManager();
        WindowManager.LayoutParams l = r.window.getAttributes();
        a.mDecor = decor;
        //...

        if (a.mVisibleFromClient) {
            if (!a.mWindowAdded) {
                a.mWindowAdded = true;

                // 添加到WindowManager中，并与wms建立双向通信
                wm.addView(decor, l);
            } else {
                a.onWindowAttributesChanged(l);
            }
        }
    } else if (!willBeVisible) {
        if (localLOGV) Slog.v(TAG, "Launch " + r + " mStartedActivity set");
        r.hideForNow = true;
    }

    //...
}
```

> 可以看到DecorView添加是在onResume之后，这也就是在onCreate与onResume的时候不能直接拿到View的宽高的原因。

这里的做法是：获取到 DecorView，先设置不可见，然后通过 wm.addView(decor, l) 将 view 添加到 WindowManager；

在某些情况下，比如此时点击了输入框调起了键盘，就会调用 wm.updateViewLayout(decor, l) 来更新 View 的布局。

这些做完以后，会调用 activity 的  makeVisible ，让视图可见。如果此时 DecorView 没有添加到 WindowManager，那么会添加

###### addView 的逻辑，ViewRootImpl的实例化

WindowManager 的实现类是 WindowManagerImpl，而它则是通过 WindowManagerGlobal 代理实现 addView，在其内部进行了**ViewRootImpl的创建**

```
        // WindowManagerGlobal  
        public void addView(View view, ViewGroup.LayoutParams params,
            Display display, Window parentWindow) {
           // ......
    
            root = new ViewRootImpl(view.getContext(), display);
            view.setLayoutParams(wparams);

            mViews.add(view);
            mRoots.add(root);
            mParams.add(wparams);
           // do this last because it fires off messages to start doing things
            try {
                root.setView(view, wparams, panelParentView);
            } catch (RuntimeException e) {
                // BadTokenException or InvalidDisplayException, clean up.
                if (index >= 0) {
                    removeViewLocked(index, true);
                }
                throw e;
            } 
        }
```
addView()实例化了 ViewRootImpl 。同时调用 ViewRootImpl 的 setView 方法来发起绘制。此外这里还保存了 DecorView ，Params，以及 ViewRootImpl 的实例

##### ViewRootImpl绘制

setView中主要是通过requestLayout进行绘制

    //ViewRootImpl
    public void setView(View view, WindowManager.LayoutParams attrs, View panelParentView,
            int userId) {
        //持有了 DecorView
        mView = view;
        //...
        requestLayout()
        //...
        //将ViewRootImpl.this设置为DecorView的andoid.View.ViewParent
        view.assignParent(this);
    }
    @Override
    public void requestLayout() {
        if (!mHandlingLayoutInLayoutRequest) {
            // 主线程判断
            checkThread();
            mLayoutRequested = true;
            // 等待垂直刷新信号量的到来，触发分发绘制流程
            scheduleTraversals();
        }
    }
    


    void scheduleTraversals() {
        if (!mTraversalScheduled) {
            mTraversalScheduled = true;
            mTraversalBarrier = mHandler.getLooper().getQueue().postSyncBarrier();
            mChoreographer.postCallback(
                    Choreographer.CALLBACK_TRAVERSAL, mTraversalRunnable, null);
            if (!mUnbufferedInputDispatch) {
                scheduleConsumeBatchedInput();
            }
            notifyRendererOfFramePending();
            pokeDrawLockIfNeeded();
        }
    }

注意，给DecorView设置的ViewParent是什么？

ViewParent是一组接口，它代表了一个视图的父容器，即视图在布局中的直接父控件，每个视图都有一个父容器，通过getParent()方法可以获得它的父容器，ViewParent中提供了与子视图相关的方法，如添加、删除或更新子视图等操作。所有的ViewGroup都是ViewParent的直接子类，它实现了ViewParent的这些方法，以便管理它们的子视图。ViewRootImpl并不是一个真正的 View，只是继承了 ViewParent 接口，用来掌管 View 的各种事件，包括 requestLayout、invalidate、dispatchInputEvent 等等。

mTraversalBarrier : Handler 的同步屏障。它的作用是可以拦截 Looper 对同步消息的获取和分发，加入同步屏障之后，Looper 只会获取和处理异步消息，如果没有异步消息那么就会进入阻塞状态。也就是说，对 View 绘制渲染的处理操作可以优先处理（设置为异步消息）。

mChoreographer: 编舞者。统一动画、输入和绘制时机。

mTraversalRunnable ：TraversalRunnable 的实例，是一个Runnable，最终肯定会调用其 run 方法：

    final class TraversalRunnable implements Runnable {
        @Override
        public void run() {
            doTraversal();
        }
    }

doTraversal开始绘制后，该方法内部会调用 performTraversals 进行绘制




Todo

https://mp.weixin.qq.com/s/ZqVFaEnKLKuqYzn-nacA2A
https://mp.weixin.qq.com/s/rE9K4yhqtwP8fX3OCB47nQ
https://mp.weixin.qq.com/s/FjINaJTZ4f2mI4H8Xawqjw
https://mp.weixin.qq.com/s/q1H_u9zGLw8SFg09YWk4VQ
https://mp.weixin.qq.com/s/TMm8ZJBU-VjtcRRNwVxuEQ
https://mp.weixin.qq.com/s/iQlayNdMvGmroMpAXbQ4Iw
https://mp.weixin.qq.com/s/QZ0FY2nSa5XyLCL1IUdkdw

理解Window机制
https://maimai.cn/article/detail?fid=1634848295&efid=6fuxUYS8XEdc1J0Ru8gc9g

绘制流程
https://juejin.cn/post/6913743020244336653

View绘制13问13答
https://mp.weixin.qq.com/s/0mGB1Zv2ZKLANqs4AWiNsQ