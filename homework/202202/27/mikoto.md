## C++:简述Vtable


C++ 虚函数（Virtual Function）是通过一张虚函数表（Virtual Table）来实现的，简称为V-Table。在这个表中，主要是一个类的虚函数的地址表，这张表解决了类的继承、覆盖的问题。
在有虚函数的类的实例中，这个表被分配在了这个实例的内存中，因而，当我们用父类的指针来操作一个子类的时候，虚函数表非常重要，他像地图一样指明实际应该调用的函数。

C++标准中提到，编译器必需保证虚函数表的指针存在于对象实例中最前面的位置（主要是为了保证正确取到虚函数的偏移量），这意味着我们通过对象实例的地址得到这张虚函数表，然后就可以遍历其中的函数指针，并调用相应的函数。






