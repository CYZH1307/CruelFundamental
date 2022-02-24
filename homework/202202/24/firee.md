### C++: 简述 virtual function

考虑这样一个需求，希望调用某个类的某个函数的时候，能根据一些参数来选择调用哪个函数。

比如这样

```c++
void func1()
{
	cout << 1 << endl;
}
void func2()
{
	cout << 2 << endl;
}

class A
{
public:
	A(int i)
	{
	}
	void fun();
};

int main()
{

	A a1 = A(1);
	A a2 = A(2);
	a1.fun();
	a2.fun();

	return 0;
}
```

希望a1能调用func1，a2调用func2

那么可以这样做

```c++
void func1()
{
	cout << 1 << endl;
}
void func2()
{
	cout << 2 << endl;
}

class A
{
	void (*func)();

public:
	A(int i)
	{
		if (i == 1)
		{
			func = func1;
		}
		else if (i == 2)
		{
			func = func2;
		}
	}
	void fun()
	{
		func();
	}
};

int main()
{

	A a1 = A(1);
	A a2 = A(2);
	a1.fun();
	a2.fun();

	return 0;
}
```



这里是用了函数指针实现A类的多态，当然这也集成进了c++里，就是virtual函数，有virtual函数的类要维护一个虚函数表，和上面A维护一个func指针同理。