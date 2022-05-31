- 构造函数模板推导
- 结构化绑定
- if-switch语句初始化
- 内联变量
- 折叠表达式
- constexpr lambda表达式
- namespace嵌套
- __has_include预处理表达式
- 在lambda表达式用*this捕获对象副本
- 新增Attribute
- 字符串转换
- std::variant
- std::optional
- std::any
- std::apply
- std::make_from_tuple
- as_const
- std::string_view
- file_system
- std::shared_mutex





**构造函数模板推导**

在C++17前构造一个模板类对象需要指明类型：

```
pair<int, double> p(1, 2.2);
```

C++17就不需要特殊指定，直接可以推导出类型，代码如下：

```
pair p(1, 2.2);
vector v = {1, 2, 3}; 
```

**结构化绑定**:

```cpp
for(auto&[key,value]: mapp)
{
    ...
}
```

**内联变量**

C++17前只有内联函数，现在有了内联变量，我们印象中C++类的静态成员变量在头文件中是不能初始化的，但是有了内联变量，就可以达到此目的

**折叠表达式**

C++17引入了折叠表达式使可变参数模板编程更方便：

```cpp
template <typename ... Ts> 
auto sum(Ts ... ts) { 
   return (ts + ...); 
} 
int a {sum(1, 2, 3, 4, 5)}; // 15 
std::string a{"hello "}; 
std::string b{"world"}; 
cout << sum(a, b) << endl; // hello world 
```

**constexpr lambda表达式**

C++17前lambda表达式只能在运行时使用，C++17引入了constexpr lambda表达式，可以用于在编译期进行计算

constexpr函数有如下限制：

函数体不能包含汇编语句、goto语句、label、try块、静态变量、线程局部存储、没有初始化的普通变量，不能动态分配内存，不能有new delete等，不能虚函数

**namespace嵌套**

```cpp
namespace A { 
   namespace B { 
       namespace C { 
           void func(); 
      } 
  } 
} 
 
// c++17，更方便更舒适 
namespace A::B::C { 
   void func();) 
} 
```

**在lambda表达式用\*this捕获对象副本**

正常情况下，lambda表达式中访问类的对象成员变量需要捕获this，但是这里捕获的是this指针，指向的是对象的引用，正常情况下可能没问题，但是如果多线程情况下，函数的作用域超过了对象的作用域，对象已经被析构了，还访问了成员变量，就会有问题。

```cpp
struct A { 
   int a; 
   void func() { 
       auto f = [this] { 
           cout << a << endl; 
      }; 
       f(); 
  }   
}; 
int main() { 
   A a; 
   a.func(); 
   return 0; 
} 
```

所以C++17增加了新特性，捕获*this，不持有this指针，而是持有对象的拷贝，这样生命周期就与对象的生命周期不相关啦。

```cpp
struct A { 
   int a; 
   void func() { 
       auto f = [*this] { // 这里 
           cout << a << endl; 
      }; 
       f(); 
  }   
}; 
int main() { 
   A a; 
   a.func(); 
   return 0; 
} 
```

**std::optional**

我们有时候可能会有需求，让函数返回一个对象，如下：

```
struct A {}; 
A func() { 
    if (flag) return A(); 
    else { 
        // 异常情况下，怎么返回异常值呢，想返回个空呢 
    } 
} 
```

有一种办法是返回对象指针，异常情况下就可以返回nullptr啦，但是这就涉及到了内存管理，也许你会使用智能指针，但这里其实有更方便的办法就是std::optional。

```cpp
std::optional<int> StoI(const std::string &s) { 
    try { 
        return std::stoi(s); 
    } catch(...) { 
        return std::nullopt; 
    } 
} 
 
void func() { 
    std::string s{"123"}; 
    std::optional<int> o = StoI(s); 
    if (o) { 
        cout << *o << endl; 
    } else { 
        cout << "error" << endl; 
    } 
} 
```

**std::any**

C++17引入了any可以存储任何类型的单个值

**std::apply**

使用std::apply可以将tuple展开作为函数的参数传入

**std::make_from_tuple**

使用make_from_tuple可以将tuple展开作为构造函数参数

**std::string_view**

通常我们传递一个string时会触发对象的拷贝操作，大字符串的拷贝赋值操作会触发堆内存分配，很影响运行效率，有了string_view就可以避免拷贝操作，平时传递过程中传递string_view即可。

**as_const**

C++17使用as_const可以将左值转成const类型

**file_system**

C++17正式将file_system纳入标准中，提供了关于文件的大多数功能