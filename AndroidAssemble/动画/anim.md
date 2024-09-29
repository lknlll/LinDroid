


Animation和Animator



动画的种类：前者只有透明度，旋转，平移，伸缩4种属性，而对于后者，只要是该控件的属性，且有setter该属性的方法就都可以对该属性执行一种动态变化的效果。

可操作的对象：前者只能对UI组件执行动画，但属性动画几乎可以对任何对象执行动画（不管它是否显示在屏幕上）。

动画播放顺序：在Animator中，AnimatorSet正是通过playTogether()、playSequentially()、animSet.play().with()、before()、after()这些方法来控制多个动画协同工作，从而做到对动画播放顺序的精确控制 