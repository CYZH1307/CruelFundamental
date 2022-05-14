### C++: 为什么vector的插入操作可能会导致迭代器失效？

当capacity容量不足时，会在一块新的地址扩容到2倍，将原地址数据拷贝过去，所以迭代器失效

### vector的reserve()和resize()方法之间有什么区别？

reverse只是预留capacity 加快插入的速度

resize是改变size的大小，有可能会改变capacity
