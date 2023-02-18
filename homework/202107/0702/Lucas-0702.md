# Java中String，StringBuffer和StringBuilder的区别
- https://www.jianshu.com/p/8c724dd28fa4

## 相同
- 都是final类，不能被继承

## String
- 长度不可变，线程安全
- 不可变对象，对象改变时，每次都生成新的对象
- 对象非常多的时候，JVM GC介入，性能降低
- 不要经常使用String.+()

## StringBuffer
- 长度可变，线程安全
- 改变时，操作对象本身，不会创建新的对象
- 多数情况下推荐使用

## StringBuilder
- 长度可变，线程不安全
- Java 1.5 引入
- 轻量级StringBuffer，比之快
