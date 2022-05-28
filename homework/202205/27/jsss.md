# C++: shared_ptr是如何实现的？

我们实现一个简易的`shared_ptr`模板类. `shared_ptr`使用指针用来管理用户指定的资源, 并提供和指针相同的行为: `->`, `*`操作符, 并支持拷贝赋值和拷贝构造. 并提供线程安全的资源访问和释放特性.

为了实现`shared_ptr`, 我们需要记录**引用计数**, 通过构造函数和拷贝构造等函数来维护这个引用计数即可. 而关键是如何记录这个引用计数. 这个**引用计数**应该对所有`shared_ptr`对象都是**可见**的, 这样才能正确记录. 为了实现对所有对象都可见, 我们可以在`shared_ptr`类中记录指向该**引用计数的指针**.

## 代码实现

```cpp
// 不可拷贝基类
class noncopyable {
public:
    noncopyable(const noncopyable&) = delete;
    noncopyable operator=(const noncopyable&) = delete;
protected:
    noncopyable() = default;
    ~noncopyable() = default;
};

// 引用计数控制块
class refCount : noncopyable{
public:
    refCount () {
        _cnt.store(0);
    }

    refCount(int cnt) {
        _cnt.store(cnt);
    }

    void decRefCount() {
        _cnt.fetch_sub(1);
    }

    void incRefCount() {
        _cnt.fetch_add(1);
    }

    int getRefCount() {
        return _cnt.load();
    }

private:
    atomic_int _cnt;
};


// 简易版shared_ptr模板类
template <typename T>
class SharedPtr : noncopyable {
public:
    using value_type     = T;
    using pointer_type   = T*;
    using reference_type = T&;

    // 构造函数
    SharedPtr () : _ptr(nullptr), _ref(nullptr) {}
    SharedPtr (pointer_type ptr) : _ptr(ptr), _ref(new refCount(1)) {}
    SharedPtr (const SharedPtr& other) {
        _ptr = other._ptr;
        _ref = other._ref;
        this -> increase();
    }

    // 拷贝构造
    SharedPtr& operator = (const SharedPtr& other) {
        if (this == &other)
            return *this;
        // 先减当前的引用计数
        this -> decrease();
        // 拷贝赋值
        _ptr = other._ptr;
        _ref = other._ref;
        // 增加引用计数
        this -> increase();
    }


    // 析构函数
    ~SharedPtr() {
        cout << "~SharedPtr() called" << endl;
        decrease();
    }  

    int use_count() {
        return _ref -> getRefCount();
    }

    // Dereferences the stored pointer. The behavior is undefined if the stored pointer is null.
    pointer_type operator -> () {
        return this -> _ptr;
    }

    reference_type operator * (){
        return *(this -> _ptr);
    }

    pointer_type get() {
        return this -> _ptr;
    }

    operator bool (){
        return this -> _ptr != nullptr;
    }

private:
    // 内部实现, 不对外开放
    void decrease() {
        if (_ptr == nullptr)
            return ;
        _ref -> decRefCount();
        if (_ref -> getRefCount() == 0) {
            delete _ptr;
            cout << "free resource" << endl;
        }
    }

    void increase() {
        if (_ptr == nullptr)
            return ;
        _ref -> incRefCount();
    }

    // 指向控制块的指针, 记录引用计数
    refCount* _ref;
    // 指向共享资源的指针
    pointer_type _ptr;
};
```

## 测试样例

```cpp
#include <iostream>
#include <thread>
#include <mutex>
#include <atomic>
#include <memory>

using namespace std;

// 不可拷贝基类
class noncopyable {
public:
    noncopyable(const noncopyable&) = delete;
    noncopyable operator=(const noncopyable&) = delete;
protected:
    noncopyable() = default;
    ~noncopyable() = default;
};

// 引用计数控制块
class refCount : noncopyable{
public:
    refCount () {
        _cnt.store(0);
    }

    refCount(int cnt) {
        _cnt.store(cnt);
    }

    void decRefCount() {
        _cnt.fetch_sub(1);
    }

    void incRefCount() {
        _cnt.fetch_add(1);
    }

    int getRefCount() {
        return _cnt.load();
    }

private:
    atomic_int _cnt;
};


// 简易版shared_ptr模板类
template <typename T>
class SharedPtr : noncopyable {
public:
    using value_type     = T;
    using pointer_type   = T*;
    using reference_type = T&;

    // 构造函数
    SharedPtr () : _ptr(nullptr), _ref(nullptr) {}
    SharedPtr (pointer_type ptr) : _ptr(ptr), _ref(new refCount(1)) {}
    SharedPtr (const SharedPtr& other) {
        _ptr = other._ptr;
        _ref = other._ref;
        this -> increase();
    }

    // 拷贝构造
    SharedPtr& operator = (const SharedPtr& other) {
        if (this == &other)
            return *this;
        // 先减当前的引用计数
        this -> decrease();
        // 拷贝赋值
        _ptr = other._ptr;
        _ref = other._ref;
        // 增加引用计数
        this -> increase();
    }


    // 析构函数
    ~SharedPtr() {
        cout << "~SharedPtr() called" << endl;
        decrease();
    }  

    int use_count() {
        return _ref -> getRefCount();
    }

    // Dereferences the stored pointer. The behavior is undefined if the stored pointer is null.
    pointer_type operator -> () {
        return this -> _ptr;
    }

    reference_type operator * (){
        return *(this -> _ptr);
    }

    pointer_type get() {
        return this -> _ptr;
    }

    operator bool (){
        return this -> _ptr != nullptr;
    }

private:
    // 内部实现, 不对外开放
    void decrease() {
        if (_ptr == nullptr)
            return ;
        _ref -> decRefCount();
        if (_ref -> getRefCount() == 0) {
            delete _ptr;
            cout << "free resource" << endl;
        }
    }

    void increase() {
        if (_ptr == nullptr)
            return ;
        _ref -> incRefCount();
    }

    // 指向控制块的指针, 记录引用计数
    refCount* _ref;
    // 指向共享资源的指针
    pointer_type _ptr;
};


class Resource {
public:
    Resource () = default;
    Resource (int val) : _val(val) {}
    
    int get () const {
        return _val;
    }
private:
    int _val;
};

ostream& operator << (ostream& os, const Resource& res) {
    os << res.get();
    return os;
}


int main() {
    SharedPtr<Resource> sp(new Resource(123));

    auto nsp = sp;

    cout << sp -> get() << endl;
    cout << (*nsp).get() << endl;
    cout << nsp.use_count() << endl;

    // 使用前先判断是否为空, 调用了 operator bool
    if (nsp) {
        *nsp = 10086;
        auto p = nsp.get();
        cout << "nsp is not empty, *nsp = " << (*p) << endl;
    }

    return 0;
}

/*
123
123
2
nsp is not empty, *nsp = 10086
~SharedPtr() called
~SharedPtr() called
free resource
*/
```
