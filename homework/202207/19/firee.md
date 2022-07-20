### 什么是工厂模式？

个人理解，设计模式是为了弥补在某些需求下语言的不足。

假设有一个需求，希望一个类在创建的时候能输出他的名字，如果是python，可以这样实现：


```python
class A:
    def __init__(self) -> None:
        print('A created!')
class B(A):
    def __init__(self) -> None:
        print('B created!')
  
s=B()



```


简单，只会输出B created


但如果用c++这样实现

```cpp
#include<iostream>
using namespace std;

class A
{
    public:
    A()
    {
        cout<<"A created!"<<endl;
    }
};

class B:public A
{
    public:
    B()
    {
        cout<<"B created!"<<endl;
    }
};



int main()
{
    A * s = new B();

    return 0;
}
```

则会输出A created和B created

原因很简单，c++会默认调用其父类构造函数，且无法关闭，python则可以选择性的调用父类构造函数，在这个场景下，c++可以选择用工厂模式做一层封装，增加类的生产流程

like this

```cpp
#include<iostream>
#include<string>
using namespace std;

class A
{
    public:
    A()
    {
    }
    virtual void init()
    {
        cout<<"A created!"<<endl;
    }
};

class B:public A
{
    public:
    B()
    {
    
    }
    virtual void init()
    {
        cout<<"B created!"<<endl;
    }
};

A* create(string name)
{
    A * rec;
    if (name=="A")
    {
        rec=new A();
    }
    else if(name=="B")
    {
        rec=new B();
    }
    rec->init();
    return rec;
}


int main()
{
    A * s = create("B");

    return 0;
}
```

简单总结一下就是，工厂模式(设计模式)在这里起到的作用是帮助c++完善了某类的构造流程以达到某个需求。
