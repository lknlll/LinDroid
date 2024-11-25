
##### JNI
https://www.jianshu.com/p/3dab1be3b9a4

加载NDK库 ？如何在JNI中注册Native函数，有几种注册方法 ？



    public class JniTest{
        //加载NDK库 
        static{
            System.loadLirary("jni-test");
        }
    }


注册JNI函数的两种方法

静态方法

动态注册

注册JNI函数的两种方式
https://blog.csdn.net/wwj_748/article/details/52347341


##### 相关应用

加密处理、影音方面、图形图像处理

ffmpeg
https://www.jianshu.com/p/411761bd5f5b



##### 内存回收

###### 知识准备

c++中的内存分区：  
栈，编译器需要时自动分配，不需要时自动清除的变量存储区，通常存放局部变量，函数参数等。连续的地址。  
堆，new分配的内存块，需手动释放，一般new 的东西用完后需要手动delete。地址不连续，空闲地址为链表
自由存储区，malloc分配的，类似堆，用完free手动释放。

###### 一维数组分配与回收
方式一 堆中创建，使用new 创建，delete 释放；
```
int *ary=new double[num];
delete [] ary;
```

方式二 栈中创建，也叫静态创建，自动释放，但空间有限；

```int a[5]={1,2,3,4,5};```

###### 需要手动释放的JNI类型

jstring 和 char*  
jobject，jobjectArray，jclass ，jmethodID  
jbyteArray  
GetByteArrayElements  
NewGlobalRef

###### so动态加载
[so 动态加载](https://juejin.cn/post/7089693552493461535)