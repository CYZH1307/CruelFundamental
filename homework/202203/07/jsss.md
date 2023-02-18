# C++: class initialization 的顺序。如果A class里面有一个member是B class的instance，A class 从class D derived得到，在创建A的instance的时候，initialization的顺序是怎样的。

## 继承的初始化顺序

继承的情况是先初始化基类, 然后再初始化子类. 析构是先析构子类再析构基类. 这样子类的内存布局是先父类再子类. 可以使用指向父类的指针指向子类. 从而利用虚函数指和虚函数表实现多态.

## 组合的初始化顺序

类里面含有的成员的初始化顺序是按照其定义的顺序出初始化的. 并且其初始化发生在类的构造之前.

## 继承和组合的初始化顺序

参考[这个问题的回答](https://stackoverflow.com/questions/2669888/initialization-order-of-class-data-members). 列出cpp的初始化顺序.

1. 按照子类定义中虚基类出现的次数调用其构造函数
2. 按照子类定义中基类出现的次数调用其构造函数
3. 非静态数据成员应按照它们在类定义中声明的顺序进行初始化
4. 最后指向当前类的构造函数

## 测试样例

### Code

```cpp
#include <iostream>

using namespace std;

class D {
public:
    D () {
        cout << "init D" << '\n';
    }
    ~D () {
        cout << "free D" << '\n';
    }
};

class B {
public:
    B () {
        cout << "init B" << '\n';
    }

    ~B () {
        cout << "free B" << '\n';
    }
};

class A : public D {
public:
    B b;
    A () : b() {
        cout << "init A" << '\n'; 
    }
    
    ~A () {
        cout << "free A" << '\n';
    }
};

int main() {
    A a;
    cout << "end" << "\n";
    return 0;
}
```

### 输出

```cpp
init D
init B
init A
end
free A
free B
free D
```
