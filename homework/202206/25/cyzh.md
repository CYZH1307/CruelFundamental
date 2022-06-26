## 2022/06/25

### C++: new/delete和malloc/free之间有什么关系？delete和delete[]有什么区别？使用malloc申请的内存能否通过delete释放？使用new申请的内存能否使用free释放?



#### new/delete和malloc/free之间有什么关系？

- `new`出来的对象需要用`delete`来删除
- `malloc`的对象需要用`free`删除，`malloc`的返回值为`(void*)`



#### new和malloc的区别

- `new/delete`是`C++`关键字，需要编译器支持，`malloc/free`是库函数，需要头文件支持`c`。
- `new`不需要指定大小，`malloc`需要指定大小

#### delete和delete[]有什么区别？

- delete只会调用一次析构函数，而delete[]会调用每一个成员的析构函数。

#### 使用malloc申请的内存能否通过delete释放？

- 理论上可以释放，delete会调用析构函数，但是可能发送不可预测的错误

#### 使用new申请的内存能否使用free释放?

- 同理对于基本类型可以，但是对于复杂的结构不行

