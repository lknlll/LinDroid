ViewStub

[ViewStub Android Developer](https://developer.android.com/reference/android/view/ViewStub.html)

View即使GONE，仍耗费资源，仍会被父窗体绘制，仍会创建对象，仍会被实例化，仍会被设置属性  
lazily inflate layout resources, zero-sized;

懒加载，尺寸为0, 在onMeasure()中把宽高都直接指定为0，保证了其大小为0

setWillNotDraw(true)使其不进行绘制并且把draw()实现为空方法，这些都保证了ViewStub在加载的时候并不会进行实际的绘制工作

The ViewStub replaces itself in its parent with the inflated View or Views when made visible or inflate() invoked;

显示性变为可见，或者调用inflate()时，ViewStub就会被填充起来的布局替换掉，并返回填充起来的View；

在xml 中定义ViewStub 节点时，内部不能包含其他节点，也就是说，ViewStub 是一个自闭合节点，如果一个布局/view如果想通过ViewStub显示，只能定义在单独的xml 文件中

android:inflatedId="@+id/iv_VsContent" 标识引用的布局ID
android:layout="@layout/viewstub_content" 标识引用的布局

inflate() 方法只能被调用一次，如果再次调用会报异常信息 ViewStub must have a non-null ViewGroup viewParent，所以调用处最好try catch

当ViewStub 调用inflate() 将其引用的 布局/view 展示出来之后，ViewStub本身就会从视图树中被移除，此时viewStub 就获取不到他的 父布局， 而 inflate() 方法中，上来就需要获取它的父布局，然后根据父布局是否为空再去执行具体的填充逻辑，如果为空就报错，所以，inflate() 之后如果还想再次显示ViewStub 引用的布局/view 就需要 在调用inflate() 的时候try catch，当 catch 到异常的时候，调用setVisibility()设置viewStub 的View.Visible即可。

ViewStub类中inflate() 的具体逻辑如下：
```
public View inflate() {
    final ViewParent viewParent = getParent();

    if (viewParent != null && viewParent instanceof ViewGroup) {
        if (mLayoutResource != 0) {
            final ViewGroup parent = (ViewGroup) viewParent;
            final LayoutInflater factory;
            if (mInflater != null) {
                factory = mInflater;
            } else {
                factory = LayoutInflater.from(mContext);
            }
            final View view = factory.inflate(mLayoutResource, parent,
                    false);

            if (mInflatedId != NO_ID) {
                view.setId(mInflatedId);
            }

            final int index = parent.indexOfChild(this);
            parent.removeViewInLayout(this);

            final ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams != null) {
                parent.addView(view, index, layoutParams);
            } else {
                parent.addView(view, index);
            }

            mInflatedViewRef = new WeakReference<View>(view);

            if (mInflateListener != null) {
                mInflateListener.onInflate(this, view);
            }

            return view;
        } else {
            throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        }
    } else {
        throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
    }
}
```

setVisibility()的逻辑：如果inflate()已经被调用过就直接更新控件可见性，否则更新可见性并调用inflate()加载真正的布局资源，渲染成 View 对象