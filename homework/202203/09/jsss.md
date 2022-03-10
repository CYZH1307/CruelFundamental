# C++: 一个空的类在instantiate的时候会有执行哪些操作

尽管空类的逻辑大小为0, 但是编译器为了保证每个实例在内存中有独一无二的地址, 会给空类隐含的加一个字节. 这样保证实例化后的空类对象有独一无二的地址.

## 测试样例

### Code

```cpp
#include <iostream>

using namespace std;

class A {};

class B {
public:
    int nums;
    virtual void set (int _nums) {
        nums = _nums;
    }
};

int main() {
    A a;
    cout << sizeof(a) << endl;
    B b;
    // 内存对齐
    cout << sizeof(b) << endl;
    cout << sizeof(B) << endl;
    return 0;
}
```

### 输出

```cpp
1
16
16
```
