# C++: 为什么优先选用别名声明，而非typedef？（Effective Modern C++）

1. 别名声明可以模板化, 而typedef不行

```cpp
template<typename T>
using MyAllocList = std::list<T, MyAlloc<T>>;

MyAllocList<Widget> lw;
```

2. 别名模板可以让人免写::type后缀，并且在模板中，对于内嵌typedef的引用经常要求加上typename前缀