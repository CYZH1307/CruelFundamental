## 介绍一下单例模式的多线程安全问题

### 饿汉式写法：

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

### 懒汉模式

#### 线程不安全的懒汉模式

```java
public class Singleton {
    private static Singleton instance;
    private Singleton(){}
    public static Singleton getInstance() {
        if(instance == null) instance = new Singleton();
        return instance;
    }
}
```

懒汉模式"懒"的原因，是别人第一次调用的时候，发现实例是空的，然后去初始化，再赋值，后面的调用就和饿汉没有区别。

#### 懒汉模式饿汉模式对比

懒汉和饿汉对比：主要是第一次创建的开销问题，以及线程安全问题。

在电商场景，数据为热点数据，就可以在系统启动的时候，用饿汉模式提前加载，这样哪怕是第一个用户调用都不会存在创建开销，并且频繁调用也不会内存浪费。

懒汉模式则可以用在非热点数据上，例如数据不确定是否有人调用，可以用懒汉，否则用了饿汉模式，可能几个月都没有人调用，提前加载类在内存中会有资源浪费。



#### 线程安全的懒汉模式

线程不安全原因：多个线程调用不安全的懒汉模式，执行到if(instance == null) 则多个线程可能同时进入，则会出现多个线程同时new Singleton(); 则会实例化多个Singleton对象，线程不安全。

**解决方案一：直接加锁(不推荐 线程安全但是效率低)**

```java
public class Singleton {
    private static Singleton instance = null;
    // 私有构造方法，防止实例化
    private Singleton(){}
    // 静态get方法(直接加锁)
    public static synchronized Singleton getInstance(){
        if(instance == null) instance = new Singleton();
        return instance;
    }
}
```

直接加锁，为时间换空间，每次创建实例的时候，直接先锁起来，再判断，会导致严重降低系统处理速度。



**解决方案二：未加violatile的双检锁(不推荐 线程不安全)**

```java
public class Singleton {
    // 未加violatile
	private static Singleton instance = null;
	private Singleton(){}
	public static Singleton getInstance() {
		// 第一次检查：先检查是否存在对象，如果不存在对象才进入下面同步块
        if(instance == null) {
            // 同步块，线程安全的创建实例
            synchronized (Singleton.class) {
                // 第二次检查：检查实例是否存在，如果不存在才真正创建实例
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
	}
}
```

将synchronized放在内部，调用不需要加锁，只有instance为null的时候，并且是在创建对象的时候才加了锁。

但是这里也有问题：

Java指令创建对象和福祉操作分开，intance = new Singleton(); 语句分开执行。

JVM不管两个操作先后顺序，有可能JVM会为新的Singleton分配空间，然后直接赋值给instance成员，然后去初始化Singleton实例。

A B线程同时进入第一个判断，A进入synchronized块，instance==null 因此执行instance = new Singleton().

由于JVM内部优化机制，JVM先划分出一些分配给Singleton实例的空白内存，并且赋值给了instance成员，此时还没初始化对象，但是instance已经有内存块的分配不为null，A线程就离开了。但是B线程也进入了synchronized块，此时instance不是null，因此马上离开，将结果返回给线程B，但是B线程要用这个对象，却发现还没被初始化，于是错误出现。



#### 最终解决方案：双检锁+volatile优化

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



#### static 作用

- static可以用于修饰类的成员方法、类的成员变量、static代码块。可以方便在没有创建对象的情况下来调用方法/变量。
- static方法：静态方法，今天方法不依赖任何对象都可以访问，今天方法没有this，因为不依赖于任何对象，没有对象就没有This。因此静态方法中，不能访问类的非静态成员变量于非静态的成员方法，因为非静态成员方法/变量，必须依赖具体的对象才能被调用。静态方法不能访问非静态成员方法和非静态成员变量，但是非静态成员方法可以访问静态成员方程和变量。
- static变量：也称为静态变量，静态变量和非静态变量区别，静态变量为所有对象共享，在内存中只有一个副本，并且当且仅当在类初次加载的时候才会被初始化，非静态变量是对象拥有的，在创建对象的时候才被初始化，存在多个副本，各个对象拥有的副本互不影响。static成员变量初始化按照定义的顺序进行初始化。
- static代码块：可以用于形成静态代码块，优化程序性能，static代码块可以放在类中的任何地方，类中可以有多个static块，类初次加载，按照static块的顺序执行每个static块，只会执行一次。会将只需要初始化一次的初始化操作都放在static代码块当中进行。
- Java中static不会影响访问权限。并且所有的静态方法和静态变量都可以通过对象访问。static不允许修饰局部变量。



https://www.cnblogs.com/dolphin0520/p/3799052.html