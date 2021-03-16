##### HashMap

- 数据结构及增删改查耗时；

- 容量为什么是2的倍数（power-of-two expansion），扩容何时由什么决定；

- 线程不安全问题；

- 构造方法；

HashMap中位运算代替常规运算提升效率的例子有什么；

---

###### 哈希表及哈希冲突
哈希表（散列表）  
在哈希表中进行添加，删除，查找等操作，性能十分之高，不考虑哈希冲突的情况下，仅需一次定位即可完成，时间复杂度为O(1)  
主干是数组，数组下标由哈希函数确定，

哈希冲突（哈希碰撞）  
两个不同的元素，通过哈希函数得出的实际存储地址相同，插入时会存在已经占用的情况；  
解决方案:开放定址法（发生冲突，继续寻找下一块未被占用的存储地址），再散列函数法，链地址法，

而HashMap即是采用了链地址法，也就是数组+链表

###### 源码实现

主干是一个Node数组，Node是HashMap的基本组成单元，被分配的层长度一定是2的幂

    //When allocated, length is always a power of two.
    transient HashMap.Node<K, V>[] table;

    //最大容量 2的30次方
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    
The load factor represents at what level the HashMap capacity should be doubled,

当HashMap的容量达到threshold域值，触发扩容resize()

threshold = length * loadFactor;

Default 75%, 16 * 0.75 = 12. 

Means after storing the 12th key – value pair into the HashMap , its capacity becomes 32.

    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    /**
     * The next size value at which to resize (capacity * load factor).
     *
     * @serial
     */
    int threshold;

主干数组的每一个元素是单链表，

每一个节点的hash值，是将key的hashCode 和 value的hashCode 异或得到的。

**Node**

    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;//单链表

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);//异或
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    } 
    
    
JDK8中，当链表长度达到8，会转化成红黑树，提升效率，

**Constructor**：4

1. 没有参数：默认default initial capacity (16) and the default load factor (0.75).

2. 自定义Capacity，就是用默认loadFactor调下一种； 

3. 自定义Capacity及loadFactor加载因子，这里有个构造方法里计算阈值的地方tableSizeFor方法；

4. Map参数构造HashMap；

    tableSizeFor 返回大于输入参数且最近的2的整数次幂的数；在Java 8 优化
    
    思路是：位运算，先减一，兼容本身就是2的整数次幂的情况，最高位的1后面的位全变为1后再加1；


