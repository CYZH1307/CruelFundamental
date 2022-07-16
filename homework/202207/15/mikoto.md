## 什么是单例模式，C++或JAVA怎么实现?

单例模式就是确保一个类只有存在一个实例化的对象，并提供一个全局的访问点


从C++ 11 开始，保证静态变量只会初始化一次，因此可以利用这一点来实现懒汉or饿汉式单例模式

注意要将类的构造和复制构造函数私有或者删除掉

实际上atmotic原子库也提供了类似once_flag的东西，但实际上利用C++ 11后的静态变量即可

推荐使用懒汉式单例模式

```
class Singleton {
    public:
        Singleton& getInstance() {
            static Singleton instance_;
            return instance_;
        }
    private:
        Singleton() = default;
        Singleton(const Singleton &) = delete;
};
```
