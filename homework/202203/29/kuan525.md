#### C++: 什么是constexpr？（Effective Modern C++）
1. constexpr函数可以用在要求编译期常量的语境中。在这样的语境中，若传入一个constexpr函数的实参是在编译期已知的，那么结果也会在编译期算出来。
2. 在调用constexpr函数时，若传入的值有一个或多个在编译期未知，则他的运作方式和普通函数无区别。