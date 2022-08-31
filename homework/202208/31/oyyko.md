装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构。这种类型的设计模式属于结构型模式，它是作为现有的类的一个包装。

这种模式创建了一个装饰类，用来包装原有的类，并在保持类方法签名完整性的前提下，提供了额外的功能。

也可以用于函数之上。

```cpp
template <typename T>
struct Logger;

template <typename R, typename... Args>
struct Logger<R(Args...)> {
  function<R(Args...)> m_func;
  string m_name;
  Logger(function<R(Args...)> f, const string &n) : m_func{f}, m_name{n} {}
  R operator()(Args... args) {
    cout << "Entering " << m_name << endl;
    R result = m_func(args...);
    cout << "Exiting " << m_name << endl;
    return result;
  }
};

template <typename R, typename... Args>
auto make_logger(R (*func)(Args...), const string &name) {
  return Logger<R(Args...)>(std::function<R(Args...)>(func), name);
}

double add(double a, double b) { return a + b; }
int main() {
  auto logged_add = make_logger(add, "Add");
  auto result = logged_add(, );
  return EXIT_SUCCESS;
}
```

