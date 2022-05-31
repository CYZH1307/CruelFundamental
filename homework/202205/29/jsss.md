# C++: 讲讲C++11的新特性

- **auto**: 编译期自动推导变量的类型, 可用于变量的类型声明, 但变量必须初始化, 否则无法推导.
- **decltype**: 编译期推导表达式的类型, 不需对表达式进行计算即可获得其类型.
- **右值和右值引用**: 使用`std::move`和`std::forward`以及移动构造函数、移动赋值函数来减少资源深拷贝的开销. `std::forward`用于参数的完美转发, 防止将`命名右值引用`错误的转发到`左值`的情况出现.
- **可变参数模板**: 支持可变参数模板`Args... args`
- **初始化列表**: 可以使用初始化列表来构造对象, 其能防止隐式类型转换.
- **多线程编程**： 提供了丰富的模板类, 如`mutex`, `unique_lock`, `atomic`, `condition_variable`, `promise`, `future`, `async`, `packaged_task`, `thread`等.
- **函数式编程**: 提供了匿名函数、`std::bind`、`std::function`等.
- **资源管理类**：基于`RAII`编程技术的资源管理类有**智能指针**, `enable_shared_from_this`, `lock_guard`, `shared_ptr`, `weak_ptr`, `unique_ptr`等.
- **STL**: 提供了基于哈希表的关联式容器`std::unordered_set`和`std::unordered_map`以及`std::forward_list`.
- **类相关的**：`final`、`override`、`const`、`explicit`、`mutable`, 委托构造函数, `default`, `delete`.
- **constexpr**: `static_assert`, 编译期静态断言, `constexpr`不仅是`const`, 即不可变, 而且要求编译期能够确定.
