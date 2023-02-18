## 2022/03/03

### C++: 简述 static member variable 和 global variable 的区别

cpp静态成员变量是某个类的公共所有，它不归某个对象所有，甚至可以不需要对象来访问，存储在全局数据的分区

访问方式：

```c++
class P{
public:
    static int a;
}
P::a = 1;//1
P p1;
p1.a  = 1;//2
P *ptr = new P;
ptr->a = 1;//3
```

全局变量与静态全局变量的区别：静态全局变量的作用域为所在文件，全局变量作用域为所有变量