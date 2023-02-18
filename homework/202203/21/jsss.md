# C++中struct和class区别，C语言struct和C++struct区别

## C++中struct和class区别

- 默认访问控制权限不同. struct默认访问控制权限为`public`, class默认访问控制权限为`private`
- 默认继承权限不同. struct默认继承权限为`public`, 而class默认继承权限为`private`
- class关键字可以用于定义模板, 基本等同于`typename`. 但指定模板中申明的类型时, 只能使用`typename vector<T>::value_type`


## C语言struct和C++struct区别

- cpp的`struct`无需只用`typedef`就可以定义对象
- cpp的`strcut`可以用于成员函数, 可以指定访问控制权限, 可以用于静态成员函数和静态数据成员.
- cpp的`struct`可以继承, 实现多态.