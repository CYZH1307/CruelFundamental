
### C++： volatile关键字作用


 翻译为 易失性

参考：https://zhuanlan.zhihu.com/p/33074506

因此我们讲，在 C/C++ 中，对 `volatile` 对象的访问，有编译器优化上的副作用：

* 不允许被优化消失（optimized out）；
* 于序列上在另一个对 `volatile` 对象的访问之前。

这里提及的「不允许被优化」表示对 `volatile` 变量的访问，编译器不能做任何假设和推理，都必须按部就班地与「内存」进行交互。因此，上述例中「复用寄存器中的值」就是不允许的。

需要注意的是，无论是 C 还是 C++ 的标准，对于 `volatile` 访问的序列性，都有单线程执行的前提。其中 C++ 标准特别提及，这个顺序性在多线程环境里不一定成立。


文章很长而且很深奥，评论区也有很多没看懂的，慢慢看


这里举个针对优化的小例子

```c++
#include<iostream>
using namespace std;


void changeVal(void *p)
{
    *((int *)p)=2;
}

int main()
{
    const int a = 1;
    volatile const int b = 1;
  
    changeVal((void *)&a);
    changeVal((void *)&b);
    cout<<a<<' '<<b<<endl;

    return 0;
}
```
