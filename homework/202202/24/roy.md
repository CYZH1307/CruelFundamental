# C++: virtual function

虚函数可以分为纯虚函数和虚函数.

## 纯虚函数

### 格式

```cpp
virtual void func() = 0;
```
### 特性

1. 含有纯虚函数的类称为**抽象类**, 这种类无法实例化. 且继承它的类如果没有实现纯虚函数的话其还是**抽象类**.
2. **抽象类**的语义类似于某些语言中"接口"的语义, 只有继承类全部实现了该抽象类定义的纯虚函数, 即可认为继承类"实现"了抽象类"接口"语义.

## 虚函数

虚函数是cpp**动态多态**的关键. cpp的多态分为静态多态和动态多态.
- 静态多态是如函数的重载、泛型模板等, 其发生在编译期. 
- 动态多态是动态类型配合虚函数导致的运行时多态.

### 格式

```cpp
virtual void func();
```

### 特性

1. 含有虚函数的类会产生**虚函数表**, 其中每个表项都是**函数指针**, 指向具体的函数. 在构造对象时, 对象会保存**虚函数表指针**, 其指向虚函数表.
2. 虚函数配合**动态类型**会产生多态的效果.
3. 虚函数的绑定是推迟到运行时, 但虚函数的**参数绑定是在编译期**的, 目的是加快执行速度, 因此虚函数带有默认参数的情况应该避免或保证默认参数一致, 否则会出现不符合预期的结果.

```cpp
#include <iostream>

using namespace std;

class Base {
public:
    void say() {
        cout << "Base" << endl;
    }
    virtual void func(int i = 0) {
        cout << "This is Base Class. i = " << i << endl;   
    }
};

class Derived : public Base {
public:
    virtual void func(int i = 1) {
        cout << "This is Derived Class. i = " << i << endl;   
    }
};


int main() {
    // p的静态类型是Base
    Base* p = nullptr;
    // 静态绑定, 且该函数不调用this, 因此可以正确执行
    p -> say();

    // p的静态类型是Base, 动态类型是Base
    p = new Base;
    p -> func();

    // p的静态类型是Base, 动态类型是Derived
    p = new Derived;
    // 动态类型配合虚函数, 产生运行时动态多态的效果
    p -> func();

    return 0;

/* 输出
Base
This is Base Class. i = 0
This is Derived Class. i = 0
*/
}
```
