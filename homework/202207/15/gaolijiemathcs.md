###   什么是单例模式？ C++或者Java如何实现它？

单例模式：当前进程的过程，通过单例模式创建的类有且仅有一个实例。

特点：单例模式保证JVM中，该对象只有一个实例存在。构造器私有，外部类无法通过构造器创建实例。没有公开的set方法，外部无法调用set创建实例。会提供一个公开的get方法获取唯一实例。

优点：减小创建复杂大型类的开销，省去了new操作，降低系统内存的使用频率，降低GC压力，避免资源重复占用。



饿汉单例模式：

```java
public class Singleton {
    private static Singleton instance = new Singleton();
    
    private Singleton(){}
    
    public static Singleton getInstance() {
        return instance;
    }
}
```

饿汉饿，提前将对象new出来，即使是第一次获取这个类对象，就存在真累了，省去创建类步骤。



懒汉单例模式：

线程不安全模式：

```java
public class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

懒汉模式懒，第一次调用的时候发现实例是空的，才去初始化，再赋值，后面的调用和饿汉没区别。



饿汉和懒汉区别就是第一次创建的时候开销问题，和线程是否安全。懒汉用于数据不热的地方，热点数据用饿汉模式。



懒汉模式，线程安全模式，双检锁：

```java
public class Singleton {
    private volatile static Singleton instance = null;
    private Singleton() {}
    public static Singleton getInstance() {
        if(instance == null) {
            synchronized (Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```





