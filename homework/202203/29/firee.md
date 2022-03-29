###  C++: 什么是constexpr？（Effective Modern C++）

```c++
#include <iostream>
#include <cstring>
#include <cstdio>
using namespace std;

template <int N>
class C
{
};

const int getReadonlyNum(int x)
{
	return x;
}
constexpr int getConstNum(int x)
{
	return x;
}

int main()
{
	C<getReadonlyNum(1)> c1;//编译报错
	C<(getConstNum(1))> c2;//编译通过

	return 0;
}
```

constexpr的语义是真const，const的语义是readonly