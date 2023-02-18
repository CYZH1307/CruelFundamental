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

class B
{
public:
    int numb;
    virtual func3(){}
};

class C :  public B,public A
{
    int numc;
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

实际上如果C继承了A,B那C的实例中一定有一块以A的方式排布的内存,里面有数据numb和B的虚指针，下面紧接着就是A的虚指针和numa,排布的顺序取决于继承时候的写法。

a->func1();编译后，应当能使用numa，所以只要把this指针偏移到A所需要的位置即可以把这一块内存当A来使用，这个偏移发生在赋值的时候，比如

```c++
C *c=new C();
//type1
fun(c);
//type2
A *a=c;
fun(a);
```

type1等价于type2，type2只要让a=c+16即可，即a指着c里一块和A一样的内存。

这样对于func1，他只要这么运行下去就行了。

在func1的基础上，可以比较巧妙的实现func2的编译，由于func2是虚函数，他多需要两个信息

1.numc

2.C::func2();

这两个信息都包含在C的vtable中，C中的vptr(A)指向了C的vtable中A类的虚函数相关，即A类的偏移量，以及func2()；这样在fun函数中，可以同时得到函数位置，和原本的this指针，有了这俩函数就能和原来一样运行了

![img](https://img-blog.csdnimg.cn/img_convert/8b1dcb79e33b35a7d2a22703e851c368.png)

vtable中还有typeinfo，我猜测应该是后来加入的东西，加在vtable中的主要原因应该如果一个类没有虚函数，那他就没有被动态知道type的需求。

小结一下：

本来调用父类函数需要转换C->A,现在加入了虚函数，则调用虚函数需要C->A->C,维护了vtable记录中间需要的信息
