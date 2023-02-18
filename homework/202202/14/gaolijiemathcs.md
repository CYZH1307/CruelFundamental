## 如何保证单例模式只有唯一实例？你知道的都有哪些方法？

### 饿汉模式：

```java
public class Singleton {
    // 创建一个实例对象
    private static Singleton instance = new Singleton();
    // 私有构造方法，防止被实例化
    private Singleton(){}
    // 静态get方法
    public static Singleton getInstance() {
        return instance;
    }
}
```

之所以被称为饿汉，因为他想把对象提前new出来，哪怕别人是第一次获取这个类的对象的时候，就已经存在这个类了，省去创建类的开销。

### 线程安全的懒汉模式：双检锁+volatile优化

```java
public class Singleton {
    // 加了volatile防止指令重排 并且保证内存可见
    private volatile static Singleton instance = null;
    private Singleton(){}
    public static Singleton getInstance() {
        // 第一次检查：先检查是否存在对象，如果不存在对象才进入同步块
        if(instance == null) {
            // 同步块，线程安全创建实例
            synchronized (Singleton.class) {
                // 再次检查实例是否存在，不存在才真正创建实例
                if(instance == null) {
                	instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

volatile修饰变量，不会被线程本地缓存，所有线程对该对象的读写第一时间同步到主内存，保证多个线程间该对象的准确性。



#### volatile作用

- 防止指令重排序，instance = new Singleton()不是原子操作。
- 保证内存可见。当共享变量，被Volatile修饰，会保证修饰的值会被立刻更新到主存，当有其他线程要读取的时候，去内存读取新值。保证不同线程对同一个变量进行操作的可见性，当一个线程修改了变量值，这个新值对于其他线程都是立即可见的。
