## C++: 讲一讲 std::move 和 std::forward （Effective Modern C++)

### std::move

假设变量x的类型为T，T不包含const 或者reference，例如T是一个int，实际上std::move(x)实际上等价于一个简单类型转换：
static_cast<T&&>(x)

move的主要作用时将x转换为一个匿名右值，而仅仅是简单的类型转换不会修改x本身的值，move需要配合移动构造函数或者是移动赋值函数才能实现**变更所有权**，（实测仅仅是单纯的move后访问原vector依旧是可以的）

move可以避免不必要的拷贝，性能上要更胜一筹

用以下一个简单的类来说明move的作用，开始时它仅仅包含一个拷贝构造函数和一个拷贝赋值函数。

```cpp
#include <bits/stdc++.h>
using namespace std;

struct Foo {
    Foo() = default;
    Foo(const Foo& lhs) { cout << "copy constructor\n"; }
    Foo& operator=(const Foo& lhs) {
        cout << "copy assignment operator\n";
        return *this;
    }
};

int main () {
    Foo a, b, c;
    a = b;
    a = move(c);
}
```

上述程序将输出两次“copy assignment operator”，因为程序中并没有实现移动赋值语句，a = move(c)时，由于右值也可以绑定到const Foo&因此同样会调用拷贝赋值函数。

而当我们添加一个移动赋值函数后
```cpp
#include <bits/stdc++.h>
using namespace std;

struct Foo {
    Foo() = default;
    Foo(const Foo& lhs) { cout << "copy constructor\n"; }
    Foo& operator=(const Foo& lhs) {
        cout << "copy assignment operator\n";
        return *this;
    }
    Foo& operator=(Foo&& rhs) {
        cout << "move assignment operator\n";
        return *this;
    }
};

int main () {
    Foo a, b, c;
    a = b;
    a = move(c);
}

    
```

此时程序会输出一次“copy assignment operator”和一次“move assignment operator”

总结：实际上move的意义就在于将左值转换为右值，而当move函数接受的本身就是右值时，不做任何操作。转换后的值在一个构造或者是赋值的context内，就会调用对应的移动构造或者是赋值的函数，并变更所有权。

### std::forward

std::forward通常用于完美转发，它会将输入的参数原封不动地传递到下一个函数中，即如果输入的参数是左值，那么传递给下一个函数的参数也是左值，如果输入的参数是右值，那么传递给下一个函数的参数也是右值。forward同样可以避免一些不必要的拷贝。

同样结合之前的例子说明：
```cpp
#include <bits/stdc++.h>
using namespace std;

struct Foo {
    Foo() = default;
    Foo(const Foo& lhs) { cout << "copy constructor\n"; }
    Foo(Foo&& rhs) { cout << "move constructor\n"; }
    Foo& operator=(const Foo& lhs) {
        cout << "copy assignment operator\n";
        return *this;
    }
    Foo& operator=(Foo&& rhs) {
        cout << "move assignment operator\n";
        return *this;
    }
};

int main () {
    Foo a;
    auto&& b = move(a);
    Foo c(b); // 调用复制构造函数
    Foo d(forward<Foo>(b)); // 调用移动构造函数
    return 0;
}


```
b为一个具名右值引用，直接将它作为参数传进构造函数，会作为左值调用复制构造函数，而若采用forward转发，则会正确的调用移动构造函数。
