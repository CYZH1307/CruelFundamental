#### C++: class initialization 的顺序。如果A class里面有一个member是B class的instance，A class 从class D derived得到，在创建A的instance的时候，initialization的顺序是怎样的。



```c++
#include <iostream>
using namespace std;

class A
{
public:
	int a;
	A()
	{
		cout << "A init" << endl;
	}
};

class B
{
public:
	B()
	{
		cout << "B init" << endl;
	}
};

class C : public A
{
public:
	B b;
    const int x;
	C():x(0)
	{
		cout << "C init" << endl;
	}
};

int main()
{
	C();
	// A init
	// B init
	// C init
	return 0;
}
```

测试可以看出init的顺序是父类->成员变量->类的构造函数,一些const变量显然要比类先获得值，在构造函数后面加：可以赋值

