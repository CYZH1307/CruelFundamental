介绍一下单例模式的多线程安全问题

#### Lazy initialization，线程不安全

若线程1走完判断null，变成线程2执行，生成新的instance，回归线程1后，会创建另一个instance。

```java
public class Singleton {
  private static Singleton instance;
  private Singleton (){}
  
  public static Singleton getInstance() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }
}
```



#### Lazy initialization，多线程安全

- 注意 `synchronized`
- 能够在多线程中很好的工作，但是，效率很低，99% 情况下不需要同步。

```java
public class Singleton {
  private static Singleton instance;
  private Singleton (){}
  
  public static synchronized Singleton getInstance() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }
}
```



#### Eager initialization，多线程安全

- 执行效率高，但类加载的时候就初始化，浪费内存

```java
public class Singleton {  
	private static Singleton instance = new Singleton();  
	private Singleton (){}  
	public static Singleton getInstance() {  
		return instance;  
	}  
}
```



#### DCL: Double-checked locking，多线程安全

- `getSingleton()` is not synchronized but the block which creates instance is synchronized.

```java
public class Singleton {
  private volatile static Singleton singleton;
  private Singleton () {}
  public static Singleton getSingleton(){
    if (singleton == null) {
      synchronized (Singleton.class) {
        if (singleton == null) {
          singleton = new Singleton();
        }
      }
    }
  }
}
```



#### 静态内部类，多线程安全

-  `Singleton` 类被装载了，instance 不一定被初始化。因为 `SingletonHolder` 类没有被主动使用，只有通过显式调用 `getInstance` 方法时，才会显式装载 `SingletonHolder` 类，从而实例化 instance。

```java
public class Singleton {  
    private static class SingletonHolder {  
    		private static final Singleton INSTANCE = new Singleton();  
    }  
    private Singleton (){}  
    public static final Singleton getInstance() {  
    return SingletonHolder.INSTANCE;  
    }  
}
```





#### 枚举，推荐

- 还没有被广泛采用，但这是实现单例模式的最佳方法
- 不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化

```java
public enum Singleton {
  INSTANCE;
  public void whateverMethod() {
  }
}
```



