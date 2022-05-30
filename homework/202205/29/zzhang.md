C++ 11 新特性

1. 基于范围的 for 循环

``` C++
vector<int> vec(10, 0)
for (int & x: vec) {}
```

2. 自动类型推断 auto

3. 匿名函数 lambda

4. 后置返回类型 tailng-return-type

``` C++
struct SomeStruct {
  auto func_name(int x, int y) -> int;
};
auto SomeStruct::func_name(int x, int y) -> int {
}
```

5. 显示 override 和 final

6. 空指针常量 nullptr

7. long long int 类型

8. 摸板的别名

9. 允许sizeof在struct数据成员上使用，无需明确对象

10. 线程支持
