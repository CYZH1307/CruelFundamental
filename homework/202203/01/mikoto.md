## 静态多态和动态多态的区别

静态多态：函数重载，编译器在编译期间完成的，编译器会根据是参类型来推断该调用哪个函数，如果有对应函数，就调用，否则报错。
动态多态：派生类重写基类犯法，然后用基类引用或指针指向派生类对象，调用时动态绑定。
