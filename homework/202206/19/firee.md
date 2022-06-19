### C++: 简述虚函数类型和作用？， 虚继承

##### 虚函数：

虚函数主要用来实现多态

简单阐述一下虚函数的原理：

```c++
#include <iostream>
using namespace std;

class A
{
public:
    virtual void fun()
    {
        cout << "A" << endl;
    }
};

class B : public A
{
    string B = "B";
    void fun() override
    {
        cout << B << endl;
    }
};

void fun(A *a)
{
    a->fun();
}

int main()
{
    B b;
    A a;
    fun(&b);
    fun(&a);
    return 0;
}
```



实际上，只要想办法完成fun这个函数的编译即可

对于fun(A *a),其中的a是一个地址，也就是说只要是一个传进来的地址，就能把它看作是一个A类型的变量，这里思考两个问题

1.如何让a->fun()指向正确的函数

2.如何让a->fun()使用原来的数据(string B)



1，2实际上都由虚函数表维护

对于1，这里有个比较清晰的图

![img](https://pic3.zhimg.com/80/v2-dfe4aefdee7e06cf3151b57492ed42a2_720w.jpg)

https://zhuanlan.zhihu.com/p/75172640

对于2，在虚表中也存有其内存偏移的地址。

所以fun差不多被翻译成了这样（伪代码)

```c++
void fun(A *a)//输入 b为例
{
	func = *a->vptr  //B::fun()
    this = a - *a->vptr+1 //b
    func(this)	//B::fun(b)
}
```





##### 虚继承：

参考 https://zhuanlan.zhihu.com/p/342271992