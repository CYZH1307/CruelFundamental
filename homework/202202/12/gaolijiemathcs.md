# 单例模式的含义及使用场景

### 单例模式简介

单例模式属于创建型模式，提供了创建对象的方式。只有一个实例存在，整个系统只拥有一个全局的对象。该类只负责创建自己的对象，同时确保只有单个对象被创建，提供了一种访问其唯一的对象，可以直接访问，不需要实例化该类的对象。

- 只能有一个实例
- 单例类必须自己创建自己的唯一实例
- 单例类必须给所有其他对象提供这个实例

### 使用场景：

想控制实例数目，节省资源。

- 生产单一序列号
- web计数器 单例缓存
- 创建对象需要消耗过多资源 例如I/O与数据库连接

### 优缺点

特点：

- Java中单例模式保证在JVM中，该对象只会有一个实例存在。
- 构造器必须为私有的，外部类无法通过调用构造器创建实例。
- 没有公开的set方法，外部类无法调用set方法创建实例。
- 提供一个公开的get方法获取唯一实例。

优点：

- 某些类创建较为繁琐，大型的对象创建是一笔很大开销
- 省去了new操作，降低系统内存使用频率。
- 系统中某些类，例如spring的controller，控制处理流程，如果该类创建很多，系统混乱。
- 避免资源的重复占用。

缺点：

- 没有接口，不能继承，与单一职责原则冲突。一个类应该只关心内部逻辑，不关系外部如何实例化。



https://www.runoob.com/design-pattern/singleton-pattern.html