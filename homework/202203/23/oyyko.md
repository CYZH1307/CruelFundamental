2022 03 23 Oyyko 残酷八股打卡

## C++: 讲一讲 std::move 和 std::forward （Effective Modern C++）

### 前置知识

C++中每个表达式都有两个属性：类型和值类别。

`int`,`int&`,`int*`,`double`等都是类型

而值类别分为：lvalue,xvlaue,prvalue,glvalue,rvalue

- lvalue是指：拥有身份且不可被移动的表达式。
- xvalue是指：拥有身份且可被移动的表达式。
- prvalue是指：不拥有身份且可被移动的表达式。
- glvalue是指：拥有身份的表达式，lvalue和xvalue都是glvalue。
- rvalue是指：可被移动的表达式。prvalue和xvalue都是rvalue。

拥有身份的含义是：可以确定表达式与另一个表达式是否指代同一个实体。例如两个右值1和1，你无法区分它们是一个1还是说只是值相等。但是`int a=1,b=1;`里面的a和b虽然值都是1，但是a和b不是一个实体。一般来讲通过比较两个左值的内存地址来区分它们是否指代同一个实体。

glvalue可以自动转换为prvalue，例如`int x=a;` a会自动转换为prvalue

lvalue具有以下特征：

- 可以通过取址运算符获取其地址
- 可以用来初始化左值引用

prvalue具有以下特征：

- prvalue不会是多态的
- prvalue不会是不完全类型
- prvalue不会是抽象类型或数组

xvalue也叫将亡值，它一般接近了生命周期的末尾，与移动语义有关。

C++11之前，可以用`const type&`的方式来引用右值例如`const int& x=3;`

C++11增加了右值引用。右值引用延长了临时值的生命周期！

例如：

```cpp
void func(X& x) {
    cout << "lvalue reference version" << endl;
}

void func(X&& x) {
    cout << "rvalue reference version" << endl;
}

X returnX() {
    return X();
}

int main() {
    X x;
    func(x); // lvalue
    func(returnX()); // rvalue
}
```

- 左值引用：可以绑定到左值（非const），也可以绑定到右值（const）
- 右值引用：只能绑定到右值

### std::move

move并不执行任何移动操作，也不会生成任何一byte的可执行代码。它所做的仅仅是一个强制类型转换。把参数强制转换为匿名右值引用。（注意：具名右值引用是左值，而匿名右值引用是右值。）

例如：

```cpp
    string a = "hello";
    string b = move(a);
    cout << b.size() << " " << a.size();// 5 0
```

这里我们把a用move转换为右值，以此来触发string的移动构造函数，从而a的size变为0，它的值给了b。且避免了深拷贝。（应该是直接把指向字符串的指针给了b，并不是复制了5个char）

## std::forward

与move类似，forward也执行一个转换操作，但是是有条件的转换。

std::forward会根据传递给func函数实参（注意，不是形参）的左/右值类型进行转发。当传给func函数左值实参时，forward返回左值引用。而当传入func的实参为右值时，forward返回匿名右值引用。

用途：配合万能引用实现完美转发。用于给函数提供右值类型的参数，使得其重载右值版本。

```cpp
void f(A &x)
{
    cout << "L\n";
}

void f(A &&x)
{
    cout << "R\n";
}

template <class T>
void do_f(T &&x)
{
    f(forward<T>(x));
}

int main()
{
    A a;
    A &x = a;
    do_f(A{}); // R
    do_f(a);   // L
    do_f(x);   // L
    A &&y = A{};
    f(y);             // R
    f(forward<A>(y)); // L
}
```

注意：

```cpp
A &&y = A{};
f(y);             // L
f(forward<A>(y)); // R
```

这个例子说明了forward的重要用途。由于具名右值引用是左值。因此需要forward来调用右值版本的重载。