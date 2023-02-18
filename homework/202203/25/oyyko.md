## C++: 为什么优先选用别名声明，而非typedef？

typedef 不支持模版化 而using可以



typedef声明函数指针的时候比较难懂

using 比较好理解

```cpp
typedef void(*FP)(int ,double);   
using FP = void(*)(int ,double); 
```



typedef 需要加`::type` 而using不用

而在模版内使用typedef的类型来创建变量的时候，需要加上一个`typename`来使得编译器理解这是一个类型。而using则不需要。

```cpp
template<class T>
class Foo {
private:
    typename MyType<T>::type bar;//必须加上typename，否则编译器不知道其是个类型
};


template<class T>
class Widget {
private:
    MyType<T> bar;
```

