## C++: 什么是noexcept?（Effective Modern C++）

`noexpect`用来表示函数一定不会抛出异常。

1. noexcept 说明符要么出现在该函数的所有声明语句和定义语句，要么一次也不出现。
2. 函数指针及该指针所指的函数必须具有一致的异常说明。
3. 在 typedef 或类型别名中则不能出现 noexcept
4. 在成员函数中，noexcept 说明符需要跟在 const 及引用限定符之后，而在 final、override 或虚函数的 =0 之前
5. 如果一个虚函数承诺了它不会抛出异常，则后续派生的虚函数也必须做出同样的承诺；与之相反，如果基类的虚函数允许抛出异常，则派生类的虚函数既可以抛出异常，也可以不允许抛出异常

编译器不会检查带有 noexcept 说明符的函数是否有 throw



## Python: 在python里shallow copy和deep copy的差别?

- `copy()` 浅拷贝，指向原来的
- `deepcopy()` 深拷贝，构造新的



不可变变量，修改赋值后的新变量，不会对原变量造成任何影响

可变变量，修改赋值后的变量，会对原有的变量造成影响
