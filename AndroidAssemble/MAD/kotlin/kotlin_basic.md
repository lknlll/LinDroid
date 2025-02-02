##### == Any和Object
kotlin.Any 类与 java.lang.Object 类相互映射

Any.equals() 函数定义如下：

`open operator fun equals(other: Any?): Boolean`

它重载了 == 操作符。我们很早就提到了，Kotlin 中的 == 等同于调用 equals() 函数，比较两个对象引用是否相等要用 === 操作符。

但这么说其实不准确，Kotlin 并不会直接把 == 编译为调用 equals() 函数，而是用 kotlin.jvm.internal.Intrinsics.java 的 areEqual() 方法包装一下，这个方法有多个重载形式，我们看一下最通用的一个：

```
public static boolean areEqual(Object first, Object second) {
  return first == null ? second == null : first.equals(second);
}
```

这就保证了空安全。所以说，我们用 == 操作符时不需要担心空安全，a == b 并不等同于 a.equals(b)，而是 a?.equals(b) ?: b == null

##### 空保护机制
?.表示对象为空时就直接返回null，仅当对象不为空时才调用后面的方法


        //类型后面加?表示可为空
        var age: String? = "23"
        //双叹号表示变量为空时抛出空指针异常
        val ages = age!!.toInt()
        //不做处理返回 null
        val ages1 = age?.toInt()
        //age为空返回-1
        val ages2 = age?.toInt() ?: -1

##### as
类型强转: as 运算符

##### 匿名内部类：
```
    button.setOnClickListener(object: View.OnClickListener{
      override fun onClick(p0: View?) {
        //can be replaced by lambda
      }
    })
```

##### let

```
object.let{
   it.todo()//在函数体内使用it替代object对象去访问其公有的属性和方法
}
```

##### if
区别于Java，kotlin if可以直接作为表达式，赋值或在返回值中返回
val max = if (a > b) a else b

布尔型只有一个Boolean类型，值只能是true或false，不能用0或者非0来代表

##### lateinit 修饰符

用 lateinit 修饰类属性的时候，实际上在告诉编译器：这个属性的初始化的时机和方式与编译器无关，由代码操作

##### 智能类型转换

当 is 检测通过时，Kotlin 会自动将 obj 视为指定类型，因此在 if 语句的分支内不需要显式地进行类型转换。

```
    if (obj is String) {
        println("字符串长度: ${obj.length}") // 在这里 `obj` 已被智能转换为 `String`，可以直接用String的方法
```
##### 区间
..操作符
相当于rangeTo()，指定范围

对于整型的区间用法示例：

```
    for (i in 1..4) print(i) // 输出“1234”
    
    for (i in 4..1) print(i) // 什么都不输出
    
    if (i in 1..10) { // 等同于 1 <= i && i <= 10
        println(i)
    }
    
    // 使用 step 指定步长
    for (i in 1..4 step 2) print(i) // 输出“13”
    
    for (i in 4 downTo 1 step 2) print(i) // 降序：从4到1，每2个取一个，输出“42”
    
    
    // 使用 until 函数排除结束元素
    for (i in 1 until 10) {   // i in [1, 10) 排除了 10
         println(i)
    }

```

##### 循环

数组遍历

```
        val intArray: IntArray = intArrayOf(1, 2, 3, 4, 5)

        for (i in intArray.indices) {
            print(intArray[i])
        }
```

结合标签的break 或者continue 
用标签可以指定break或continue哪趟循环

```kotlin
    loop@ for (i in 1..100) {
        for (j in 1..100) {
            if (j == 20) break@loop
        }
    }
```

结合标签的return 可以控制返回哪里

如果是没有标签的情况，直接从foo()返回

```kotlin
    fun foo() {
        ints.forEach {
            if (it == 0) return
            print(it)
        }
    }
```

如果使用标签 给内联函数的 lambda 表达式，用以限制 return
则可以从 lambda 表达式中返回,如下

```kotlin
    fun foo() {
        ints.forEach lit@ {
            if (it == 0) return@lit
            print(it)
        }
    }
```
隐式标签更方便。 该标签与接受该 lambda 的函数同名

```kotlin
    fun foo() {
        ints.forEach {
            if (it == 0) return@forEach
            print(it)
        }
    }

```

从标签 @a 返回 1 
`return@a 1`

##### field标识符

问题：会导致recursive call，正确是使用Backing field；
```
    class Student(_name: String, _age: Int) {
        val name = _name
            get() = this.name
        var age = _age
            get() = this.age
            set(value) {
                this.age = value
            }
    }
```

这里，name自定义了setter，只有isNotEmpty时，才把set的值传给backing field
person.name = ""//这里是空串，不赋给field
println(person.name) // 仍然输出 "initial value"

```kotlin
    class Person {
        var name: String = "initial value"
            set(value) {
                if (value.isNotEmpty()) {
                    field = value
                }
            }
    }
```

