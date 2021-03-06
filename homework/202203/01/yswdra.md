### C++: static polymorphism 和 dynamic polymorphism 的区别

------

多态（polymorphism）指为不同数据类型实体提供统一接口

#### 静态多态

指实例所调用的具体函数（不同实例实现了同一接口的不同内容的方法）在编译期就可以确定，C++通过重载和泛型（函数模板）机制来体现静态多态这一概念。

**重载**就是在同一作用域内，通过修改同名函数的参数来实现多态。编译时编译器通过参数类型来匹配具体调用函数（若没有链接情况，则在编译器就可以确定调用函数的具体偏移），若没有匹配到适应的函数，则在编译期就产生错误。

**泛型（函数模板）**感觉可以理解为重载的更上一层概念，即函数本身实例也通过调用参数来具体化（在编译期推导）。

#### 动态多态

实例的具体类型在编译期无法确定，需要在运行期才能确定，也就是说只有在运行时才能决定调用的函数具体是哪个（同名同参但函数内容不一致）。C++通过虚函数和继承机制来实现这一概念。

#### 区别

主要是服务于程序的不同时期，静态多态在编译器，动态多态在运行期。