# C++中struct和class的区别，  C语言struct和C++ struct的区别？

### C++中struct和class的区别
区别如下：

    1.struct的成员默认访问权限是public，class则是private；
    2.struct的默认继承关系是public，class则是private；
    3.struct不可以用于定义模板参数，class可以。

### C语言struct和C++ struct的区别

    1.组成不同：C结构体不能有成员函数，C++结构体可以。
    2.权限不同：C结构体所有成员对外界透明，可认为是public；C++结构体则有public/protect/private三种。
    3.继承不同：C结构体没有继承的概念；C++结构体类似于class，有继承，也有虚函数。
