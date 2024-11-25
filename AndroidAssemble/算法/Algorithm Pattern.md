

##### 基本门类

1.Binary Search 二分查找

2.Two Pointers/Sliding Window 双指针和滑动窗口

3.Data Structure 数据结构 - LinkedList/Queue/Stack

4.Hash Table/Prefix sum 哈希表和前缀和

5.BFS

6.DFS

7.Tree

8.Graph 图

9.Heap/Top K 堆、前K最K个

10.Union Find & 综合应用

##### 模版

##### 经典

KMP 字符串匹配

排序：冒泡、快速、插入
http://www.jianshu.com/p/ae97c3ceea8d

引申：[一个大致有序的数组如何排序](https://blog.csdn.net/qq_36523667/article/details/80529221)

##### 待归类

###### 队列实现栈

队列：FIFO，FIRST IN FIRST OUT 先进先出，队尾进，队首出；
栈：LIFO，LAST IN FIRST OUT 后进先出，栈顶进，栈顶出；

API Java

队列Queue

//从队首移除并返回出队元素
poll()

//查询队首
peek()

//入队方法，向队尾添加元素；
offer()

栈Stack

push()：入栈方法，向栈顶添加元素；
pop()：出栈方法，将栈顶的元素移除并返回元素；
peek()：查询栈顶元素，并不会移除元素。



两个队列实现栈

思路：

队列1，用于最终保存元素，队列2，用于入栈时维护；

每次入栈的元素，应使其放置在队列1的队首，这样才能保证出栈时从队列1出队的是最后入栈的；

入栈的元素，先入队列2，成为队首，然后将队列1的元素，依次从队首移除并入队队列2，

队列1完全取完后，队列2也维护完成；

队列2赋给队列1后，即可清空，等待下一次入栈；

代码中操作是，取完了的空队列1，用temp进行临时保存，队列2赋给队列1后，将temp赋给队列2，就实现了清空；

一个队列实现栈 

入栈的元素入队后，依次从队首移出元素重新入队；

时间复杂度都是O(n)

com.example.lindroidcode.algodatastruct


###### 容斥原理

[分糖果](https://leetcode.cn/problems/distribute-candies-among-children-i/) 


###### 设计数据结构

[O(1) 时间插入、删除和获取随机元素](https://blog.csdn.net/qq_26460841/article/details/120360876)