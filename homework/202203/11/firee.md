#### C++: Lock-free queue 的实现。lock-free和lock-based的区别

##### Lock-free queue 的实现(猜测)

```
<template T>
class LockFreeQueue
{
	int head,tail;
	SomeQueue<T> q;
	Queue()
	{
		//针对head和tail应该会有一些循环的操作，先不考虑这个
	}
	void push(T x)
	{
		int t=tail;
		while(!CAS(t,&tail,tail+1)) t=tail;
		q.push(x,t);
	}
	T pop()
	{
		if(head==tail)	return 0;
		int t=head;
		while(!CAS(t,&head,head+1))	t=head;
		return q.pop(x,t);
	}
};
//SomeQueue 是一种能在位置x插入或取出数据的一种数据结构，map说不定可以?


```

##### lock-free和lock-based的区别

前者不用锁，通过逻辑规避了同一内存被同时使用的可能。