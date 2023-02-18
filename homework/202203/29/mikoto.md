## C++: 什么是constexpr？

constexpr: 修饰常量表达式，约定修饰对象不仅为常量，而且在编译阶段就能得到值。

### constexpr修饰普通变量

定义变量时可以用constexpr修饰，从而是该变量获得在编译阶段即可计算出结果的能力，该变量必须经过初始化，而且初始值必须是一个常量表达式。

### constexpr修饰函数

需要满足以下条件：
1. 整个函数体中国，除了using typedef static_assert以外，只能包含一条return语句
2. 函数必须有返回值，不能是void
3. 常量表达式函数在使用前必须被定义
4. return返回的表达式必须是常量表达式

const 和 constexpr的区别，

简单的说，const表示的是只读，而其常量的语义被划分给了constexpr,因此，在c++11编程中，只读语义的建议用const，表达常量语义的使用constexpr
