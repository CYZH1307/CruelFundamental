## C++：简述 mechanism of new

`new`是C++的关键字，作用是动态申请内存并初始化，new会现申请空间，然后在调用构造函数，返回对应对象的指针

new T的原理

1. 调用 void\* operator new(sizeof T)函数，申请T类型大小的堆空间
2. 调用构造函数，完成T类对象的构造。

delete T的原理

1. 在空间上执行析构函数，完成对象中的资源清理工作
2. 调用operator delete函数释放对象的空间

new T[N]的原理

1. 调用void\* operator new[](count = sizeof(T)* N + 4(用于记录申请对象的个数)) 在operator new[]中实际调用operator new函数来完成N个对象空间的申请
2. 将空间前4个字节中填充对象的个数
3. 在申请的空间上执行N次构造函数。

delete[] 的原理

1. 从第一个对象空间之前的4个直接中取对象的个数N
2. 在释放的对象空间上执行N次析构函数，完成N个对象中资源的清理
3. 调用void operator delete[](void* p)释放空间，实际上是在operator delete[]中调用operator delete来释放空间。
