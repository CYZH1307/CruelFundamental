C++是一门伟大的语言，永远给程序员最大的设计自由， 未使用的特性从不产生副作用，新版本永远完全兼容旧版本。 C++11先前被称作C++0x，即ISO/IEC 14882:2011，是C++编程语言的一个标准。

之前的C++标准包括C++98、C++03。 虽然此后的[C++14]才是C++的现行标准，但C++14旨在对C++11的小扩展（漏洞修复、功能改进），而C++11仍然是一个具有热度的关键词。

C++11的特性主要包括下面几个方面：

提高运行效率的语言特性：右值引用、泛化常量表达式
原有语法的使用性增强：初始化列表、统一的初始化语法、类型推导、范围for循环、Lambda表达式、final和override、构造函数委托
语言能力的提升：空指针nullptr、default和delete、长整数、静态assert
C++标准库的更新：智能指针、正则表达式、哈希表等
本文就从这几个方面来介绍C++11中那些令人心动的新特性。

右值引用
C++03及之前的标准中，右值是不允许被改变的，实践中也通常使用const T&的方式传递右值。然而这是效率低下的做法，例如：

Person get(){
    Person p;
    return p;
}
Person p = get();
上述获取右值并初始化p的过程包含了Person的3个构造过程和2个析构过程。 这是C++广受诟病的一点，但C++11的右值引用特性允许我们对右值进行修改。 借此可以实现move语义，即从右值中直接拿数据过来初始化或修改左值， 而不需要重新构造左值后再析构右值。一个move构造函数是这样声明的：

class Person{
public:
    Person(Person&& rhs){...}
    ...
};
泛化的常量表达式
还记得刚开始学习C++给你的苦恼吗？你看：

int N = 5;
int arr[N];
编译器会报错：error: variable length array declaration not allowed at file scope int arr[N];，但N就是5啊！不过编译器不知道这一点，于是我们需要声明为const int N = 5才可以。但C++11的泛化常数给出了解决方案：

constexpr int N = 5;    // N 变成了一个只读的值
int arr[N];             // OK
constexpr告诉编译器这是一个编译期常量，甚至可以把一个函数声明为编译期常量表达式。

constexpr int getFive(){ return 5; }
int arr[getFive() + 1];
初始化列表
接下来几个特性属于原有语言特性的使用性增强。这意味着这些操作原来也是可以实现的， 不过现在语法上更加简洁。比如首先要介绍的初始化列表。

在C++手稿：函数与参数一文中提到了可变参数（比如printf的参数列表）的用法。 需要借助于stdarg.h来操作参数堆栈，而C++11提供了initializer_list来接受变长的对象初始化列表：

class A{
public:
    A(std::initializer_list<int> list);
};
A a = {1, 2, 3};
注意初始化列表特性只是现有语法增强，并不是提供了动态的可变参数。该列表只能静态地构造。

统一的初始化语法
这是一个苦恼我很久的问题：不同的数据类型具有不同的初始化语法。如何初始化字符串？如何初始化数组？如何初始化多维数组？如何初始化对象？C++11给出了统一的初始化语法。

struct S1{ int x; float y; };
struct S2{
    S2(int _x, float _y): x(_x), y(_y){}
private:
    int x;
    float y;
};
S1 s1{1, 2.1f};
S2 s2{1, 2.1f};

// 标准库
std::vector<int> v{1, 2, 3};

// 返回值
std::vector<int> func(){
    return {1, 2, 3};
}
此外，你是否还记得在C98标准中，只有静态常量整型（long, short, enum）才可以就地初始化，C++11允许任何变量进行就地初始化，但只支持=和{}两种方式。例如：

class A{
    string str = "sss";     // OK
    int i {23};             // OK
    int j (23);             // Error
};
在Effective C++: Item 2中提到，对于就地初始化的静态常量整型，如果不给出声明就无法取地址。在C++11中这一点仍然成立。

类型推导
C++提供了auto和decltype来静态推导类型，在我们知道类型没有问题但又不想完整地写出类型的时候， 便可以使用静态类型推导。

for(vector<int>::const_iterator it = v.begin(); it != v.end(); ++it);
// 可以改写为
for(auto it = v.begin(); it != v.end(); ++it);
虽然写起来和动态语言（如JavaScript的var）很像，但C++仍然是强类型的，会执行静态类型检查的语言。 这只是语法上的简化，并未改变C++的静态类型检查。

decltype用于获取一个表达式的类型，而不对表达式进行求值（类似于sizeof）。decltyp(e)规则如下：

若e为一个无括号的变量、函数参数、类成员，则返回类型为该变量/参数/类成员在源程序中的声明类型；
否则的话，根据表达式的值分类（value categories），设T为e的类型：
若e是一个左值（lvalue，即“可寻址值”），返回T&；
若e是一个临终值（xvalue），则返回值为T&&；
若e是一个纯右值（prvalue），则返回值为T。
来看例子：

const std::vector<int> v(1);
const int&& foo();      // 返回临终值：生命周期已结束但内存还未拿走

auto a = v[0];          // a 为 int
decltype(v[0]) b = 0;  // b 为 const int&
                        // 即 vector<int>::operator[](size_type) const 的返回值类型
auto c = 0;             // c, d 均为 int
auto d = c;           
decltype(c) e;          // e 为 int，即 c 的类型
decltype((c)) f = e;    // f 为 int&，因为 c 是左值
decltype(0) g;          // g 为 int，因为 0 是右值
基于范围的for循环
Boost中定义了很多"范围"，很多标准库函数都使用了范围风格的实现。这一概念被C++11提了出来：

int arr[5];
vector<int> v;

for(int x: arr);
for(const int& x: arr);
for(int x: v);
Lambda表达式
Lambda表达式可以简化STL用到的函数对象或指针（如less模板，这些操作函数成为谓词函数）的定义和传递，一个简单的Lambda函数是这样定义的：

[](int x, int y) -> int { return x + y; }
例如用for_each和Lambda表达式来实现accumulate的功能：

std::vector<int> someList;
int total = 0;
std::for_each(someList.begin(), someList.end(), [&total](int x) { total += x; });
std::cout << total;
其中的total以传引用的方式进入Lambda函数定义的闭包中。

构造函数委托
在C#和Java中，一个构造函数可以调用另一个来实现代码复用，但C++一直不允许这样做。现在可以啦：

class SomeType {
  int number; string name;
  SomeType( int i, string& s ) : number(i), name(s){}
public:
  SomeType( )           : SomeType( 0, "invalid" ){}
  SomeType( int i )     : SomeType( i, "guest" ){}
  SomeType( string& s ) : SomeType( 1, s ){ PostInit(); }
};
final 和 override
C++借由虚函数实现运行时多态，但C++的虚函数又很多脆弱的地方：

无法禁止子类重写它。可能到某一层级时，我们不希望子类继续来重写当前虚函数了。
容易不小心隐藏父类的虚函数。比如在重写时，不小心声明了一个签名不一致但有同样名称的新函数。
在Effective C++: Item 33一文中详细讨论了父类名称隐藏的问题。

C++11提供了final来禁止虚函数被重写/禁止类被继承，override来显示地重写虚函数。 这样编译器给我们不小心的行为提供更多有用的错误和警告。

struct Base1 final { };     
struct Derived1 : Base1 {};         // 编译错：Base1不允许被继承

struct Base2 {
    virtual void f1() final;
    virtual void f2();
};
struct Derived2 : Base2 {
    virtual void f1();              // 编译错：f1不允许重写
    virtual void f2(int) override;  // 编译错：父类中没有 void f2(int)
};
空指针nullptr
现在开始我们来介绍C++11提供的核心语言能力的增强。先来看看空指针，C语言中用#define NULL 0表示空指针，有时会使得语义不明确，例如：

void foo(char*);
void foo(int);
foo(NULL)的直观语义是传入空指针，事实却会调用void foo(int)。因此C++11提供nullptr关键字来表示空指针：

char *p = nullptr;
int i = nullptr;        // 错误
foo(p);                 // 调用 foo(char*)
default和delete
我们知道编译器会为类自动生成一些方法，比如构造和析构函数（完整的列表见Effective C++: Item 5）。现在我们可以显式地指定和禁止这些自动行为了。

struct S{
    S() = defauult;     // 声明一个自动生成的函数
    S(T value);
    void *operator new(size_t) = delete;    // 禁止生成new运算符
};
在上述S中定义了S(T value)构造函数，因此编译器不会默认生成一个无参数的构造函数了， 如果我们需要可以手动声明，或者直接 = default。

long long int
这是一个至少64位的整数类型，其实当前编译器都已经支持了，现在它进入标准啦！

静态assertion
C++提供了两种方式来assert：一种是assert宏，另一种是预处理指令#error。 前者在运行期起作用，而后者是预处理期起作用。它们对模板都不好使，因为模板是编译期的概念。 static_assert关键字的使用方式如下：

template< class T >
struct Check {
  static_assert( sizeof(int) <= sizeof(T), "T is not big enough!" ) ;
} ;
智能指针
接下来介绍C++11对于C++标准库的变更。C++11把TR1并入了进来，废弃了C++98中的auto_ptr， 同时将shared_ptr和uniq_ptr并入std命名空间。 智能指针在Effective C++: Item 13中已经有不少讨论了。这里给一个例子：

int main(){
    std::shared_ptr<double> p_first(new double) ;
    {
        std::shared_ptr<double> p_copy = p_first ;
        *p_copy = 21.2;
    }  // p_copy 被销毁，里面的 double 还有一个引用因此仍然保持
    return 0;  // p_first 及其里面的 double 销毁
}
正则表达式
这个任何一门现代的编程语言都会提供的特性终于进标准啦：

const char *reg_esp = "[ ,.\\t\\n;:]";
std::regex rgx(reg_esp) ;
std::cmatch match ;  
const char *target = "Polytechnic University of Turin " ;

if( regex_search( target, match, rgx ) ) {
  const size_t n = match.size();
  for( size_t a = 0 ; a < n ; a++ ) {
    string str( match[a].first, match[a].second ) ;
    cout << str << "\n" ;
  }
}
上述代码段来自Wikipedia: https://zh.wikipedia.org/wiki/C%2B%2B11

增强的元组
在C++中本已有一个pair模板可以定义二元组，C++11更进一步地提供了边长参数的tuple模板：

typedef std::tuple< int , double, string       > tuple_1 t1;
typedef std::tuple< char, short , const char * > tuple_2 t2 ('X', 2, "Hola!");
t1 = t2 ;       // 隐式类型转换
哈希表
这是在Leetcode上第一次看到的。。。C++的map, multimap, set, multiset使用红黑树实现， 插入和查询都是O(lgn)的复杂度，但C++11为这四种模板类提供了哈希实现以达到O(1)的复杂度：

散列表类型	有无关系值	接受相同键值
std::unordered_set	否	否
std::unordered_multiset	否	是
std::unordered_map	是	否
std::unordered_multimap	是	是
