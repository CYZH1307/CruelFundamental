## C++: 简述static member variable 和 global variable的区别

### 静态成员变量

静态成员变量属于整个类，而不属于某个对象，因此必须在类内声明，在类外初始化。
仅支持在本文件中通过 ** 类名::变量名 ** 访问，不支持跨文件访问。


### 全局变量

全局变量可初始化也可以不初始化，未初始化的全局变量存储在BSS段，并被自动初始化为0。
全局变量可以不仅可以在本文件内访问还可以通过`extren`扩展作用域