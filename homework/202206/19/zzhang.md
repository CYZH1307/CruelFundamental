虚函数是为了实现对象在运行时的动态调用，可以通过父类的指针指向子类的对象从而调用子类的方法。

实现原理： 主要是通过虚函数表和虚函数指针实现

虚函数表示便一起啊在编译阶段为类分配的静态数组，每一个使用虚函数的类都会有自己的虚函数表，这里包括子类和父类，表中存放的都是函数指针，指向该类实际访问的虚函数。

同时，编译器还添加一个指向Vtable的指针，称为是Vptr，虚函数指针在类创建实例的时候自动设置，虚函数指针的设定和重置都由类的构造、析构和拷贝赋值运算符自动完成
