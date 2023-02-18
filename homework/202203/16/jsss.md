# C++: 如何实现heap-only class，如何实现stack-only 

## heap-only class

只能在堆上申请使用的话, 需要限制其在栈上使用. 主要思路是利用cpp的**RAII**机制. RAII机制保证对象在使用前初始化, 在离开作用域的时候析构释放资源. 这样我们可以将`析构函数私有化`. 我们通过`new`可以初始化对象. 而利用`public成员函数手动实现析构`. 即可以在堆上使用. 而由于析构函数是私有的, 因此RAII机制无法析构对象, 因此禁止在栈上申请该对象. (如果私有化构造函数, 那么无法通过`new`来构造使用)

```cpp
#include <iostream>

using namespace std;

class A {
private:
    ~A () {}
public:
    A () {}
    void free() {
        ::free(this);
        cout << "free success~\n";
    }
};

int main() {
    // heap only
    A* a = new A;
    a -> free();
    // stack error : variable of type 'A' has private destructor
    // auto b = A();
    return 0;
}


/*
free success~
*/
```

## stack-only

仅在栈上的化就比较直接, 只需限制`new`运算符即可, `new`运算符会调用`void operator new (size_t size)`函数. 因此对该类重载`operator new`函数, 并将其私有化.

```cpp
#include <iostream>
#include <cstdlib>

using namespace std;

class A {
private:
    static void* operator new (size_t size) {
        return malloc(size);
    }
public:
    A () {}
    ~A () {
        cout << "free A\n";
    }
};

int main() {
    // stack only
    A a;
    // heap error: 'operator new' is a private member of 'A'
    // auto b = new A;
    return 0;
}

/*
free
*/
```
