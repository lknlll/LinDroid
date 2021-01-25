###### Android 9 Pie API 级别 28 适配

Android P以后限制了非加密的流量请求

访问网络时未使用https 或 wss, 则会报  
CLEARTEXT communication to *** not permitted by network security policy

解决： 
1.在res目录下创建xml文件夹，创建一个.xml文件
  
```
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>

```

2.在AndroidManifest中的application里设置networkSecurityConfig为上面 xml文件  

```
<application
        ****
        android:networkSecurityConfig="@xml/上面创建的文件"
        ****
 >
```