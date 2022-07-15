
### 什么是单例模式？ C++或者Java如何实现它？（你自己最喜欢的语言即可）

单例模式就是希望一个类只有一个实例，并且这个实例在第一次被使用到的时候进行初始化。

c++中可以简单的利用static变量只会初始化一次来实现这个功能

如下所示。

```cpp
class Singleton
{
    public:
        static Singleton & getInstance()
        {
            static Singleton val;
            return val;
        }
};


```
