## C++如何实现heap-only class, 如何实现stack-only class？

### heap-only class

将类的构造函数以及拷贝构造函数声明成私有，防止调用拷贝在栈上声明对象
提供一个静态成员函数，在静态成员函数中完成堆对象的创建。


### stack-only class

屏蔽掉new操作符即可，因此只需要重载new操作符并将其设置为私有访问即可。
