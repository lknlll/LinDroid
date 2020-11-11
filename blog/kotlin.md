##### ==
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

###### ?
?.表示对象为空时就直接返回null

###### as
类型强转: as 运算符

##### 匿名内部类：
```
    button.setOnClickListener(object: View.OnClickListener{
      override fun onClick(p0: View?) {
        //can be replaced by lambda
      }
    })
```

###### let

```
object.let{
   it.todo()//在函数体内使用it替代object对象去访问其公有的属性和方法
}
```

###### if
区别于Java，kotlin if可以直接作为表达式，赋值或在返回值中返回
val max = if (a > b) a else b

布尔型只有一个Boolean类型，值只能是true或false，不能用0或者非0来代表