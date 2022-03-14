#### C++: constructor和desctructor能virtual吗

构造函数不能virtual，析构函数最好virtual

我觉得是类生命周期的问题

c++原生支持的类生命流程是 父类构造->子类构造->子类析构->父类析构

对于构造函数上加virtual，实际上是想要实现一个某子类特有的初始化，对于这个需求，应当是在基类中声明一个virtual init()函数，然后用工厂模式之类的加入到类的生命周期中

父类构造->子类构造->init->子类析构->父类析构

对于析构函数，调用完子类析构函数后一定会调用父类的析构函数，所以要使类正确的被析构，必须要知道这个变量的真实面目，实际上虚函数本身就带有这一信息

```c++
#include <iostream>
using namespace std;

class A
{
public:
	virtual ~A()
	{
		cout << "A";
	}
};
class B : public A
{
public:
	~B()
	{
		cout << "B";
	}
};

int main()
{
	A *a = new B();
	delete (a);
	// B A
	return 0;
}
```

```c++
#include <iostream>
using namespace std;

class A
{
public:
	~A()
	{
		cout << "A";
	}
};
class B : public A
{
public:
	~B()
	{
		cout << "B";
	}
};

int main()
{
	A *a = new B();
	delete (a);
	//  A
	return 0;
}
```

容易引起误解的是如果使用了虚函数，析构函数只会调用子类的析构函数，**不是的**，**他仍然会逐级向上调用**。

如果想要实现某个类特有的删除方法，同样需要在基类声明一个虚函数del，完善生命周期

父类构造->子类构造->init->del->子类析构->父类析构

所以实际上这一块是对c++流程的妥协

在python的init函数中提供了更为灵活的控制，他把选择权交给了用户，也就是要调用super().init才会调用父类的构造函数