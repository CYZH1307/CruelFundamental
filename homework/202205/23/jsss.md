# C++: 讲一讲 std::move 和 std::forward

`move`和`forward`是`cpp11`引入的两个模板函数, `move`配合移动语义对应的函数(移动构造, 移动赋值)可以减少不必要的拷贝, 而`forward`可以完美的保留参数的特性, 从而实现预期的行为.

<!--more-->

## 引用

从`cpp11`以来, 引用可以分为`左值引用`、`右值引用`和`通用引用`. `左值引用`和`右值引用`分别可以绑定到`左值`和`右值`上, 从而在函数调用过程中减少不必要的拷贝. `通用引用`用于泛型编程中, 其可以绑定到左值上, 也可以绑定到右值上.

### 引用折叠

`cpp`规定不存在指向引用的引用. 在泛型编程中, 如果模板函数指定参数类型为`T&& param`, 而传入的参数可以是左值也可以是右值, 因此`T&&`作为`通用引用`就需要利用引用折叠规则进行参数类型的适配.

1. 如果参数`param`为左值或左值引用, 那么`T`就被推导为`T&`, 这样参数类型就变成了`T& &&param`, 引用折叠规则规定这种情况下`T`为左值引用.
2. 如果参数`param`为右值, 那么`T`就被推导为`T`, 这样参数类型就变成了`T&& param`.
3. 如果参数`param`为命名右值引用, 那么`T`被推导为`T&`, 因为命名右值引用为左值, `T &&`无法绑定到左值上去, 因此推导为`T&`.
4. 如果参数`param`为匿名右值引用, 那么`T`被推导为`T`, 因为匿名右值引用为右值.


### Code

```cpp
#include <iostream>

using namespace std;

template<typename T>
void testType(T&& param) {
    if(is_rvalue_reference<decltype(param)>::value)
        cout<<"param is rvalue reference\n";
    else 
        cout<<"param is lvalue reference\n";
}

int main() {
    int val = 10;
    int& L = val;
    int&& R = 123;

    // 左值
    testType(val);
    // 左值引用
    testType(L);
    // 命名右值引用
    testType(R);
    // 匿名右值引用
    testType(static_cast<int&&>(val));
    // 右值
    testType(10086);
    return 0;
}
```

### Result

```bash
param is lvalue reference
param is lvalue reference
param is lvalue reference
param is rvalue reference
param is rvalue reference
```

## move

`move`函数无条件的将传入参数转换为右值引用返回. 由于函数返回右值引用为匿名右值引用, 因此其可以配合`移动构造函数`和`移动赋值函数`实现移动语义.

### move源码


```cpp
  // GNU cpp-11.2.0
  /**
   *  @brief  Convert a value to an rvalue.
   *  @param  __t  A thing of arbitrary type.
   *  @return The parameter cast to an rvalue-reference to allow moving it.
  */
  template<typename _Tp>
    _GLIBCXX_NODISCARD
    constexpr typename std::remove_reference<_Tp>::type&&
    move(_Tp&& __t) noexcept
    { return static_cast<typename std::remove_reference<_Tp>::type&&>(__t); }
```

`move`函数的源码很简单. 函数的参数类型为`通用引用`, 返回值类型为`constexpr typename std::remove_reference<_Tp>::type&&`, 其中`std::remove_reference<_Tp>::type`是偏特化模板, 类似于`traits`, 提取的是`_Tp`的去掉引用后的类型(如int&为int, char&&为char). 函数的主体为一条类型转化的语句: `static_cast<typename std::remove_reference<_Tp>::type&&>(__t)`. 其意很明确: 将参数转换为对应类型的右值引用并返回.


### 使用样例

`move`函数的过程很简单, 就是简单的类型转换. 其可以认为是一个小的语法糖, 主要配合`移动构造函数`和`移动赋值函数`实现移动语义.

```cpp
#include <iostream>
#include <string>

using namespace std;

class Array {
public:
    Array () : _size(0), _ptr(nullptr) {}

    Array (int len) {
        _size = len;
        _ptr = new int[_size];
    }
    
    // copy-cotr
    Array (Array& a) {
        _size = a._size;
        _ptr = new int[_size];
        for (int i = 0; i < _size; i ++ )
            _ptr[i] = a._ptr[i];
        cout << "called copy-cotr. " << endl;
    }

    // copy operator = 
    Array& operator = (Array& a) {
        if (this == &a)
            return *this;
        if (_ptr != nullptr)
            delete _ptr;
        // copy
        _size = a._size;
        _ptr = new int[_size];
        for (int i = 0; i < _size; i ++ )
            _ptr[i] = a._ptr[i];
        cout << "called copy operator = " << endl;
        return *this;
    }

    // move cotr
    Array (Array&& a) {
        _size = a._size;
        _ptr = a._ptr;
        a._size = 0;
        a._ptr = nullptr;
        cout << "called move cotr." << endl;
    }

    // move operator = 
    Array& operator = (Array&& a) {
        if (this == &a)
            return *this;
        if (_ptr != nullptr)
            delete _ptr;
        // move
        _size = a._size;
        _ptr = a._ptr;
        a._size = 0;
        a._ptr = nullptr;        
        cout << "called move operator = " << endl;
        return *this;
    }

    // de-cotr.
    ~ Array () {
        delete _ptr;
        cout << "called de-cotr." << endl;
    }
    
    void printInfo() {
        cout << "size of array is " << _size << endl;
    }

private:
    // size and pointer
    int _size;
    int* _ptr;
};


int main() {
    Array arr(10);
    Array dst;

    dst = move(arr);

    dst.printInfo();
    arr.printInfo();

    return 0;
}


/*
called move operator = 
size of array is 10
size of array is 0
called de-cotr.
called de-cotr.
*/
```

### 总结

1. `move`函数无法移动任何东西, 其只是无条件的返回参数的匿名右值引用.
2. `move`需要配合`移动构造函数`和`移动赋值函数`才能正确实现`移动语义`.

## forward

### 源码

```cpp
/**
*  @brief  Forward an lvalue.
*  @return The parameter cast to the specified type.
*
*  This function is used to implement "perfect forwarding".
*/
template<typename _Tp>
_GLIBCXX_NODISCARD
constexpr _Tp&&
forward(typename std::remove_reference<_Tp>::type& __t) noexcept
{ return static_cast<_Tp&&>(__t); }

/**
*  @brief  Forward an rvalue.
*  @return The parameter cast to the specified type.
*
*  This function is used to implement "perfect forwarding".
*/
template<typename _Tp>
_GLIBCXX_NODISCARD
constexpr _Tp&&
forward(typename std::remove_reference<_Tp>::type&& __t) noexcept
{
    static_assert(!std::is_lvalue_reference<_Tp>::value, "template argument"
        " substituting _Tp must not be an lvalue reference type");
    return static_cast<_Tp&&>(__t);
}
```

`forward`可以有区别转发左值和右值, 其主要特点是通过函数重载实现不同参数类型的分别处理. 如果`__t`是左值那么进入第一个版本, 如果`__t`是右值那么进入第二个版本. 通过源码可以发现, 只有`_Tp`为左值引用时, 参数`__t`才会被转发成`_Tp`的左值引用. 否则就是`_Tp`类型的右值引用. 

那么为什么需要`forward`函数呢? 如果我们希望根据参数引用的类型实现不同的功能, 或者在函数调用中保留原始参数的引用特性, 就必须使用`forward`函数进行完美转发.

### 使用样例

#### Code

```cpp
#include <iostream>
#include <string>

using namespace std;

class Array {
public:
    Array () : _size(0), _ptr(nullptr) {}

    Array (int len) {
        _size = len;
        _ptr = new int[_size];
    }
    
    // copy-cotr
    Array (Array& a) {
        _size = a._size;
        _ptr = new int[_size];
        for (int i = 0; i < _size; i ++ )
            _ptr[i] = a._ptr[i];
        cout << "called copy-cotr. " << endl;
    }

    // copy operator = 
    Array& operator = (Array& a) {
        if (this == &a)
            return *this;
        if (_ptr != nullptr)
            delete _ptr;
        // copy
        _size = a._size;
        _ptr = new int[_size];
        for (int i = 0; i < _size; i ++ )
            _ptr[i] = a._ptr[i];
        cout << "called copy operator = " << endl;
        return *this;
    }

    // move cotr
    Array (Array&& a) {
        _size = a._size;
        _ptr = a._ptr;
        a._size = 0;
        a._ptr = nullptr;
        cout << "called move cotr." << endl;
    }

    // move operator = 
    Array& operator = (Array&& a) {
        if (this == &a)
            return *this;
        if (_ptr != nullptr)
            delete _ptr;
        // move
        _size = a._size;
        _ptr = a._ptr;
        a._size = 0;
        a._ptr = nullptr;        
        cout << "called move operator = " << endl;
        return *this;
    }

    // de-cotr.
    ~ Array () {
        delete _ptr;
        cout << "called de-cotr." << endl;
    }
    
    void printInfo() {
        cout << "size of array is " << _size << endl;
    }

private:
    // size and pointer
    int _size;
    int* _ptr;
};

template<typename T> 
T wrap(T&& src) {
    return forward<T&&>(src);
}


void funcB(Array&& src) {
    
}


template<typename T> 
void funcA(T&& src) {
    // Candidate function not viable: expects an rvalue for 1st argument, 转发失败
    // funcB(src);
    // 转发成功
    funcB(forward<T&&>(src));
}



int main() {
    Array arr(10);

    Array dst1 = wrap(arr);
    Array dst2 = wrap(move(arr));

    dst1.printInfo();
    dst2.printInfo();

    funcA(move(dst2));

    return 0;
}
```

#### Result

```cpp
called copy-cotr. 
called move cotr.
size of array is 10
size of array is 10
called de-cotr.
called de-cotr.
called de-cotr.
```

- 我们在`main`函数里调用完美转发包装的`wrap`函数. 通过传入参数的不同实现调用不同的构造函数. 
- 在函数调用中, 由于命名右值引用是左值, 因此其无法进行期望的函数调用, 需要使用`forward`转发
- `forward`完美转发会保留参数的`const`和引用特性, 降低出错的可能.


### 总结

由于命名右值引用是左值, 因此在函数中继续转发该参数会发生不符合预期的结果出现(调用左值引用版本的代码、编译出错等等), 而`forward`可以完美转发参数的`const`特性和`引用`特性, 从而保证执行符合我们的预期, 降低出错的可能性.