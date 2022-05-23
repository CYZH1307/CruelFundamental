### C++: 讲一讲 std::move 和 std::forward

参考：https://paul.pub/cpp-value-category/

C++的程序由一系列的表达式（expressions）构成。表达式是运算符和操作数的序列，表达式指定一项计算。

例如：`2 + 2` 或者 `std::cout << "Hello World" << std::endl`都是表达式。

每个表达式有两个互相独立但是非常重要的属性：

- 类型（type）。类型是我们很熟悉的概念，`int`，`double`和`std::string`这些都是类型。类型确定了表达式可以进行哪些操作。
- 除了类型之外，还有一个称之为值类别（value category）的属性，却可能是我们平时不太注意的。

每个表达式都属于三种值类别（prvalue，xvalue，lvalue）中的一种.

值类别可以影响表达式的含义，例如：你应该知道这个表达式是没有意义的：`3 = 4`，它甚至编译不过。但你可能说不出来为什么编译器会认为它编译不过。

如果你使用gcc编译器，它的报错如下：

```
 error: lvalue required as left operand of assignment
```

这个报错中的`lvalue`就是数字表达式`3`的值类别。

再者，值类别还会影响函数的重载：当某个函数有两种重载可用，其中之一接受右值引用的形参而另一个接受 const 的左值引用的形参时，右值将被绑定到右值引用的重载之上。



# 从左值和右值说起

最初的时候，只有左值（lvalue）和右值（rvalue）这两个术语。它们源于C++的祖先语言：[CPL](https://en.wikipedia.org/wiki/CPL_(programming_language))。

lvalue之所以叫lvalue，是因为它常常出现在等号的左边（left-hand side of an assignment）。同样，rvalue是因为它常常出现在等号的右边（right-hand side of an assignment）。

回顾一下上面的`3 = 4`编译报错，就是因为编译器要求等号的左边得是一个lvalue，而数字`3`其实是一个rvalue，所以这个是无法通过编译的。

C语言遵循了相似的分类法，但是否需要等号赋值已经不再重要。在C语言中，标识一个对象的表达式称之为左值，不过lvalue已经是“locator value”的简写，因为lvalue对应了一块内存地址。

你可以简单的理解为：**左值对应了具有内存地址的对象，而右值仅仅是临时使用的值**。例如`int a = 1`中，`a`是左值，`1`是右值。



# C++11中的值类别

C++中对于值类别的定义也经历一些变化。从C++11标准开始，值类别早以不止是lvalue和rvalue两种这么简单。

但情况也不算太坏，因为主要的值类别有：lvalue，prvalue 和 xvalue三种。加上两种混合类别：glvalue和rvalue，一共有五种。

我们来看一下它们的定义：

- A **glvalue**（generalized lvalue） is an expression whose evaluation determines the identity of an object, bit-field, or function.
- A **prvalue**（pure rvalue） is an expression whose evaluation initializes an object or a bit-field, or computes the value of an operand of an operator, as specified by the context in which it appears, or an expression that has type cv void.
- An **xvalue**（eXpiring value） is a glvalue that denotes an object or bit-field whose resources can be reused （usually because it is near the end of its lifetime）.
- An **lvalue** is a glvalue that is not an xvalue.
- An **rvalue** is a prvalue or an xvalue.

这个定义很难理解，就算翻译成中文，也一样不好理解。所以下文会通过一些示例来对它们进行说明。

这五种类别的分类基于表达式的两个特征：

- 是否拥有身份（identity）：可以确定表达式是否与另一表达式指代同一实体，例如比较它们所标识的对象或函数的（直接或间接获得的）地址；
- 是否可被移动（具体见下文）：移动构造函数、移动赋值运算符或实现了移动语义的其他函数重载能够绑定到这个表达式。

由此，C++11中对于这五种类别定义如下：

- lvalue是指：拥有身份且不可被移动的表达式。
- xvalue是指：拥有身份且可被移动的表达式。
- prvalue是指：不拥有身份且可被移动的表达式。
- glvalue是指：拥有身份的表达式，lvalue和xvalue都是glvalue。
- rvalue是指：可被移动的表达式。prvalue和xvalue都是rvalue。

# 左值引用与右值引用

左值引用和右值引用不属于值类别（value category），它们是表达式的类型（type），并且都是组合类型（compound type）。



在C++11之前，引用分为const引用和非const引用。这两种引用在C++11中都称做左值引用（lvalue reference）。

无法将非const左值引用指向右值。例如，下面这行代码是无法通过编译的：

```
int& a = 10;
```

编译器的报错是：

```
error: non-const lvalue reference to type 'int' cannot bind to a temporary of type 'int'
```

它的意思是：你无法将一个非const左值引用指向一个临时的值。

但是const类型的左值引用是可以绑定到右值上的，所以下面这行代码是没问题的：

```
const int& a = 10;
```

不过，由于这个引用是const的，因此你无法修改其值的内容。

C++11新增了右值引用，左值引用的写法是`&`，右值引用的写法是`&&`。

右值是一个临时的值，右值引用是指向右值的引用。右值引用延长了临时值的生命周期，并且允许我们修改其值。

例如：

```
std::string s1 = "Hello ";
std::string s2 = "world";
std::string&& s_rref = s1 + s2;    // the result of s1 + s2 is an rvalue
s_rref += ", my friend";           // I can change the temporary string!
std::cout << s_rref << '\n';       // prints "Hello world, my friend"
```

在上面这个代码中，`s_rref`是一个指向临时对象的引用：右值引用。由于这里没有const，因此我们可以借此修改临时对象的内容。

右值引用使得我们可以创建出以此为基础的函数重载，例如：

```
void func(X& x) {
    cout << "lvalue reference version" << endl;
}

void func(X&& x) {
    cout << "rvalue reference version" << endl;
}
```

右值引用本身是一个左值还是一个右值？答案是：都有可能。**右值引用既可能是lvalue，也可能是rvalue。如果它有名称，则是lvalue，否则是rvalue。**



右值引用是C++11中两个新增功能的语法基础，这两个功能是：

- 移动语义（Move Semantics）
- 完美转发（Perfect Forward）

`std::move()`的名称具有一定的迷惑性，因为它并没有进行任何“移动”的操作，它仅仅是：**无条件的将实参强制转换成右值引用**，仅此而已。因此C++之父认为它的名字叫做`rval()`应该更合适。但是不管怎么样，由于历史原因，它已经叫做`std::move()`。



在C++11之前，C++语言存在一个称之为“[The Forwarding Problem](http://www.open-std.org/jtc1/sc22/wg21/docs/papers/2002/n1385.htm)”的问题。

这个问题直到C++11才得以解决。不过要说清楚这个问题并不那么容易。下面以一个具体的代码示例来说明。

一直以来，我们都是通过`push_back`方法往`vector`中添加对象的：

```
class MyKlass {
public:
  MyKlass(int ii_, float ff_) {...}
  ...
};

some function {
  std::vector<MyKlass> v;
  v.push_back(MyKlass(2, 3.14f));
}
```

但看了上面的内容，你应该已经意识到，这样的方式是通过拷贝的形式完成添加的：要先创建出一个临时对象来，然后拷贝进集合中，这样做效率不够高。更好的方法当时是通过移动。于是C++11为集合类添加了 [emplace](https://en.cppreference.com/mwiki/index.php?title=Special%3ASearch&search=emplace) 和 [emplace_back](https://en.cppreference.com/mwiki/index.php?title=Special%3ASearch&search=emplace_back) 方法。

`emplace_back`用起来像这样：

```
v.emplace_back(2, 3.14f);
```

这个方法接受模板实例类`MyKlass`的构造函数形参，这样做避免了临时对象的构造。

但是你有没有想过`emplace_back`函数是如何实现的呢？我们可以尝试一下。

我们尝试的第一个版本可能是这样：

```
template <typename T1, typename T2>
void emplace_back(T1 e1, T2 e2) {
    func(e1, e2);
}
```

这个方法存在一个问题，那就是它不支持引用类型。即便`func`的参数是引用类型的，但是外层`emplace_back`的参数已经是复制的值。也就说，这里会多一次拷贝。

于是我们第二个版本将改成这样，把外层的参数也改成引用的：

```
template <typename T1, typename T2>
void emplace_back(T1& e1, T2& e2) {
    func(e1, e2);
}
```

这时又有一个问题：左值引用不能指向右值，所以如果我们这样调用是无法通过编译的：

```
emplace_back(42, 3.14f);
```

不过const引用是可以指向右值的，所以解决这个问题的办法就是重载：为每个参数定义const和非const两种类型的引用版本，于是乎成了这样：

```
template <typename T1, typename T2>
void emplace_back(T1& e1, T2& e2)                { func(e1, e2); }

template <typename T1, typename T2>
void emplace_back(const T1& e1, T2& e2)          { func(e1, e2); }

template <typename T1, typename T2>
void emplace_back(T1& e1, const T2& e2)          { func(e1, e2); }

template <typename T1, typename T2>
void emplace_back(const T1& e1, const T2& e2)    { func(e1, e2); }
```

很显然，你马上就意识好像不太对劲。如果是2个参数，需要定义四个重载的版本。那如果是5个参数呢？需要25=3225=32的版本。如果是10个参数呢？？?



为了解决这个问题，C++11引入了两个新的机制：

1. Reference Collapsing Rules，我不太确定它的正式中文翻译是什么。我们姑且称之为：引用符号折叠规则。
2. 特殊类型推导规则，这个与Universal Reference相关。

先说第一个：在C++11中，不存在引用的引用，因此`A& &`的写法是无法编译的。但在模板类型推导的时候，这是有可能发生的。例如下面这个定义：

```
template <typename T>
void baz(T t) {
  T& k = t;
}
```

当我们用`int&`去实例化的时候：

```
int ii = 4;
baz<int&>(ii);
```

将`T`替换成`int&`，于是`k`的类型就变成了`int& &`。甚至于，如果用右值引用`int&&`代替`T`的话，`k`的类型就变成了`int&& &`。

所以C++标准定了一些规则，在这种情况下，编译器会执行Reference Collapsing Rules，具体的规则如下：

- 如果是 `A& &` 将变成 `A&`
- 如果是 `A& &&` 将变成 `A&`
- 如果是 `A&& &` 将变成 `A&`
- 如果是 `A&& &&` 将变成 `A&&`

简单记忆如下：

- 两个或者三个`&`都会变成一个`&`
- 四个`&`都会变成两个`&`

然后我们再说第二个规则：特殊类型推导规则是在特殊的环境下才会产生的推导规则（这好像是一句话废话）。 要理解这个，还需要再借助另外一个术语：Universal Reference，我们可以简称URef。这应该是Scott Meyers（Scott Meyers是世界顶级的C++软件开发技术权威之一，他的《Effective C++》，《More Effective C++》你应该听说过）创造的名词。



只有声明为`T&&`且T需要推导的情况下，才是URef。例如`void f(Widget&& w);`，由于不需要推导，所以它不是URef。

但下面这个代码中，由于模板需要推导，所以它是URef：

```
template<typename T>
void foo(T&&);
```

如果是URef这种情况，则其具有特殊的推导规则。具体描述如下：

- 如果用类型A的左值初始化URef，则URef会变成左值引用 `A&`
- 如果用类型A的右值初始化URef，则URef会变成右值引用 `A&&`

除了这两个规则之外，C++还为我们提供了`forward`函数，该函数有两个重载的版本，定义如下：

```
template<class T>
T&& forward(typename std::remove_reference<T>::type& t) noexcept {
  return static_cast<T&&>(t);
}

template <class T>
T&& forward(typename std::remove_reference<T>::type&& t) noexcept {
  return static_cast<T&&>(t);
}
```

`forward`函数依赖`<type_traits>`头文件中的另外一个结构体`remove_reference`：

```
template< class T >
struct remove_reference;
```

`remove_reference`中包含了一个类型成员名称为`type`:

```
template< class T >
using remove_reference_t = typename remove_reference<T>::type;
```

若类型T为引用类型，则成员`type`为T所引用的类型。否则`type`为T本身。例如：

- `std::remove_reference<int>::type` 得到`int`
- `std::remove_reference<int&>::type` 依旧得到`int`
- `std::remove_reference<int&&>::type` 仍然得到`int`