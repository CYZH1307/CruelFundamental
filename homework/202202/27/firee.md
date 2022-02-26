#### C++:简述vtable



先看一下调用的内存示意图

![img](https://img-blog.csdnimg.cn/img_convert/92a626616737e8b4a1a80af0f6c1b7cb.png)



考虑下面函数fun怎么样去编译：

```c++
class A
{
public:
    int numa;
    void func1() {}
    virtual void func2() {}
};

class B : public A
{
    int numb;
};

void fun(A *a)
{
    a->func1();
    a->func2();
}
```

如何去编译需要满足以下条件

1.代码是写死的

2.如果传入一个B类指针，也能实现

再来看一下继承后类的内存排布

实际上如果B继承了A,那B的实例中一定有一块以A的方式排布的内存



