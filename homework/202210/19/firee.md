### 简单说一个自己熟悉的设计模式。再说一个你项目中实际使用过的其他设计模式。

单例模式：

```c++
class Singleton {
public:
  static Singleton& getInstance(){
    static Singleton instance;
    return instance;
  }
private:
Singleton ()= default;
~Singleton ()= default;
Singleton (const Singleton &)= delete;
Singleton & operator=(const Singleton &)= delete;
};
```


工厂模式其实用的比较多，因为对类对象创建做了统一的入口，所以很多功能可以用工厂模式实现。
