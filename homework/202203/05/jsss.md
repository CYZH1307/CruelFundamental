# C++: 简述 mechanism of new

`new`是cpp的关键字, 其作用是动态申请一块内存并按照我们的指定的方式初始化.

## new的流程

1. 从**自由存储区**中申请一块内存. 自由存储区是`new`和`delete`对应的抽象概念. 其可以是堆上内存也可以是全局数据段内存.
2. 按照指定的方式调用对象的构造函数进行初始化.
3. 返回指向对象的指针

## new与malloc的区别

1. `new`是cpp的关键字, `malloc`是c的库函数.
2. `new`时无需指定内存的大小, 而`malloc`需要指定申请的内存大小
3. `new`返回值为指向对象的指针, 可以直接使用. 而`malloc`返回的是`void *`, 需要进行类型转换.
4. `new`失败时, 一般会发生`bad_alloc`异常. 而`malloc`判断返回值是否为`NULL`来确定是否成功.
5. `new`可以重载. 比如`void* operator new(size_t size)` . 而`malloc`无法重载
6. `new`可以方便的处理数组. 而`malloc`需要自行管理.
7. `new`和`delete`搭配使用. `malloc`和`free`搭配使用.
8. `new`可以通过`malloc`实现. 而`malloc`无法通过`new`实现. `malloc`一般通过`brk()`和`mmap()`函数实现.
9. `new`支持`placement new`. 对一块已经申请好的内存进行赋值.

## 测试样例

```cpp
#include <iostream>
#include <new>
#include <string>
#include <vector>
#include <sstream>

using namespace std;

class MyString {
private:
    string str;
public:
    MyString (const string& s): str(s) {
        cout  << endl << "call mystring constructor function" << endl;
    } 

    ~MyString () = default;

    
    static void* operator new (size_t size) throw() {
        cout  << endl <<  "call mystring operator new function!" << endl;
        // maclloc 函数实现 operator new
        void* p = malloc(size);
        // 编译器自带的全局operator new 实现
        // void *p = ::operator new(size);
        if (p == NULL)
            throw bad_alloc();
        return p;
    }

    static void* operator new (size_t size, void* p) {
        cout << endl << "place ment new called!" << endl;
        return p;
    }

    // 通过getline实现字符串分割
    vector<string> split(char delim) {
        stringstream ss(this -> str);
        string word;
        vector<string> ans;
        while (getline(ss, word, delim))
            ans.emplace_back(word);
        return ans;
    }

};

void printSplit(MyString* s, char delim) {
    // 通过delim字符进行分隔
    auto ans = s -> split(delim);
    cout << "string split result : " << endl;
    for (auto& c : ans)
        cout << c << endl;
    cout << "split string end !" << endl;
}

int main() {
    MyString* s = new MyString("I am using cpp.");

    printSplit(s, ' ');

    // placement new: 在一块已经分配好的内存上调用构造函数, 不涉及内存的动态申请
    s = new (s) MyString("Testing,PlaceMent,New!");

    printSplit(s, ',');

    return 0;
}

/* 输出
call mystring operator new function!

call mystring constructor function
string split result :
I
am
using
cpp.
split string end !

place ment new called!

call mystring constructor function
string split result :
Testing
PlaceMent
New!
split string end !
*/
```