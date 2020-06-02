
**ADB**
adb shell am start com.android.settings/com.android.settings.Settings 打开原生系统Settings

小米再次安装App失败，报错
Error type 3  
Error: Activity class {xxx/xxx.MainActivity} does not exist.

adb uninstall packageName 后再安装，可能是卸载不彻底

**MAC**  
Command+shift+. 显隐/隐藏

**GIT**  
Git config http.postBuffer 524288000

只clone单独分支且只拉取一次commit 
git clone -b LinDroid --depth 1  https://github.com/lknlll/LinDroid.git
可以避免git目录下的pack 文件过大

**社区**  
[掘金](https://juejin.im/)

**MarkDown**  
换行：两空格一个回车  
超链接：\[链接文字\]\(链接地址\)  
转义：当我们想在 Markdown 文件中显示一些标记符号，可以使用\进行转义  
下标（符号方式）：θ~1~  上标：θ^2^  
下标（标签方式）：θ<sub>1</sub>  上标 ：θ<sup>2</sup>  

**Android Studio**  
默认快捷键  

| 操作  | Mac | Windows |
|---|---|---|
| 排列格式  | option + command + L | |
| 打开Project Structure  | command + ; | |

color 左侧可以点击 会出现color picker

Git remote authentication change
Preferences | Appearance & Behavior | System Settings | Passwords

hot key:  
 inn insert if not null

编译报错：Program type already present:com.xx.xx

通常是重复依赖的module, aar, jar包导致；

**Java基础**

java中不存在byte/short型常量的表示法

Java 获取class

1：知道java文件的路径

Class userClz=Class.forName("com.oracle.entity.User")

2:通过类的对象获得class

User user=new User();  
Class userclz=userClz.getClass();

3:通过类的“class运算”，这里我把它称为“运算”，因为不是方法，也不是属性。

Class userClz=User.class;

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

**Shell**

cd .. 返回上一级

**Gradle**

Could not download okhttp.jar (com.squareup.okhttp3:okhttp:3.10.0)

project gradle中两处加入 mavenCentral()