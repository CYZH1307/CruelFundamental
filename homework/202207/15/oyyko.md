单例模式(Singleton Pattern，也称为单件模式)，使用最广泛的设计模式之一。其意图是保证一个类仅有一个实例，并提供一个访问它的全局访问点，该实例被所有程序模块共享。

定义一个单例类：

1. 私有化它的构造函数，以防止外界创建单例类的对象；
2. 使用类的私有静态指针变量指向类的唯一实例；
3. 使用一个公有的静态方法获取该实例。



```cpp
template<typename T>
class Singleton
{
public:
    static T& getInstance() {
        static T t;
        return t;
    }

    Singleton(const Singleton&) = delete; 
    Singleton& operator=(const Singleton&) = delete; 
protected:
    Singleton() = default;
    ~Singleton() = default;
};
```

