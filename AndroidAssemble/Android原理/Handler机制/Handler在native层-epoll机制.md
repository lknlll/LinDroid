[TOC]

# epoll机制是什么

> epoll is a Linux system call, which allows to monitor a file descriptor for IO events. nativePollOnce calls epoll_wait on a certain file descriptor, whereas nativeWake writes to the descriptor, which is one of the IO operations, epoll_wait waits for. The kernel then takes out the epoll-waiting thread from the waiting state and the thread proceeds with handling the new message. when a thread epoll-waiting, it becomes disabled for thread scheduling purposes.

epoll是一个Linux的系统调用，可以监控文件描述符的IO事件，nativePollOnce在一个特定文件描述符上调用了epoll_wait，等到nativeWake对这个文件描述符调用了IO操作write，内核就监控到，并把相应的线程从等待状态唤醒然后开始处理消息，这个机制的好处就在于线程在epoll-waiting态时，不参与线程调度，不需要被CPU轮询，也就是不占CPU。

Android 2.3(API 9)开始，由Java的Object.wait()/notify()改为native层epoll机制实现；

# 多路复用

阻塞BIO（Blocking-IO），一个线程只处理一个流并一直等待，新的流会不断启线程，分配内存，浪费线程资源；
非阻塞NIO（Non-Blocking IO），线程不等待长连接流的结果，但需要CPU不断访问流，才能得知哪些流有数据，浪费 CPU 资源；

内核接管流结果的监听，同时监听多个流：I/O 多路复用。

Linux有 select, poll, epoll三种多路复用模型；

- select：只能监控 FD_SETSIZE 个链接，libc 里这个数量设置的很小
- poll：尽管没有链接数的限制，也就是说可以监控 RLIMIT_NOFILE 个链接，但是每一次都要对所有监控链接从头到尾的扫描，速度O(n)，这也降低了性能

在这两个系统调用使用时，内核与用户空间通过内存复制来进行消息传递，进一步降低性能
而 epoll 没有 select 和 poll 的限制， 可以 O(1) 时间处理操作，内核与用户空间通过 mmap 来进行消息传递，又进一步加快了速度。

Linux 内核对于 epoll 池的内部实现就是用红黑树的结构体来管理这些注册进程来的句柄 fd。红黑树是一种平衡二叉树，时间复杂度为 O(log n)，就算这个池子就算不断的增删改，也能保持非常稳定的查找性能。

这里用到的文件描述符是eventfd：eventfd 实现非常简单，故名思义就是专门用来做事件通知用的。使用系统调用 eventfd 创建，这种文件 fd 无法传输数据，只用来传输事件，常常用于生产消费者模式的事件实现；
