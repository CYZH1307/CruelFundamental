# C++: 简述 vtable

**虚函数表**是含有虚函数的类所拥有的. 它配合动态绑定技术, 可以实现运行期多态.

## 特点

- 每个含有虚函数的类均会有一份类共享的虚函数表.
- 每个对象含有虚函数表指针, 一般会在对象内存地址的开头. 这样的好处是通过父类指针调用子类对象的虚函数时, 能在子类对象的前sizeof(父类)区域中拿到子类的虚函数表指针, 正确的实现多态.
- 虚函数表是指针数组, 其表项为函数指针, 指向类所对应的虚函数.
- 虚函数表的产生是在编译器, 并且编译器会为有虚函数的对象添加虚函数指针.
- 动态绑定是在运行期. 其依赖于虚函数指针和虚函数表.