### 简单说一个自己熟悉的设计模式。再说一个你项目中实际使用过的其他设计模式。

#### 设计模式

面试常考单例模式（doge

```java
public class Singleton {
	private volatile static Singleton instance = null;
    private Singleton() {}
    public static Singleton getInstance() {
        if(instance == null) {
            synchronized(Singleton.class) {
            	if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

#### 项目中实际使用的其他设计模式

观察者模式，常见的消息队列，发布订阅的方式可以看做是一种观察者模式。知识区别在于发布订阅是将能力给了消息的发布订阅中心。观察者模式是直接由被观察者去触发通知观察者。

建造者模式，创建对象的时候常用。

代理模式，正向代理反向代理。

策略模式，方便更换策略。