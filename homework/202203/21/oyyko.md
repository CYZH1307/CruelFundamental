# C++中struct和class的区别， C语言struct和C++ struct的区别？

## C++中struct和class的区别

1

class用在模版里面可以替换typename 例如

```cpp

template <class A>
A add(A x, A y)
{
    return x + y;
}

int main()
{
    cout << add("aaa"s, "bbb"s);
}
```

而struct不可以用作这个用途。

2

class默认访问权限是private，而struct是public

3

作为父类的时候，struct默认是public继承，class默认是private继承



其他方面没有区别。但一般把struct用于POD类型组成的结构，而class用于较复杂的类型。



## C语言struct和C++ struct的区别？

1

C语言里面，使用struct的时候需要加关键字，而C++不用。

例如树的结点为Node

C语言里面声明一个节点需要： `struct Node root;`

C++里面只需要:`Node root;`

2

前面说到C++的struct实际上和class是差不多的。因此面向对象的一些特点都是C++的struct比C语言多的特性。例如可以继承，可以有成员函数（C语言里面需要用函数指针作为成员变量才行），可以直接初始化成员变量，可以拥有访问控制权限public，private，protected，可以有静态成员函数和静态成员变量，拥有构造函数和析构函数。

并且`sizeof(一个空结构体)`

在C语言里面是0

在C++里面是1 因为C++规定空对象的大小也至少是1



