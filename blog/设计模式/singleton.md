##### 单例模式 Singleton

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

