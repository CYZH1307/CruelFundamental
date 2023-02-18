# C++如何实现heap-only class，如何实现stack-only class

如果想要在栈上构造一个对象，需要调用该对象的构造函数，在栈帧被销毁之后调用该对象的析构函数。
使用new构造一个对象，首先使用operator new申请一片内存，然后调用placement new调用构造函数。

所以如果想要实现heap-only class的话，只需要将这个对象的析构函数置为private，
实现stack-only的话，只需要将operator new置为private即可。
