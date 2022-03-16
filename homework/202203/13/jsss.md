# C++: constructor和desctructor能virtual吗

1. **构造函数和内联函数必须是非虚的.**
    构造对象的时候. 先申请一块内存, 然后进行实例化. 如果构造函数是虚的, 则需要找到虚指针指向的虚函数对应的函数指针. 而此时对象内存还未初始化, 无法找到虚函数指针. 另外从逻辑上来说, 构造函数无需是虚的. 因为构造子类的时候, 编译器先构造父类. 无需通过父类的指针手动调用.

2. **析构函数可以是虚函数, 甚至可以是纯虚函数(纯虚函数也可以有函数体). 通常父类的析构函数是虚函数, 保证多态情况下子类对象可以被正确析构.**
    当一个类用做基类的时候, 其析构函数需要设置成虚的. 保证父类指针指向子类对象时, 其能先析构子类再析构父类. 确保内存不泄露.