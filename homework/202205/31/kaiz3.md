使 static_assert 的文本信息可选

删除 trigraphs

在模板参数中允许使用 typename（作为替代类）

来自 braced-init-list 的新规则用于自动推导

嵌套命名空间的定义，例如：使用 namespace X::Y { … } 代替 namespace X { namespace Y { … }}

允许命名空间和枚举器的属性

新的标准属性：[[fallthrough]], [[maybe_unused]] 和 [[nodiscard]]

UTF-8 字符文字

对所有非类型模板参数进行常量评估

Fold 表达式，用于可变的模板

A compile-time static if with the form if constexpr(expression)

结构化的绑定声明，现在允许 auto [a, b] = getTwoReturnValues();

if 和 switch 语句中的初始化器

在某些情况下，确保通过编译器进行 copy elision（Guaranteed copy elision by compilers in some cases）

一些用于对齐内存分配的扩展

构造函数的模板推导，允许使用 std::pair(5.0, false) 代替 std::pair<double,bool>(5.0, false)

内联变量，允许在头文件中定义变量

__has_include，允许由预处理程序指令检查头文件的可用性

__cplusplus 的值更改为 201703L
