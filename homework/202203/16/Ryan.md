# C++: 如何实现heap-only class，如何实现stack-only class
### heap-only

实现这种类，就是让类不会在栈上出现，即取消掉类的析构函数的自动调用，将析构函数声明为private即可。

### stack-only

实现这种类，就是让类无法在堆上出现，只需将new操作声明为private。
