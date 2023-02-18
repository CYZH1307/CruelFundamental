## C++: 为什么优先选用别名声明，而非typedef？（Effective Modern C++）

- typedef不支持模板化，但是别名声明可以

例如using可以像下面这样子使用
```cpp
template<class T>
using MyAllocList = std::list<T, MyAlloc<T>>;
```

- 使用别名using的模板可以避免写“::type”后缀，对于内嵌typedef的引用经常要求添加typename前缀（总之就是选using不要用typedef）


