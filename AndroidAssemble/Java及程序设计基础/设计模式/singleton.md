##### 单例模式 Singleton

多线程中建立单例模式考虑的因素有很多，比如线程安全 -延迟加载-代码安全:如防止序列化攻击，防止反射攻击(防止反射进行私有方法调用) -性能因素

实现方法有多种，饿汉，懒汉(线程安全，线程非安全)，双重检查(DCL),内部类，以及枚举

所谓双层检验锁（在加锁前后对实例对象进行两次判空的检验）：加锁是为了第一次对象实例化的线程同步，而锁内还要有第二层判空是因为可能会有多个线程进入第一层if判断内部，而在加锁代码块外排队等候，如果锁内不进行第二次检验，仍然会出现实例化多个对象的情况。


单例模式的总结
https://xxxblank.github.io/2017/09/14/singleTon/


###### 饿汉模式：

不管一个人吃不吃东西都先把吃的准备好

    public class SingletonTest	{  
    
        // 定义一个私有的构造方法
        private SingletonTest() {  
        }
    
        // 将自身的实例对象设置为一个属性,并加上Static和final修饰符
        private static final SingletonTest instance = new SingletonTest();  
    
        // 静态方法返回该类的实例
        public static SingletonTest getInstance() {  
            return instance;  
        }
    }


缺点：类加载时申请内存，即使不用到这个实例也吃内存；

###### 懒汉模式(线程不安全)：

    public class SingletonTest	{
    
        // 定义私有构造方法（防止通过 new SingletonTest()去实例化）
        private SingletonTest() {   
        }   
    
        // 定义一个SingletonTest类型的变量（不初始化，注意这里没有使用final关键字）
        private static SingletonTest instance;
    
        // 定义一个静态的方法（调用时再初始化SingletonTest，但是多线程访问时，可能造成重复初始化问题）
        public static SingletonTest getInstance() {   
            if (instance == null)   
                instance = new SingletonTest();   
            return instance;   
        }
    }

对比饿汉，懒汉类加载时不分配内存，getInstance第一次调用才申请内存；
缺点：多线程getInstance有可能多次new 出实例来；

####### 懒汉模式（线程安全）

    public	class	SingletonTest	{
        private SingletonTest() {   
        }   
        private	static SingletonTest instance;   
    
        // 定义一个静态的方法（调用时再初始化SingletonTest，使用synchronized 避免多线程访问时，可能造成重的复初始化问题）
        public static synchronized SingletonTest getInstance() {   
            if (instance == null)   
                instance = new SingletonTest();   
            return instance;   
        }   
    }
