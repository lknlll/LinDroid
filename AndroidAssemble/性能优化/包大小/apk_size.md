包大小

https://www.jianshu.com/p/5921e9561f5f

gradle 重复资源检测

一个完整APK包含以下目录（将APK文件拖到Android Studio）：

META-INF/：包含CERT.SF和CERT.RSA签名文件以及MANIFEST.MF 清单文件。

assets/：包含应用可以使用AssetManager对象检索的应用资源。

res/：包含未编译到的资源 resources.arsc。

lib/：包含特定于处理器软件层的编译代码。该目录包含了每种平台的子目录，像armeabi，armeabi-v7a， arm64-v8a，x86，x86_64，和mips。

resources.arsc：包含已编译的资源。该文件包含res/values/ 文件夹所有配置中的XML内容。打包工具提取此XML内容，将其编译为二进制格式，并将内容归档。此内容包括语言字符串和样式，以及直接包含在**resources.arsc*8文件中的内容路径 ，例如布局文件和图像。

classes.dex：包含以Dalvik / ART虚拟机可理解的DEX文件格式编译的类。

AndroidManifest.xml：包含核心Android清单文件。该文件列出应用程序的名称，版本，访问权限和引用的库文件。该文件使用Android的二进制XML格式。 

lib、class.dex和res占用了超过90%的空间，所以这三块是优化Apk大小的重点（实际情况不唯一）

###### 减少res，压缩图文文件

图片文件压缩是针对jpg和png格式的图片。我们通常会放置多套不同分辨率的图片以适配不同的屏幕，这里可以进行适当的删减。在实际使用中，只保留一到两套就足够了（保留一套的话建议保留xxhdpi，两套的话就加上hdpi），然后再对剩余的图片进行压缩(jpg采用优图压缩，png尝试采用pngquant压缩)

###### 减少dex文件大小

添加资源混淆 

```
android {
      buildTypes {
          release {
              minifyEnabled false
              proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
          }
      }
 }



```

shrinkResources为true表示移除未引用资源，和代码压缩协同工作。

minifyEnabled为true表示通过ProGuard启用代码压缩，配合proguardFiles的配置对代码进行混淆并移除未使用的代码。

代码混淆在压缩apk的同时，也提升了安全性。

Android混淆最佳实践
https://www.jianshu.com/p/cba8ca7fc36d

###### 减少lib文件大小

由于引用了很多第三方库，lib文件夹占用的空间通常都很大，特别是有so库的情况下。很多so库会同时引入armeabi、armeabi-v7a和x86这几种类型，这里可以只保留armeabi或armeabi-v7a的其中一个就可以了，实际上微信等主流app都是这么做的。

只需在build.gradle直接配置即可，NDK配置同理