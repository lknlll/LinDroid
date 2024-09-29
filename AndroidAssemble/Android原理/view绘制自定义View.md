

##### MeasureSpec这个类

https://blog.csdn.net/carson_ho/article/details/94545178

作用：通过宽测量值widthMeasureSpec和高测量值heightMeasureSpec决定View的大小

组成：一个32位int值，高2位代表SpecMode(测量模式)，低30位代表SpecSize( 某种测量模式下的规格大小)。

三种模式：

UNSPECIFIED：父容器不对View有任何限制，要多大有多大。常用于系统内部。

EXACTLY(精确模式)：父视图为子视图指定一个确切的尺寸SpecSize。对应LyaoutParams中的match_parent或具体数值。

AT_MOST(最大模式)：父容器为子视图指定一个最大尺寸SpecSize，View的大小不能大于这个值。对应LayoutParams中的wrap_content。

决定因素：值由子View的布局参数LayoutParams和父容器的MeasureSpec值共同决定。

##### Canvas
https://juejin.cn/post/6844903651551510541