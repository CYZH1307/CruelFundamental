多态分为两种。

- 一种是静态多态，即我们常见的函数重载和泛型编程，这种在编译阶段即可以确定函数的调用地址并生产代码，地址是早绑定的，静态多态也被称为静态连编。
- 动态多态的函数地址只能由运行时确定，最常见的使用方式是：初始化基类类型指向派生类的指针，并调用派生类函数

![image](https://user-images.githubusercontent.com/76064160/174720002-c0a3c1ac-d86b-45ca-a9e2-3bd0c35e37da.png)
