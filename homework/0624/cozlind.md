 **0624 用熟悉的实现支持多线程的单例模式，并列举优缺点** 

```c++
//eager singleton

class EagerSingleton{
	private static EagerSingleton instance = new HungrySingleton();
    public static EagerSingleton GetInstance(){
        return instance;
    }
}
```

饿汉式单例本身就是线程安全的，但未使用的情况下占用空间。并且调用GetInstance() 的对象和instance本身被初始化的顺序不确定可能冲突。

```c++
// lazy singleton
class LazySingleton{
    private static LazySingleton instance;
    static std::mutex m;
    public static LazySingleton GetInstance(){
        if (instance == NULL){
			std::lock_guard<std::mutex> lock(m);
            if (instance == NULL){
                instance = new LazySingleton();
            }
        }
        return instance;
    }
}
```

懒汉式单例较为节约资源，第一次使用时才创建单例对象。