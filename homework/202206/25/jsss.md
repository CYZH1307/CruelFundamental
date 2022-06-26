# C++: new/delete和malloc/free之间有什么关系？delete和delete[]有什么区别？使用malloc申请的内存能否通过delete释放？使用new申请的内存能否使用free释放?

`new`和`delete`是c++关键字, 用于动态构造和释放对象, 其底层实现中会调用`malloc`和`delete`. 

## 联系和区别

1. new是操作符(cpp关键字)，而malloc是c语言的库函数(cstdlib)。
2. new在调用的时候先分配内存，在调用构造函数，释放的时候调用析构函数；而malloc没有构造函数和析构函数。
3. malloc需要给定申请内存的大小，返回的指针(void *)需要强转；new会调用构造函数，不用指定内存的大小，返回指针不用强转。
4. new可以被重载(operator new)；malloc不行
5. new分配内存更直接和安全。
6. new发生错误抛出异常(bad_alloc)，malloc失败返回值为NULL
7. new支持数组, 使用new[]和delete[]支持.
8. new和delete在自由存储区上动态申请和分配内存, malloc和free在操作系统堆上动态申请和分配内存
9. new和delete可以调用malloc和free, 反之则否.

delete和delete[]的区别是delete[]释放的是数组对象的内存空间以及调用其析构函数.

使用malloc申请的内存通过delete释放可能会造成内存泄露. 使用new申请的内存使用free释放可能会造成内存泄漏.
