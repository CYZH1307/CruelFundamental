### C++: 讲讲 C++17 的新特性

参考：https://zhuanlan.zhihu.com/p/165640868
先学 3 个，感觉有点杂乱

##### if-switch 语句初始化

if (int a = GetValue()); a < 101) {
cout << a;
}

可以像 go 一样用 if

##### 构造函数模板推导

在 C++17 前构造一个模板类对象需要指明类型：

pair<int, double> p(1, 2.2); // before c++17

C++17 就不需要特殊指定，直接可以推导出类型，代码如下：

pair p(1, 2.2); // c++17 自动推导
vector v = {1, 2, 3}; // c++17

感觉这个会产生很多 bug

##### 折叠表达式

C++17 引入了折叠表达式使可变参数模板编程更方便：

template <typename ... Ts>
auto sum(Ts ... ts) {
return (ts + ...);
}
int a {sum(1, 2, 3, 4, 5)}; // 15
std::string a{"hello "};
std::string b{"world"};
cout << sum(a, b) << endl; // hello world

这个感觉挺方便的
