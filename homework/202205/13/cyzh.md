## 2022/05/13

### C++: 为什么vector的插入操作可能会导致迭代器失效？

当vector的剩余容量没有时，插入一个元素会引起vector的扩容操作，请求新的地址，所以原来的迭代器就失效了

### vector的reserve()和resize()方法之间有什么区别？

`reserve()` 是预留空间，在`reserve()` 之后再`push_back()`会快一点，改变的是`_Myend`

`resize()`是改变`vector`的大小,改变的是`_Mylast`