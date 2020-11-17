
**ADB**

adb -s 多设备选择设备  

adb shell
查看CPU  
cat /proc/cpuinfo  
在焦点位置输入XXX  
input text XXX

adb命令长时间无响应且ctrl+c不能退出可以用  
\>exit

adb logcat > /Users/linkunning/test/test.log  
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

**GIT**  
Git config http.postBuffer 524288000

只clone单独分支且只拉取一次commit 
git clone -b LinDroid --depth 1  https://github.com/lknlll/LinDroid.git
可以避免git目录下的pack 文件过大

git 设置本地忽略必须保证 git 的远程仓库分支上没有这个要忽略的文件。如果远程分支上存在这个文件，本地再设置 ignore，将不起作用。
需先push 该文件的删除，再设置gitignore

**社区**  
[掘金](https://juejin.im/)

**MarkDown**  
换行：两空格一个回车 或者HTML的 \<br>  
超链接：\[链接文字\]\(链接地址\)  
转义：当我们想在 Markdown 文件中显示一些标记符号，可以使用\进行转义  
下标（符号方式）：θ~1~  上标：θ^2^  
下标（标签方式）：θ<sub>1</sub>  上标 ：θ<sup>2</sup>  
序号：数字 加点 加空格

插入程序代码的方式：单行代码前后使用反引号 `，或多行代码前后使用三个反引号```

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

hot key:  
 inn insert if not null

编译报错：Program type already present:com.xx.xx

通常是重复依赖的module, aar, jar包导致；

**Java基础**

List 按索引添加新元素，  
void add(int index, E element);
index 不得大于List.size()，插入后插入的元素变为第index个

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

&与&&（短路与），|与||（短路或），区别在于，短路与 短路或，当后面表达式的结果对整个结果不产生影响时便不会再执行

java.net.SocketTimeoutException: Read timed out

网络请求超时抛出

**Shell**

cd .. 返回上一级

**Gradle**

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

**Other**

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