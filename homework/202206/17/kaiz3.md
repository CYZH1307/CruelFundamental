1.指针指的是某块内存的地址，引用是别名。
2.指针可以指向空地址，但是引用必须有引用的对象。
3.引用只能在定义时初始化一次，之后不能改变。指针变量的值可以改变。
4.sizeof指针对象和引用对象的意义不一样。sizeof引用得到的是所指向的变量的大小，而sizeof指针是对象地址的大小。
5. 指针和引用自增(++)自减(--)意义不一样。
6.相对而言，引用比指针更安全。
7.指针是一个实体，而引用仅是个别名；
8.引用使用时无需解引用(*)，指针需要解引用；
9.引用没有 const，指针有 const；const修饰的指针不可变；
