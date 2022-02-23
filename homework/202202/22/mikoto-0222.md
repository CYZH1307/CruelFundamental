## cpp: volatile和atomic的区别

### volatile

volatile: 关键字，意味着程序可能受到程序之外的因素影响，结合例子比较好说明
```
volatile int *p = ????;
int a, b;
a = *p;
b = *p;
```

使用说明：

一个非volatile值到volatile值的转换是无效果的。
要使用volatile语义访问非volatile对象，必须先将它的地址转换成指向volatile类型的指针，再通过指针访问该对象。
任何通过非volatile左值结果，对拥有volatile限定类型对象的尝试度或者写都会导致未定义行为

```
volatile int n = 1; // volatile 限定类型
int* p = (int*)&n;
int val = *p; // 未定义行为，这里是通过指针去访问volatile限定类型对象
```


如果不考虑volatile关键字，p是一个简单的int\*指针，下面两个赋值语句，只需要从内存中读取一次，因为读取后CPU寄存器中有这个值，之后直接复用该值，编译器会把两次访存的操作优化成一次。但是上述优化，是基于如果我们认为：我们没有在代码里改变p指向的内存地址的值，那么这个值一定不改变。

然而由于MMIP（Memory mapped I/O）的存在，假设p指向的内存是一个硬件设备，那么从p指向的内存读取数据伴随着可观测的副作用：硬件状态的修改。如此一来，若原代码的意愿是将硬件设备返回的两个连线int值分别保存在a 和 b中，由于编译器的优化，程序的行为会变得不符合预期。

总结来说，被volatile修饰的变量，在对其进行读写操作时，会引发一些可观测的副作用，而这些副作用是由程序之外的原因决定的。可以用volatile禁用优化


### atomic

std::atomic为C++ 11封装的原子数据类型。

原子数据类型：从功能上来看，简单地说，原子数据类型不会发生数据竞争，可以直接用在多线程中而不必我们用户对其进行添加互斥资源锁的类型。

atomic模板的实例化好全特化定义一个原子类型。若一个线程写入原子对象，同时另一线程从它读取，则行为良好定义。
对原子对象的访问可以建立线程间同步，并按照std::memory_order所对非原子内存访问定序
std::atomic即不可复制也不可移动。

示例：使用atomic_int 类型

```
#include <bits/stdc++.h>
using namespace std;

atomic_int iCount(0);

void threadfunc1() {
    for(int i = 0; i < 10; i ++) {
        printf("iCount: %d\n", iCount ++);
    }
}

void threadfunc2() {
    for(int i = 0; i < 10; i ++) {
        printf("%iCount: %d\n", iCount --);
    }
}

int main () {
    list<thread> lt;
    for(int i = 0; i < 10; i ++) {
        lt.push_back(thread(threadfunc1));
    }
    for(int i = 0; i < 10; i ++) {
        lt.push_back(thread(threadfunc2));
    }
    for(auto&& i: lt) {
        i.join();
    }
    int x = iCount.load(memory_order_relaxed);
    printf("finally iCount: %d\n", x);
}
```
最终输出x结果为0

使用普通 int类型

```
#include <bits/stdc++.h>
using namespace std;

int iCount(0);

void threadfunc1() {
    for(int i = 0; i < 10; i ++) {
        printf("iCount: %d\r\n", iCount ++);
        //iCount ++;
    }
}

void threadfunc2() {
    for(int i = 0; i < 10; i ++) {
        printf("iCount: %d\r\n", iCount --);
        //iCount --;
    }
}

int main () {
    list<thread> lt;
    for(int i = 0; i < 20; i ++) {
        lt.push_back(thread(threadfunc1));
    }
    for(int i = 0; i < 20; i ++) {
        lt.push_back(thread(threadfunc2));
    }
    for(auto&& i: lt) {
        i.join();
    }
    
    //int x = iCount.load(memory_order_relaxed);
    printf("finally iCount: %d\r\n", iCount);
}
```
结果不保证是0，通常不是0


## Python：什么是鸭子类型

鸭子模型 常见说法：当一只鸟走起来像鸭子，游泳起来像鸭子，叫起来也像鸭子，那么这只鸟就可以被称为鸭子

鸭子类型（duck typing）在程序设计中是动态类型的一种风格，这种风格中，一个对象有效的语义，不是由继承自特定的类型或者实现特定的接口，而是由“当前方法和属性的集合”决定

鸭子类型中，关注点主要在于对象的行为能做什么；而不是关注对象所属的类型。比如C++中函数参数类型固定，函数实现的方法固定，而python中函数参数可以不写类型名，实现的方法固定。

