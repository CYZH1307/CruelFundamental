## 如何保证单例模式只有唯一实例？你知道的都有哪些方法？
主要是通过将类的构造方法定义为私有方法，这样其他地方的代码无法通过该类的构造方法来实例化该类的对象，只能通过该类提供静态方法来获得类的唯一实例。

单例模式的实现方法主要有两种，饿汉式和懒汉式

饿汉式是提前先创建好实例，而懒汉式是在需要使用时才创建，前者线程安全，后者则需要上锁保证安全。

饿汉式单例模式的实现：
```
class SingleTon {
    SingleTon() {}
public:
    stait SingleTon* getInstance() {
        static SingleTon instance;
	return &instance;
    }
};
```

线程安全的懒汉式单例模式实现：
```
class SingleTon {
    static SingleTon* instance;
    SingleTon() {}
public:
    static SingleTon* getInstance() {
        if (!instance) {
              unique_lock<mutex> lock(my_mutex);
	      if(!instance) {
	           instance = new SingleTon();
	      }
	}
	return instance;
    }
}
```
