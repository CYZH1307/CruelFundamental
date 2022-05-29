auto类型推导
在早期版本中，关键字auto主要是用于声明具有自动存储期的局部变量。然而，它并没有被经常使用。原因是：除了static类型以外，其他变量(以“数据类型＋变量名”的方式定义)都默认为具有自动存储期，所以auto关键字可有可无。

所以，在C++11的版本中，删除了auto原本的功能，并进行了重新定义了。即C++11中的auto具有类型推导的功能

其实auto就相当于一个类型声明时的**占位符**，而不是一种“类型”的声明，在**编译时期**编译器会将auto替代成变量的实际类型

#### auto的优势

**I. 拥有初始化表达式的复杂类型变量声明时的简化代码。**

也就是说，auto能够节省代码量，使代码更加简洁， 增强可读性。

```cpp
std::vector<std::string> array;
std::vector<std::string>::iterator it = array.begin();
// auto
auto it = array.begin();
```

decltype 类型推导
类型推导是随着模板和泛型编程的广泛使用而引入的。在非泛型编程中，类型是明确的，而在模板与泛型编程中，类型是不明确的，它取决于传入的参数类型。

decltype与我前面讲到的auto还是有一些共同点的，如二者都是通过推导获得的类型来定义另外一个变量，再如二者都是在编译时进行类型推导。不过他们类型推导的方式有所不同，auto是通过初始化表达式推导出类型，而decltype是通过普通表达式的返回值推导出类型。

不过在讲解decltype之前，我们先来了解一下typeid。

(1)typeid 与 decltype
对于C语言，是完全不支持动态类型的；
对于C++，与C不同的是，C++98标准中已经有部分支持动态类型了，便是运行时类型识别(RTTI)。

RTTI机制：为每个类型产生一个type_info类型数据，程序员可以在程序中使用typeid随时查询一个变量的类型，typeid就会返回变量相应的type_info数据，type_info的name成员可以返回类型的名字。在C++11中，增加了hash_code这个成员函数，返回该类型唯一的哈希值，以供程序员对变量类型随时进行比较。

也许你会有这样一个想法：我直接对type_info.name进行字符串比较不就可以了么，为什么还要给每个类型一个哈希值？我认为，字符串比较的开销也是比较大的，如果用每个类型来对于一个哈希值，通过比较哈希值确定类型是否相同的方法，会比使用字符串比较的效率要高得多。

然而，**RTTI**无法满足程序员的需求：因为**RTTI**在运行时才确定出类型，而更多的需求是在编译时确定类型。并且，**通常的程序是要使用推导出来的这种类型而不是单纯地识别它**。

decltypr的应用
I.decltype 与 using / typedef 连用
在头文件，常常看到如下定义：

```cpp
using size_t = decltype(sizeof(0));
using ptrdiff_t = decltype((int *)0-(int*)0);
using nullptr_t = decltype(nullptr);
```

**重用匿名类型**

```cpp
enum class {K1, K2, K3} anon_e;  // 匿名的强类型枚举
union {
    decltype (anon_e) key;
    char* name;
} anon_u; // 匿名的union联合体
struct {
    int d;
    decltype(anon_u) id; 
} anon_s[100]; // 匿名的struct数组
int main(void) {
    decltype(anon_s) as;
    as[0].id.key = decltype(anon_e)::K1; // 引用匿名强类型枚举中的值
    return 0;
}
```

**decltype 可以适当扩大模板泛型编程的能力。**

```cpp
template <typename T1, typename T2>
void Sum(T1& t1, T2 & t2, decltype(t1+t2)& s) {
    s = t1+t2
}
int main(void) {
    int a = 3;
    long b = 5;
    float c = 1.0f, f = 2.3f;
    long e;
    float f;
    Sum(a, b, e);
    Sum(c, d, f);
    return 0;
}
```

decltype 推导的四规则
在了解这四个规则之前，我们先来了解标记符表达式(id-expression)的概念。

标记符表达式(id-expression)：所有除去关键字和字面量等编译器需要使用的标记以外的程序员自定义的标记(token)都可以是标记符(identifier)， 而单个标记符对应的表达式就是标记符表达式。

如int arr[4], int i, arr与i就是标记符表达式。对于前者，去除关键字int与字面量[4]后剩下的arr便是标记符表达式。

还有一点，C++11中对值的类型分类与C++98有所不同。在C++98中，值可分左值与右值。通俗地来讲， 所谓的左值便是含有变量名的数值，所谓的右值就是没有变量名的数值，即为临时变量， 以及包含右值引用。而在C++11中，就将右值更进一层地分类：分为纯右值与将亡值，纯右值即为没有变量名的数值，将亡值即为右值引用，且左值与将亡值合称为泛左值。

decltype推导的四规则如下：
（1）如果e是一个没有带括号的标记符表达式或者类成员访问表达式，那么decltype(e)就是e所命名的实体的类型。此外，如果e是一个被重载的函数，可能会导致编译错误；
（2）否则，假设e的类型是T，如果e是一个将亡值(xvalue), 那么decltype(e)为T&&；
（3）否则，假设e的类型是T，如果e是一个左值，则decltype(e)为T&；
（4）否则，假设e的类型是个T， 则decltype(e)为T。追踪返回类型

在C++98中，如果一个函数模板的返回类型依赖于实际的入口参数类型，那么该返回类型在模板实例化之前可能都无法确定，这样的话我们在定义该函数模板时就会遇到麻烦。

我们很快就会想到可以用decltype来定义：

```cpp
// 注：本段代码不能通过编译，只是帮助读者了解追踪返回类型的由来
template<typename T1, typename T2>
decltype(t1+t2) Sum(T1& t1, T2& t2) {
    return t1+t2;
}
```

为了解决这一问题，C++11引进了新语法——追踪返回类型，来声明和定义这样的函数：

```cpp
template<typename T1, typename T2>
auto Sum(T1& t1, T2& t2)->decltype(t1+t2) {
    return t1+t2;
}
```

##### c++11的基于范围的for循环，**无需**告诉循环体其界限范围。

```cpp
# include <iostream>
using namespace std;
int main(void) {
    int a[5] = {1, 2, 3, 4, 5};
    for (int& e: arr) e *= 2;
    for (int& e: arr) cout << e << "\t";
    // or(1)
    for (int e: arr) cout << e << "\t";
    // or(2)
    for (auto e:arr) cout << e << "\t";
    return 0;
}
```

**使用条件：**
**(1)for循环迭代的范围是可确定的：对于类，需要有begin()与end()函数；对于数组，需要确定第一个元素到最后一个元素的范围；**
**(2)迭代器要重载++；**
**(3)迭代器要重载`\*`, 即\*iterator；**
**(4)迭代器要重载== / !=。**



nullptr
在良好的C++编程习惯中，声明一个变量的同时，总是需要记得在合适的代码位置将其初始化。对于指针类型的变量，这一点尤其应当注意。未初始化的悬挂指针通常会是一些难于调试的用户程序的错误根源。

而典型的初始化指针通常有两种：0与NULL, 意在表明指针指向一个空的位置。

```cpp
# include <iostream>
using namespace std;
void f(int* ptr) {}
void f(int num) {}
int main(void) {
    f(0);
    f((int*)0);
    f(NULL);   // 编译不通过
    return 0;
}
```

NULL既可以被替换成整型，也可以被替换成指针，因此在函数调用时就会出现问题。因此，在早期版本的C++中，为了解决这种问题，只能进行显示类型转换。

所以在C++11中，为了完善这一问题，引入了nullptr的指针空值类型的常量。为什么不重用NULL？原因是重用NULL会使已有很多C++程序不能通过C++11编译器的编译。为保证最大的兼容性且避免冲突，引入新的关键字是最好的选择。

而且，出于兼容性的考虑，C++11中并没有消除NULL的二义性。
1.所有定义为nullptr_t类型的数据都是等价的，行为也是完全一致的。
也就是说，nullptr_t的对象都是等价，都是表示指针的空值，即满足“==”。
2.nullptr_t类型的数据可以隐式转换成任意一个指针类型。
3.nullptr_t类型数据不能转换成非指针类型，即使用reinterpret_cast()的方式也不可以实现转化；
4.nullptr_t类型的对象不适用于算术运算的表达式；
5.nullptr_t类型数据可以用于关系运算表达式，但仅能与nullptr_t类型数据或者是指针类型数据进行比较，当且仅当关系运算符为-=, <=, >=, 等时返回true。

#### 智能指针

#### 右值引用

#### constexpr

#### 初始化列表

```cpp
class A{
public:
    A(std::initializer_list<int> list);
};
A a = {1, 2, 3};
```

#### 统一的初始化语法

#### lambda表达式

```cpp
std::vector<int> someList;
int total = 0;
std::for_each(someList.begin(), someList.end(), [&total](int x) { total += x; });
std::cout << total;
```

#### 构造函数委托

```cpp
class SomeType {
  int number; string name;
  SomeType( int i, string& s ) : number(i), name(s){}
public:
  SomeType( )           : SomeType( 0, "invalid" ){}
  SomeType( int i )     : SomeType( i, "guest" ){}
  SomeType( string& s ) : SomeType( 1, s ){ PostInit(); }
};
```



#### final override

```cpp
struct Base1 final { };     
struct Derived1 : Base1 {};         // 编译错：Base1不允许被继承

struct Base2 {
    virtual void f1() final;
    virtual void f2();
};
struct Derived2 : Base2 {
    virtual void f1();              // 编译错：f1不允许重写
    virtual void f2(int) override;  // 编译错：父类中没有 void f2(int)
};
```

#### default和delete

```cpp
struct S{
    S() = defauult;     // 声明一个自动生成的函数
    S(T value);
    void *operator new(size_t) = delete;    // 禁止生成new运算符
};
```

#### 静态assertion

#### 正则表达式

```cpp
const char *reg_esp = "[ ,.\\t\\n;:]";
std::regex rgx(reg_esp) ;
std::cmatch match ;  
const char *target = "Polytechnic University of Turin " ;

if( regex_search( target, match, rgx ) ) {
  const size_t n = match.size();
  for( size_t a = 0 ; a < n ; a++ ) {
    string str( match[a].first, match[a].second ) ;
    cout << str << "\n" ;
  }
}
```

