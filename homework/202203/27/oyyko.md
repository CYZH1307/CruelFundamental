## C++: 什么是noexcept?（Effective Modern C++）

要理解这个关键字 先从C++98中的`throw`关键字讲起 

throw 关键字除了可以用在函数体中抛出异常，还可以用在函数头和函数体之间，指明当前函数能够抛出的异常类型，这称为异常规范。

例如：

```cpp
void f(char c) throw(int, double) {...}
```

这个的含义是函数f只能抛出`int`和`double`类型的异常

如果抛出其他类型的异常，try 将无法捕获，并直接调用`std::unexpected`

而如果函数不会抛出任何异常，只需要加入空括号即可

```cpp
void no_ex(char c) throw() {}
```

如果函数`no_ex`抛出任何异常，try 将无法捕获，并直接调用`std::unexpected`



**C++ 规定，派生类虚函数的异常规范必须与基类虚函数的异常规范一样严格，或者更严格。只有这样，当通过基类指针（或者引用）调用派生类虚函数时，才能保证不违背基类成员函数的异常规范**。

```cpp
class Base {
  public:
    virtual int fun1(int) throw();
    virtual int fun2(int) throw(int);
    virtual string fun3() throw(int, string);
};

class Derived: public Base {
  public:
    int fun1(int) throw(int);    //错！异常规范不如 throw() 严格
    int fun2(int) throw(int);    //对！有相同的异常规范
    string fun3() throw(string); //对！异常规范比 throw(int, string) 更严格
}
```



异常规范在函数声明和函数定义中必须同时指明，并且要严格保持一致，不能更加严格或者更加宽松。



但是异常规范在C++11中被删除。

它的优点是：让程序员看到函数的定义或声明后，立马就知道该函数会抛出什么类型的异常，这样程序员就可以使用 try-catch 来捕获了。如果没有异常规范，程序员必须阅读函数源码才能知道函数会抛出什么异常。

它的缺点是：

* 嵌套的函数调用里面，A调用B，B引发了异常。则不一定体现在A的异常规范里面

* 异常规范的检查是在运行期 这会影响性能 而且不能保证所有异常都得到了处理
* 模版中无法使用
* 实际中应用较多的是：无异常和可能有异常、

因此异常规范被删除，加入`noexpect`,用来表示函数一定不会抛出异常。

1. noexcept 说明符要么出现在该函数的所有声明语句和定义语句，要么一次也不出现。
2. 函数指针及该指针所指的函数必须具有一致的异常说明。
3. 在 typedef 或类型别名中则不能出现 noexcept
4. 在成员函数中，noexcept 说明符需要跟在 const 及引用限定符之后，而在 final、override 或虚函数的 =0 之前
5. 如果一个虚函数承诺了它不会抛出异常，则后续派生的虚函数也必须做出同样的承诺；与之相反，如果基类的虚函数允许抛出异常，则派生类的虚函数既可以抛出异常，也可以不允许抛出异常

**编译器不会检查带有 noexcept 说明符的函数是否有 throw**

这种情况下会直接调用`std::terminate`并且不会栈展开

**noexcept 除了可以用作说明符（Specifier），也可以用作运算符（Operator）**。noexcept 运算符是一个一元运算符，它的返回值是一个 bool 类型的右值常量表达式，用于表示给定的表达式是否会抛出异常

```cpp
void f() noexcept {
}

void g() noexcept(noexcept(f)) { // g() 是否是 noexcept 取决于 f()
    f();
}
```

**析构函数默认都是 noexcept 的**。C++ 11 标准规定，类的析构函数都是 noexcept 的，除非显示指定为 `noexcept(false)`

在为某个异常进行栈展开的时候，会依次调用当前作用域下每个局部对象的析构函数，如果这个时候析构函数又抛出自己的未经处理的另一个异常，将会导致 `std::terminate`。所以析构函数应该从不抛出异常



与容器操作的关联：

一个 `std::vector<T>`，若要进行 `reserve` 操作，一个可能的情况是，需要重新分配内存，并把之前原有的数据拷贝（copy）过去，但如果 T 的移动构造函数是 noexcept 的，则可以移动（move）过去，大大地提高了效率。

为什么在移动构造函数是 noexcept 时才能使用？这是因为它执行的是 Strong Exception Guarantee，发生异常时需要还原，也就是说，你调用它之前是什么样，抛出异常后，你就得恢复成啥样。但对于移动构造函数发生异常，是很难恢复回去的，如果在恢复移动（move）的时候发生异常了呢？但复制构造函数就不同了，它发生异常直接调用它的析构函数就行了。



可以使用检测函数来检测析构函数的性质：

`is_move_constructible`，用于检测类型是否可以被移动构造，如果对应类的移动构造函数被声明为 “delete”，那么这个函数的实例值便为 “false”。

 `is_trivially_move_constructible`，它用于检测类型是否具有普通的移动构造函数，为了满足“普通”这一特点，我们需要保证这个类型符合多项约束条件，比如，类型没有虚函数，没有虚基类，没有任何不稳定的非静态成员，等等。

 `is_nothrow_move_constructible`，同它的名称一样，它的功能是用来检测类是否具有不会抛出异常的移动构造函数。当条件成立时，这个函数实例所对应的值便为 “true”。

