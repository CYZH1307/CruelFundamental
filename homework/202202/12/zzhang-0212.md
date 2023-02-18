#### 介绍一下单例模式的含义及使用场景

Referece: https://www.runoob.com/design-pattern/singleton-pattern.html

1. 单例类只能有一个实例
2. 单例类必须自己创建自己的唯一实例
3. 单例类必须给所有其他对象提供这一实例



- 构造函数是私有的
- 在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（例如学院网站页面缓存）

使用场景：

1. 需要产生唯一序列号
2. 创建Web中的计数器，不用每次刷新都数据库内count+1，可先用单例缓存
3. 创建消耗资源多的对象，如I/O与数据库的连接。



例子：

```java
public class SingleObject {
  private static SingleObject instance = new SingleObject(); // single instance
  private SingleObject(){} // 构造函数为 private
  public static SingleObject getInstance() {
    return instance;
  }
  // ... other methods
}
```

