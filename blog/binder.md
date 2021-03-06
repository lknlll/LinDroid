**知识准备**

跨进程通信（IPC，Inter-Process Communication）

进程隔离：保护操作系统中的进程互不干扰，避免进程A写入进程B，使用虚拟地址空间，不同进程使用不同虚拟地址，进程间数据不共享，进程间交互依赖IPC机制

虚拟内存，对应用程序是一片连续的地址空间，映射到碎片化物理空间中，映射过程对应用程序无感，

操作系统内核的进程所用的虚拟地址空间需要和应用程序进程空间分离开，前者是内核空间（Kernel Space）后者是用户空间（User Space）,  
32位操作系统寻址空间是2的32次方，即4G，一般高位1G是内核空间，3G是用户空间。

内核拥有对底层设备的所有访问权限，普通应用程序没有，且用户进程也不能直接访问内核进程。

用户空间需要访问内核空间时，例如文件IO或者网络等，实现方式是通过**系统调用**接口，对内核空间在内核控制下进行有限访问。  
内存映射就是通过系统调用mmap实现。
应用程序执行自己代码时，进程状态为用户态，处理器执行用户代码权限较低，用户进程通过系统调用调用内核代码时，进程暂时进入内核态，处理器权限高，可执行特权指令；

用户空间之间的通信（IPC机制），通过内核来支持，内核中有对应IPC机制的驱动程序；
Binder机制在内核中也有一个Binder驱动程序；  
驱动程序一般指系统设备驱动程序，相当于操作系统和硬件间接口。Binder驱动程序驱动的是虚拟的字符设备，注册在/dev/binder中，定义一套Binder通信协议，提供数据包在进程间传递的底层支持。  
应用程序访问Binder驱动也要通过系统调用。


**应用**

App 启动并初始化

Activity 启动过程

进程间通信

AIDL

插件化框架的设计原理

**Linux IPC**

管道、消息队列、共享内存、Socket（套接字）

优缺点分析

传统IPC机制没有安全措施，接收方无法获取进程ID或用户ID，靠上层保护协议，  
传统IPC接入点开放，访问不受限制。

Android 为已安装的App分配了用户ID（UID），鉴别进程身份，校验权限；

| IPC机制  | 内存拷贝次数 | 安全风险 |
| --- | --- | --- |
| Binder | 1 | --- |
| 管道 | 2 |  |
| 消息队列 | 2 |  |
| Socket | 2 | IP地址是客户端填入，恶意篡改风险 |
| 共享内存 | 0 | --- |

共享内存较Binder 管理复杂

管道，消息队列，Socket一般流程：  
1. 发送方通过系统调用（copy_from_user）将所要发送数据存拷贝到内核缓冲区；
2. 接收方开辟一定内存空间，内核通过系统调用（copy_to_user）将内核缓冲区数据拷贝到接收方的内存缓冲区。  
问题：接收方开辟内核空间可能浪费。

###### Binder

Binder一次数据拷贝原理，在内核空间和接收方用户空间中做一次内存映射，减少一次内存拷贝。

**Binder 通信模型**

基于C/S架构，发起请求的进程属于Client，接收请求的进程属于Server。

Binder 4个角色：Client、Server、Binder驱动和ServiceManager。  
Binder驱动类似于路由，将Client请求转发到Server，将Server返回的数据传给Client。
ServiceManager类似于DNS，负责将Client请求的Server 描述符转化为具体的Server地址，以便于Binder驱动转发至Server，Server提供Binder服务都需要在ServiceManager注册。

一般流程：
1. Server通过Binder驱动向ServiceManager注册，声明可以对外提供服务，ServiceManager保留一份Server名称和其引用的映射表；
2. Client向ServiceManager请求Server的Binder引用。
3. 通过该Server的Binder引用向其发送请求。
4. Server响应后通过Binder驱动进行返回。

ServiceManager的创建：  
Android系统启动后，创建一个名为_servicemanger_的进程，该进程通过一个约定命令_BINDER*SET*CONTEXTMGR_向系统注册成为ServiceManager，Binder驱动自动为该ServiceManager创建一个Binder实体。
这个Binder实体的引用在所有的Client中都是0，也就是Client通过0号引用向ServiceManager注册，Server通过0号引用可以获取Server的Binder引用。

**代理机制**  
Client调用Server的Binder引用（一个Object）的方法来调用Server，但这个Object并不是Server真正的实体，Binder驱动做了一层对象转换，将其包装成一个ProxyObject，这个代理对象的方法签名和实体对象都一样，Client请求调用一个方法时，Binder驱动就将其转发到具体的Binder实体的该方法。

Object 称为本地对象，Client调用的Binder引用ProxyObject称为代理对象。

**AIDL 对Binder机制的应用**

aidl: Android Interface Definition Language, 接口定义语言，

步骤：

1. 创建aidl文件，定义带有方法签名的编程接口，这里面的方法可供调用。经过build之后生成可供引用的Java类。例子中ISchool.aidl经过编译生成ISchool.java  
   ISchool.java 扩展了IInterface，声明了ISchool.aidl中的声明的接口方法
   1. **IInterface**是一个Base接口，用来标识Server提供的能力，是Server和Client通信的协议。
   2. 静态抽象内部类**Stub**，继承了Binder类，实现ISchool接口，它的子类需要去具体实现ISchool接口的方法，作为Server的本地对象。
      1. **Binder类**：提供Binder服务的本地对象的基类，实现了IBinder接口；
      2. **IBinder**接口是进程间通信的Base接口，声明了跨进程通信所需实现的抽象方法，Client和Server都需要实现这个接口来实现跨进程通信。
      3. Stub内部有一个静态内部类**Proxy**，它的角色是Binder代理，是Server交给Client调用的本地代理对象，包含一个IBinder对象mRemote，这个对象在Proxy构造方法中被赋值，会在调用Proxy的asRemote()方法时返回；  
         Proxy也实现ISchool接口，将参数序列化，交给mRemote处理，此处实际上就是通过代理调用Binder驱动去和远程Stub进行通信，这里还需要一个方法asInterface()。
      4. **asInterface()**，一般是Client bindService()成功以后进行调用，就是将绑定成功之后返回的IBinder对象转换为具体的IInterface接口。Client通过调这个方法返回扩展了IInterface的Server接口，从而能够调用Server的能力。
         这个方法返回IInterface对象既可能是Stub本身所在的实现了IInterface的对象，也可能是使用传入的IBinder对象创建的代理类Proxy实例，因为Binder也可以为本进程服务，此时就不必通过Binder驱动来中转。  
      如果方法返回自定义的类，该类必须扩展Parcelable，并且为其单独声明一个aidl文件，import之。
2. Server App中，定义Service，.aidl文件生成的Java类中有一个名为Stub的内部抽象类，扩展Binder类实现aidl接口，在Service中扩展这个类并具体实现aidl方法。
   之后将Service注册在Manifest中，添加好action。
3. 在Service的onBind()中返回上面那个扩展了Stub的类的实例，返回类型IBinder。

Client 和Server在不同进程，Client 请求后调用方线程会被挂起，Binder提供了异步方式，等待Server响应后返回数据，Server的响应线程是在Binder线程池中，而不是主线程。

