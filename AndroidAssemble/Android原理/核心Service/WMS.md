

ViewRootImpl这个类

```

    public ViewRootImpl(Context context, Display display) {
        
        this(context, display, WindowManagerGlobal.getWindowSession(),
                false /* useSfChoreographer */);
    }

```
它初始化的时候得到了一个WindowSession,通过WindowSession可以与WMS进行通信实现一些窗口信息的传递