

###### 图片内存优化



2、内存中如果加载一张500*500的png高清图片.应该是占用多少的内存?

不考虑屏幕比的话：占用内存=500 * 500 * 4 = 1000000B ≈ 0.95MB

考虑屏幕比的的话：占用内存= 宽度像素 x （inTargetDensity / inDensity） x 高度像素 x （inTargetDensity / inDensity）x 一个像素所占的内存字节大小

inDensity表示目标图片的dpi（放在哪个资源文件夹下），inTargetDensity表示目标屏幕的dpi



Bitamp 所占内存大小 = 宽度像素 x （inTargetDensity / inDensity） x 高度像素 x （inTargetDensity / inDensity）x 一个像素所占的内存字节大小

注：这里inDensity表示目标图片的dpi（放在哪个资源文件夹下），inTargetDensity表示目标屏幕的dpi，所以你可以发现inDensity和inTargetDensity会对Bitmap的宽高进行拉伸，进而改变Bitmap占用内存的大小。



在Bitmap里有两个获取内存占用大小的方法。

getByteCount()：API12 加入，代表存储 Bitmap 的像素需要的最少内存。

getAllocationByteCount()：API19 加入，代表在内存中为 Bitmap 分配的内存大小，代替了 getByteCount() 方法。

在不复用 Bitmap 时，getByteCount() 和 getAllocationByteCount 返回的结果是一样的。在通过复用 Bitmap 来解码图片时，那么 getByteCount() 表示新解码图片占用内存的大 小，getAllocationByteCount() 表示被复用 Bitmap 真实占用的内存大小




在Android默认情况下，当图片文件解码成位图时，会被处理成32bit/像素。红色、绿色、蓝色和透明通道各8bit，即使是没有透明通道的图片，如JEPG隔世是没有透明通道的，但然后会处理成32bit位图，这样分配的32bit中的8bit透明通道数据是没有任何用处的，这完全没有必要，并且在这些图片被屏幕渲染之前，它们首先要被作为纹理传送到GPU，这意味着每一张图片会同时占用CPU内存和GPU内存。下面，我总结了减少内存开销的几种常用方式，如下所示：

1、设置位图的规格：当显示小图片或对图片质量要求不高时可以考虑使用RGB_565，用户头像或圆角图片一般可以尝试ARGB_4444。通过设置inPreferredConfig参数来实现不同的位图规格，代码如下所示：

```
BitmapFactory.Options options = new BitmapFactory.Options();
options.inPreferredConfig = Bitmap.Config.RGB_565;
BitmapFactory.decodeStream(is, null, options);
```

2、inSampleSize：位图功能对象中的inSampleSize属性实现了位图的缩放功能，代码如下所示：

```

BitampFactory.Options options = new BitmapFactory.Options();
// 设置为4就是宽和高都变为原来1/4大小的图片
options.inSampleSize = 4;
BitmapFactory.decodeSream(is, null, options);

```

3、inScaled，inDensity和inTargetDensity实现更细的缩放图片：当inScaled设置为true时，系统会按照现有的密度来划分目标密度，代码如下所示：

BitampFactory.Options options = new BitampFactory.Options();
options.inScaled = true;
options.inDensity = srcWidth;
options.inTargetDensity = dstWidth;
BitmapFactory.decodeStream(is, null, options);
复制代码

上述三种方案的缺点：使用了过多的算法，导致图片显示过程需要更多的时间开销，如果图片很多的话，就影响到图片的显示效果。最好的方案是结合这两个方法，达到最佳的性能结合，首先使用inSampleSize处理图片，转换为接近目标的2次幂，然后用inDensity和inTargetDensity生成最终想要的准确大小，因为inSampleSize会减少像素的数量，而基于输出密码的需要对像素重新过滤。但获取资源图片的大小，需要设置位图对象的inJustDecodeBounds值为true，然后继续解码图片文件，这样才能生产图片的宽高数据，并允许继续优化图片。总体的代码如下所示：

BitmapFactory.Options options = new BitampFactory.Options();
options.inJustDecodeBounds = true;
BitmapFactory.decodeStream(is, null, options);
options.inScaled = true;
options.inDensity = options.outWidth;
options.inSampleSize = 4;
Options.inTargetDensity = desWith * options.inSampleSize;
options.inJustDecodeBounds = false;
BitmapFactory.decodeStream(is, null, options);


要选择合适的图片规格（bitmap类型）：通常我们优化Bitmap时，当需要做性能优化或者防止OOM，我们通常会使用RGB_565，因为ALPHA_8只有透明度，显示一般图片没有意义，Bitmap.Config.ARGB_4444显示图片不清楚，Bitmap.Config.ARGB_8888占用内存最多。：

ALPHA_8   每个像素占用1byte内存

ARGB_4444 每个像素占用2byte内存

ARGB_8888 每个像素占用4byte内存（默认）

RGB_565 每个像素占用2byte内存



降低采样率：BitmapFactory.Options 参数inSampleSize的使用，先把options.inJustDecodeBounds设为true，只是去读取图片的大小，在拿到图片的大小之后和要显示的大小做比较通过calculateInSampleSize()函数计算inSampleSize的具体值，得到值之后。options.inJustDecodeBounds设为false读图片资源。

复用内存：即通过软引用(内存不够的时候才会回收掉)，复用内存块，不需要再重新给这个bitmap申请一块新的内存，避免了一次内存的分配和回收，从而改善了运行效率。

使用recycle()方法及时回收内存。
通过源码可以了解到，加载Bitmap到内存里以后，是包含两部分内存区域的。简单的说，一部分是Java部分的，一部分是C部分的。这个Bitmap对象是由Java部分分配的，不用的时候系统就会自动回收了

但是那个对应的C可用的内存区域，虚拟机是不能直接回收的，这个只能调用底层的功能释放。所以需要调用recycle()方法来释放C部分的内存

bitmap.recycle()方法用于回收该Bitmap所占用的内存，接着将bitmap置空，最后使用System.gc()调用一下系统的垃圾回收器进行回收，调用System.gc()并不能保证立即开始进行回收过程，而只是为了加快回收的到来。


压缩图片。



5、inBitmap

可以结合LruCache来实现，在LruCache移除超出cache size的图片时，暂时缓存Bitamp到一个软引用集合，需要创建新的Bitamp时，可以从这个软用用集合中找到最适合重用的Bitmap，来重用它的内存区域。

需要注意，新申请的Bitmap与旧的Bitmap必须有相同的解码格式，并且在Android 4.4之前，只能重用相同大小的Bitamp的内存区域，而Android 4.4之后可以重用任何bitmap的内存区域。

6、图片放置优化

只需要UI提供一套高分辨率的图，图片建议放在drawable-xxhdpi文件夹下，这样在低分辨率设备中图片的大小只是压缩，不会存在内存增大的情况。如若遇到不需缩放的文件，放在drawable-nodpi文件夹下。




##### 大图，如一张30M的大图，如何预防OOM？

参考回答：避免OOM的问题就需要对大图片的加载进行管理，主要通过缩放来减小图片的内存占用。

BitmapFactory提供的加载图片的四类方法（decodeFile、decodeResource、decodeStream、decodeByteArray）都支持BitmapFactory.Options参数，通过inSampleSize参数就可以很方便地对一个图片进行采样缩放

比如一张10241024的高清图片来说。那么它占有的内存为102410244，即4MB，如果inSampleSize为2，那么采样后的图片占用内存只有512512*4,即1MB（注意：根据最新的官方文档指出，inSampleSize的取值应该总是为2的指数，即1、2、4、8等等，如果外界输入不足为2的指数，系统也会默认选择最接近2的指数代替，比如2）

综合考虑。通过采样率即可有效加载图片，流程如下

将BitmapFactory.Options的inJustDecodeBounds参数设为true并加载图片

从BitmapFactory.Options中取出图片的原始宽高信息，它们对应outWidth和outHeight参数

根据采样率的规则并结合目标View的所需大小计算出采样率inSampleSize

将BitmapFactory.Options的inJustDecodeBounds参数设为false，重新加载图片

多图大图加载方案
https://blog.csdn.net/guolin_blog/article/details/9316683


[内存大户Bitmap](https://mp.weixin.qq.com/s/Dx3NiaaXsYRUTvMj4p0mZA)