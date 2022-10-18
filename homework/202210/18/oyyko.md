https://zhuanlan.zhihu.com/p/50812510



inline还有两点意义：

**1.** 编译器并不是万能的，有时候人工的内联建议确实能解决一些编译器优化的盲点**【参考：[libcxx修改](https://link.zhihu.com/?target=https%3A//reviews.llvm.org/D22782)1 、[libcxx修改](https://link.zhihu.com/?target=https%3A//reviews.llvm.org/D22834)2 】**

**2.** inline并不只是只有把函数内联到调用的地方这个意义，他还关系到ODR （定义与单一定义规则）。所谓**[ODR](https://link.zhihu.com/?target=https%3A//zh.cppreference.com/w/cpp/language/definition)**，就是任何变量、函数、类类型、枚举类型、[概念](https://link.zhihu.com/?target=https%3A//zh.cppreference.com/w/cpp/language/constraints) (C++20 起)或模板，在每个翻译单元中都只允许有一个定义*（它们有些可以有多个声明，但定义只允许有一个）。*不过具体一点的说又有很多种情况，对于非inline且*odr-used的*变量、函数，要求全局只能有一个定义；Inline变量、函数在每个编译单元都有有一个定义；需要使用类的时候，在每个翻译单元都需要一个定义。

**例如：**你如果把一个非成员函数放到.h文件里面并被多个编译单元包含，那么在链接的时候就会报错。因为非inline的全局函数在全局只能有一个定义，如果每个编译单元都有一个成员函数，编译器不知道链接哪一个。如果给这个函数加上inline的话，就可以解决这个问题。而如果你在多个cpp里面定义了函数签名完全相同的但是内容不同inline函数，也不会发生编译失败，不过具体链接到哪个版本的inline函数可能是未定义行为。**【参考：[One Definition Rule](https://link.zhihu.com/?target=https%3A//en.wikipedia.org/wiki/One_Definition_Rule)** **[既然编译器可以判断一个函数是否适合 inline，那还有必要自己加 inline 关键字吗？](https://www.zhihu.com/question/53082910)** **[最近看到陈硕的一本书提了一个问题，“编译器如何处理inline函数中的static变量？”](https://www.zhihu.com/question/45488363/answer/99426141)】**

# `inline` 说明符

 

[C++](https://zh.cppreference.com/w/cpp)

 

[C++ 语言](https://zh.cppreference.com/w/cpp/language)

 

[声明](https://zh.cppreference.com/w/cpp/language/declarations)

 

inline 说明符，在用于函数的 [声明说明符序列](https://zh.cppreference.com/w/cpp/language/declarations#.E8.AF.B4.E6.98.8E.E7.AC.A6) 时，将函数声明为一个 *内联（inline）函数*。

整个定义都在 [class/struct/union 的定义](https://zh.cppreference.com/w/cpp/language/classes)内且被附着到全局模块 (C++20 起)的函数是隐式的内联函数，无论它是成员函数还是非成员 friend 函数。

| 声明有 constexpr 的函数是隐式的内联函数。弃置的函数是隐式的内联函数：它的（弃置）定义可以在多于一个翻译单元中出现。 | (C++11 起) |
| ------------------------------------------------------------ | ---------- |
|                                                              |            |

| inline 说明符，在用于具有静态存储期的变量（静态类成员或命名空间作用域变量）的 [声明说明符序列](https://zh.cppreference.com/w/cpp/language/declarations#.E8.AF.B4.E6.98.8E.E7.AC.A6) 时，将变量声明为*内联变量*。声明为 constexpr 的静态成员变量（但不是命名空间作用域变量）是隐式的内联变量。 | (C++17 起) |
| ------------------------------------------------------------ | ---------- |
|                                                              |            |

### 解释

*内联函数*或*内联变量* (C++17 起)具有下列性质：

1. 内联函数或变量 (C++17 起)的定义必须在访问它的翻译单元中可达（不一定要在访问点前）。
2. 带[外部连接](https://zh.cppreference.com/w/cpp/language/storage_duration#.E5.A4.96.E9.83.A8.E8.BF.9E.E6.8E.A5)的 inline 函数或变量 (C++17 起)（例如不声明为 static）拥有下列额外属性：



在内联函数中，

- 所有函数定义中的函数局部静态对象在所有翻译单元间共享（它们都指代相同的在某一个翻译单元中定义的对象）
- 所有函数定义中所定义的类型同样在所有翻译单元中相同。

| 命名空间作用域的内联 const 变量默认具有[外部连接](https://zh.cppreference.com/w/cpp/language/storage_duration#.E5.A4.96.E9.83.A8.E8.BF.9E.E6.8E.A5)（这点与非内联非 volatile 的有 const 限定的变量不同） | (C++17 起) |
| ------------------------------------------------------------ | ---------- |
|                                                              |            |

inline 关键词的本意是作为给优化器的指示器，以指示优先采用[函数的内联替换](https://en.wikipedia.org/wiki/inline_expansion)而非进行函数调用，即并不执行将控制转移到函数体内的函数调用 CPU 指令，而是代之以执行函数体的一份副本而无需生成调用。这会避免函数调用的开销（传递实参及返回结果），但它可能导致更大的可执行文件，因为函数体必须被复制多次。

因为关键词 inline 的含义是非强制的，编译器拥有对任何未标记为 inline 的函数使用内联替换的自由，和对任何标记为 inline 的函数生成函数调用的自由。这些优化选择不改变上述关于多个定义和共享静态变量的规则。

| 由于关键词 inline 对于函数的含义已经变为“容许多次定义”而不是“优先内联”，因此这个含义也扩展到了变量。 | (C++17 起) |
| ------------------------------------------------------------ | ---------- |
|                                                              |            |

#### 注解

如果具有外部连接的内联函数或变量 (C++17 起)在不同翻译单元中的定义不同，那么行为未定义。

inline 说明符不能用于块作用域内（函数内部）的函数或变量 (C++17 起)声明。

inline 说明符不能重声明在翻译单元中已定义为非内联的函数或变量 (C++17 起)。

隐式生成的成员函数和任何在它的首条声明中声明为预置的成员函数，与任何其他在类定义内定义的函数一样是内联的。

如果一个内联函数在不同翻译单元中被声明，那么它的[默认实参](https://zh.cppreference.com/w/cpp/language/default_arguments)的积累集合必须在每个翻译单元的末尾相同。

在 C 中，内联函数不必在每个翻译单元声明为 inline（最多一个可以是非 inline 或 extern inline），函数定义不必相同（但如果程序依赖于调用的是哪个函数则行为未指明），且函数局部的静态变量在同一函数的不同定义间不同。

| 关于内联静态成员的额外规则见[静态数据成员](https://zh.cppreference.com/w/cpp/language/static)。内联变量消除了将 C++ 代码打包为只有头文件的库的主要障碍。 | (C++17 起) |
| ------------------------------------------------------------ | ---------- |
|                                                              |            |



macro属于预处理阶段。和内联不同。不能进行类型判断等操作，比较初级。