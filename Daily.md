
**ADB**

adb -s 多设备选择设备  

adb shell  
不断输出各进程CPU 占用情况  
top -m 10 -s cpu  
查看CPU  
cat /proc/cpuinfo  
在焦点位置输入XXX  
input text XXX

adb命令长时间无响应且ctrl+c不能退出可以用  
\>exit

adb logcat > /Users/xxx/test/test.log  
保存log到文件，文件不存在将被创建

adb shell am start com.android.settings/com.android.settings.Settings 打开原生系统Settings

小米再次安装App失败，报错
Error type 3  
Error: Activity class {xxx/xxx.MainActivity} does not exist.

adb uninstall packageName 后再安装，可能是卸载不彻底

无线调试：先连线，确保android设备和电脑在一个网内，查看android设备IP（假设192.168.1.199）
adb tcpip 5555 （端口号）  
adb connect 192.168.1.199:5555

I/chatty: uid=10322(com.example.lindroidcode) identical 1 line  
app considered 'chatty' by logcat (more than 5 lines per second), logs of your app will be collapsed.

**MAC**  
Command+shift+. 显隐/隐藏  
Command+→  行尾  
Command+Shift+N 新建文件夹

sudo vim /etc/hosts，然后输入电脑的密码进入host文件，按 i 键进入编辑状态，修改host。然后，ESC退出编辑状态，输入 :wq 保存并退出vim

Chrome CMD + SHIFT + P 弹出命令框 后输入 full 将整个网页另存为图片

**GIT**  
Git config http.postBuffer 524288000

只clone单独分支且只拉取一次commit 
git clone -b LinDroid --depth 1  https://github.com/lknlll/LinDroid.git
可以避免git目录下的pack 文件过大

git 设置本地忽略必须保证 git 的远程仓库分支上没有这个要忽略的文件。如果远程分支上存在这个文件，本地再设置 ignore，将不起作用。
需先push 该文件的删除，再设置gitignore

**社区**  
[掘金](https://juejin.im/)

**Maven**  
[阿里云镜像](https://maven.aliyun.com)

**工具**  
[DNS查询](http://tool.chinaz.com/dns/)

[MarkDown](https://www.typora.io/)

**MarkDown**  
换行：两空格一个回车 或者HTML的 \<br>  
超链接：\[链接文字\]\(链接地址\)  
转义：当我们想在 Markdown 文件中显示一些标记符号，可以使用\进行转义  
下标（符号方式）：θ~1~  上标：θ^2^  
下标（标签方式）：θ<sub>1</sub>  上标 ：θ<sup>2</sup>  
序号：数字 加点 加空格  
分割线
1 \---
2 \___
3 \***

插入程序代码的方式：单行代码前后使用反引号 `，或多行代码前后使用三个反引号```  
星号空格 \* ：项目符号

插入网络图片

\!\[avatar\](https://github.com/lknlll/LinDroid/blob/LinDroid/blog/pic/ThreadLocalIntro.png)

块引用：行的开头加上“大于”插入符号（>）

项目符号：在文字前面加上 - 

**Android Studio**

create class 填写VISIBILITY PUBLIC 全部大写

默认快捷键  

| 操作  | Mac | Windows |
|---|---|---|
| 排列格式  | option + command + L | |
| 打开Project Structure  | command + ; | |
| 页签切换  | command + shift + \[ or \] | |

color 左侧可以点击 会出现color picker

Git remote authentication change
Preferences | Appearance & Behavior | System Settings | Passwords  

Refactor > Migrate to AndroidX 迁移AndroidX

hot key:  
 inn insert if not null

编译报错：Program type already present:com.xx.xx

通常是重复依赖的module, aar, jar包导致；

**Java基础**

Java虚拟机（JVM）

JVM生命周期  
* 启动。启动一个Java程序时，一个JVM实例就产生了，任何一个拥有public static void main(String[] args)函数的class都可以作为JVM实例运行的起点。
* 运行。main()作为该程序初始线程的起点，任何其他线程均由该线程启动。
* 消亡。当程序中的所有非守护线程都终止时，JVM才退出；若安全管理器允许，程序也可以使用Runtime类或者System.exit()来退出。

一个运行中的Java虚拟机有着一个清晰的任务：执行Java程序。程序开始执行时他才运行，程序结束时他就停止。你在同一台机器上运行三个程序，就会有三个运行中的Java虚拟机。 Java虚拟机总是开始于一个main()方法，这个方法必须是公有、返回void、直接受一个字符串数组。在程序执行时，你必须给Java虚拟机指明这个包换main()方法的类名。main()方法是程序的起点，他被执行的线程初始化为程序的初始线程。程序中其他的线程都由他来启动。

Java中的线程分为两种：守护线程 （daemon）和普通线程（non-daemon）。守护线程是Java虚拟机自己使用的线程，比如负责垃圾收集的线程就是一个守护线程。当然，你也可以把自己的程序设置为守护线程。包含main()方法的初始线程不是守护线程。

只要Java虚拟机中还有普通的线程在执行，Java虚拟机就不会停止。如果有足够的权限，你可以调用exit()方法终止程序。

List 按索引添加新元素，  
void add(int index, E element);
index 不得大于List.size()，插入后插入的元素变为第index个

数组转list使用Arrays.asList(T... a)  

```
String[] stringArray = {"hello","world","B"};
List<String> stringB = Arrays.asList(stringArray);
```  

android.util.Pair
```
Pair pair = new Pair(1, 2);//第一种创建方式 
Pair pair2 = Pair.create("1", 2);//第二种创建方式 
//Pair 的 first是获取第一个位置的数据，second是获取第二个位置的数据
```

Annotation 注解

lambda表达式，闭包，Java 8特性

允许把函数作为一个方法的参数。

参数列表，箭头（->），以及一个表达式或语句块

函数式接口(functional interface)只能有一个抽象方法，可以在Lambda表达式中使用，

```
        mButton.setOnClickListener(v -> {
            Log.e(TAG, "lambda");
        });
```

```
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "anonymous class");
            }
        });
```

java中不存在byte/short型常量的表示法

Java 获取class

1：知道java文件的路径

Class userClz=Class.forName("com.oracle.entity.User")

2:通过类的对象获得class

User user=new User();  
Class userclz=userClz.getClass();

3:通过类的“class运算”，这里我把它称为“运算”，因为不是方法，也不是属性。

`Class userClz=User.class;`

byte[]转ByteBuffer

byte[] bytes = ......;  
ByteBuffer buf = ByteBuffer.wrap(bytes);

java的访问控制是停留在编译层的，不会在.class文件中留下任何的痕迹，只在编译的时候进行访问控制的检查。通过反射可以访问任何包下任何类中的成员，例如，访问类的私有成员也是可能的

|   | 类内部 | 本包 | 子类 | 外部包 |
|---|---|---|---|---|
| public  |✔|✔|✔|✔|
| protected  |✔|✔|✔|✘|
| default  |✔|✔|✘|✘|
| private  |✔|✘|✘|✘|

List<String>不是List<Object>的子类型, 泛型是Object的类型和泛型是?的类型不同

位运算：  
&与&&（短路与），|与||（短路或），区别在于，短路与 短路或，当后面表达式的结果对整个结果不产生影响时便不会再执行

异或：^ 

相同则结果为0，不同则结果为1

15 异或2 ，15转换成二进制为1111，2转换成二进制为0010，异或结果为1101 即13

\>>> (unsigned right shift) 无符号右移，左边补0；

按位“或”赋值 运算符 (|=)

n |= n >>> 1 结果相当于 n = (n | n >>> 1)
    
    n = 10;//1010
    n |= n >>> 1;//0101 | 1010 = 1111
    n |= n >>> 2;//1111 | 0010 = 1111

java.net.SocketTimeoutException: Read timed out

网络请求超时抛出

**Shell**

cd .. 返回上一级

**Gradle**

Android studio中引入了build.gradle中的applicationId这个概念，作为APP的唯一标识。  
这样的好处是进行了解耦，applicationId作为APP的唯一标识，而AndroidManifest.xml中的包名package负责代码和资源的路径，包名可以随意改，可以和applicationId不一致。  
这样还有一个好处，假如你想发布一个免费版，一个收费版，你只需要在build.gradle中把applicationId后面加上免费版的后缀包名（如".free"），收费版加上收费版的后缀，你的代码也不需要对包名进行重构。  
获取APP唯一标识applicationId的方法为：  
getApplicationInfo().processName)，或getApplication().getPackageName()，或getApplicationInfo().packageName  
经测试，获取的都是gradle.build中的applicationId，而不是AndroidManifest.xml中的包名package

Could not download okhttp.jar (com.squareup.okhttp3:okhttp:3.10.0)

project gradle中两处加入  

`mavenCentral()`

gradle 脚本实现单个aar 建立maven坐标

新建aar-upload.gradle文件，并在build.gradle文件中使用apply from: 'aar-upload.gradle'进行引用，aar-upload.gradle的内容如下
```
apply plugin:'maven-publish'

publishing{

    repositories {
        maven {
            credentials {
                username "maven库用户名"
                password "密码"
            }
            url "http://*****/nexus/content/repositories/snapshots/" //maven库地址
        }
    }


    publications{
        audio(MavenPublication) {
            groupId 'com.zjxiliu.android'
            artifactId 'audio'
            version '1.0.0-SNAPSHOT'
            artifact 'aars/audio.aar' //aar目录下面的audio.aar文件
        }
    }
}
```

一套project各插件版本组合，解决各类编译冲突问题  
诸如  
CMake was unable to find a build program corresponding to "Ninja"  
ERROR: Error occurred while communicating with CMake server.  
(project root path)/build.gradle  

``` dependencies {
        classpath 'com.android.tools.build:gradle:3.3.0'
        classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1' //maven upload 用
    }
```

(project root path)/gradle/wrapper/gradle-wrapper.properties

```distributionUrl=https\://services.gradle.org/distributions/gradle-4.10.1-all.zip```

if using cmake for native code  
(project root path)/(module path)/build.gradle

```externalNativeBuild {
       cmake {
           path "CMakeLists.txt"
           version "3.10.2"
       }
   }
```



**Other**
github file download https://d.serctl.com/

TextLine.sCached leak no need to fix

java.lang.NoClassDefFoundError: Failed resolution of: Lorg/apache/http/client/methods/HttpPost

AndroidManifest.xml  的application 标签中添加

`<uses-library android:name="org.apache.http.legacy" android:required="false" />`

尽量在Android 9 以后不要用这个库

以 Android 10（API 级别 29）及更高版本为目标平台的应用在默认情况下被赋予了对外部存储设备的分区访问权限（即分区存储）。此类应用只能看到本应用专有的目录（通过 Context.getExternalFilesDir() 访问）以及特定类型的媒体

java.lang.SecurityException: ConnectivityService: Neither user 10037 nor current process has android.permission.CHANGE_WIFI_STATE

检查Manifest是否存在相关权限声明
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
修改WIFI状态

java.lang.RuntimeException: Parcelable encountered IOException writing serializable object

intent.getSerializableExtra 时key对应的类或者其子类没有implement Serializable

Supertypes of the following classes cannot be resolved. Please make sure you have the required dependencies in the classpath
kotlin暂时不支持greendao，相关操作使用Java

Signal protocol 端到端的通讯加密协议

public String toUpperCase() 返回全大写

Android Studio 4.1 导入项目提示 Could not find method buildFeatures()for arguments xxx on extension ‘android‘on...
原因是当下IDE及插件版本下dataBinding的开启方式不正确

```
buildFeatures {
    dataBinding = true
}
```
```
dataBinding {
    enabled = true
}
```

xml外设置margin
```
LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
lp.setMargins(10, 20, 30, 40);
imageView.setLayoutParams(lp);
```  

App安装后一直出现waiting for debugger，开发者选项中设置了“选择调试应用选项”为该应用
小米安装应用失败之坑：MIUI默认在开发者选项最底部开启了启用MIUI优化；

Cannot fit requested classes in a single dex file (# methods: 66822 > 65536)  
Android系统定义总方法数是一个short int，short int 最大值为65536  
超过65k个方法。一个dex已经装不下，需要个多个dex，也就是multiDex
application module build.gradle: 

```
android {
    defaultConfig {
        //
        multiDexEnabled true
    }
}

dependencies {
    implementation 'com.android.support:multidex:1.0.3'
}
```

如果存在自定义Application子类  
```
@Override
public void onCreate() {
    super.onCreate();
    MultiDex.install(this);
}
```

github push fail 报错

Push failed
Unable to access 'https://github.com/lknlll/LinDroid.git/': LibreSSL SSL_connect: SSL_ERROR_SYSCALL in connection to github.com:443

Git支持三种协议：git://、ssh://和http://

取消http代理

git config --global --unset http.proxy