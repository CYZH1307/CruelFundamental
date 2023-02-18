# C++: Lock-free queue 的实现。lock-free和lock-based的区别

## Lock Free Queue

**无锁队列**是指多线程对于队列的操作无需添加粒度较大的独占锁`mutex`, 而是通过细粒度的`CAS(compare and swap)`原子操作实现多线程下的同步. 其主要思想是: 使用`mutex`的锁的代价太高, 那就使用更小粒度的锁, 甚至这个锁的粒度是一条赋值指令. 

很多情况下, 一个完整事件的处理流程可以拆分成几个子事件. 虽然各子事件有顺序关系, 但无强烈的时间上的约束. 这样当该阶段处理完成后, 我们可以给下一个阶段传递一条消息. 下阶段接收到消息后, 再进行处理. 这里传递消息的数据结构可以是`无锁队列`. 保证了高并发和分布式场景下的快速处理需求和一定的前后顺序. 这就类似于`生产者-消费者`问题: 多个生产者产生多个事件, 给下一阶段的消费者处理.

本篇博客主要参考了[酷壳陈皓关于无锁队列的讲解博客](https://coolshell.cn/articles/8239.html)以及[原始的论文-Implementing Lock-Free Queues](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.53.8674&rep=rep1&type=pdf)和[改进的论文-Simple, Fast, and Practical Non-Blocking and Blocking ConcurrentQueue Algorithms](https://www.cs.rochester.edu/u/scott/papers/1996_PODC_queues.pdf).


## CAS操作

`Compare And Swap (CAS)`是现代操作系统支持的原子性操作, 如X86中`CMPXCHG`汇编指令. 其实现逻辑如下:

```cpp
bool compare_and_swap(int *addr, int oldVal, int newVal)
{
    if (*addr != oldVal)
        return false;

    *addr = newVal;
    return true;
}
```

`addr`指向内存中的某个变量, 如果该内存值为`oldVal`, 那么就将其置`newVal`并返回成功; 否则直接返回失败.

有了`CAS`操作, 多线程情况下我们可以保证某个共享变量值为我们期待值时才执行某些操作, 从而实现同步.

- GCC的CAS

```cpp
bool __sync_bool_compare_and_swap (type *ptr, type oldval type newval, ...)
type __sync_val_compare_and_swap (type *ptr, type oldval type newval, ...)
```

- cpp11的CAS

```cpp
template< class T >
bool atomic_compare_exchange_weak( std::atomic* obj,
                                   T* expected, T desired );
template< class T >
bool atomic_compare_exchange_weak( volatile std::atomic* obj,
                                   T* expected, T desired );
```

## 逻辑实现

可以通过链表或数组实现. 数组实现的话由于数组大小需要指定而队列长度不一定知道, 因此需要实现循环队列. 存在一定的局限性.

### 基于链表的实现

用链表实现队列的时候, 为了避免麻烦的边界判断, 我们可以添加`头结点`. 初始化时申请头结点, 并且`head`指针和`tail`指针均指向该结点. 


1. **入队操作**

    设入队新建结点的指针为`p`, 则`p -> next = nullptr`, 入队操作需要两步实现. 
    1.  **tail -> next = p**
    2.  **tail = p**

    为了保证第一步的并发正确性. 我们持续不断获取`tail`的快照, 记为`cur`. 直到`CAS(cur -> next, nullptr, p)`. 即如果`cur -> next = nullptr`成立, 则当前`cur`一定为正确的`tail`. 接着我们将`cur -> next`更新成`p`. 这样是正确的. 因为当某个线程执行完这一步后. `tail`指针还未更新成`p`, 这样其他所有线程都在轮询等待`tail`指针的`next`为空, 无法入队.

    当轮询完成第一步后, 我们退出循环. 然后执行`CAS(tail, cur, p)`. 即将尾指针置为新入队结点.

    - **结点定义**

    ```cpp
    // 使用cpp11实现, 用了atomic类对象的方法实现CAS操作
    // 结点定义
    struct Node {
		T val;
		atomic<Node*> next;
		Node () : val(T()), next(atomic<Node*>(nullptr)) {}
		Node (const T& _v) : val(_v), next(atomic<Node*>(nullptr)){}
		Node (const T& _v, atomic<Node*> _next) : val(_v), next(_next) {}
	};

    ```

    - **入队(VERSION-1)**
  
    ```cpp
    // VERSION-1
    void enqueue(T _val) {
        // p指向插入结点
        Node* p = new Node(_val);
        Node* cur;
        // 
        Node* null = nullptr;

        while (true) {
            cur = tail.load();
            // CAS(cur -> next, nullptr, p)
            if (cur -> next.compare_exchange_weak(null, p) == true)
                break;
        }
        // CAS(tail, cur, p)
        tail.compare_exchange_weak(cur, p);
    }
    ```

    以上操作的流程会出现一个问题. 因为只有入队的线程才能将`tail`指针更新, 因此如果该线程入队后还未更新`tail`就结束了. 那么其他所有入队线程都将死锁. 因为他们`CAS(cur -> next, nullptr, p)`时, `cur -> next`一定不为空, 此时可能出现`cur -> next = p`.

    为了解决上述可能存在的问题. 我们主要**解决思路**是让其他线程也可以移动`tail`指针. 

    一个简单的方法是, 如果当前线程`CAS(cur -> next, nullptr, p)`入队失败了, 那么说明当前`cur`不是真正的`tail`, 那么我们尝试`CAS(tail, cur, cur -> next)`. 即尝试让`tail`往后走. 然后再获取`tail`的快照, 按照之前的流程轮询(Retry-loop).

   - **入队(VERSION-2)**
  
    ```cpp
    // VERSION-2
    void enqueue(T _val) {
        // p指向插入结点
        Node* p = new Node(_val);
        Node* cur;
        // 
        Node* null = nullptr;

        while (true) {
            cur = tail.load();
            // CAS(cur -> next, nullptr, p)
            if (cur -> next.compare_exchange_weak(null, p) == true)
                break;
            // CAS(tail, cur, cur -> next)
            else 
                tail.compare_exchange_weak(cur, cur -> next)

        }
        // CAS(tail, cur, p)
        tail.compare_exchange_weak(cur, p);
    }
    ```

    较为复杂的方法是, 获取`tail`的快照`cur`, 并记录`cur -> next`为`next`. 接着判断`cur`是否为`tail`. 如果不为就重新开始. 然后判断`next`是否为空. 如果不为空, 我们就尝试更新`tail`, 更新的语句是`CAS(tail, cur, next)`. 如果为空. 我们就尝试`CAS(tail -> next, next, p)`入队(此时`next = nullptr`).

    - **入队(VERSION-3)**
  
    ```cpp
	void enqueue (T _val) {
		// 插入结点为p
		Node* p = new Node(_val);
		Node *cur, *next;
		Node* null = nullptr;
		while (true) {
			// cur 指针为尾结点的快照
			cur = tail.load();
			// next 指针为为尾结点下一个结点
			next = cur -> next;
			// 如果尾指针被移动, 则重新获取cur和next (可能被其他线程移动了, 重新获取以减少CAS操作次数)
			if (cur != tail.load())
				continue;
			if (next != nullptr) {
				// 当next指针不为空时, 尝试移动tail指针, 防止出现死锁
				tail.compare_exchange_weak(cur, next);
				continue;
			}
			// 实现第一步操作: tail -> next = p
			if (cur->next.compare_exchange_weak(null, p) == true)
				break;
		}
		// 实现第二步操作: tail = p
		tail.compare_exchange_weak(cur, p);
	};
    ```

2. **出队操作**

    出队操作类似于入队操作的思路. 我们首先获取`head`的快照`cur`. 然后判断`cur -> next`是否为空, 如果为空说明此时队列为空, 返回队列为空的信号. 如果不为空, 我们`CAS(head, cur, cur -> next)`来重置`head`的指针. 然后返回`cur -> next -> val`, 因为此时`cur`指向之前的头结点, `cur -> next -> val`为出队前的队头. 

	- **出队(VERSION-1)**
  
    ```cpp
    bool dequeue (T& _ret) {
        // 返回true: 出队成功, 返回值存在 _ret参数中
		Node* cur;

		while (true) {
            cur = head.load();
            if (cur -> next == nullptr)
                return false;
            // CAS(head, cur, cur -> next)
			if (head.compare_exchange_weak(cur, cur -> next))
				break;
		}
        _ret = cur -> next -> val;
		// 释放原头结点
		delete cur;
		return true;
	}
    ```

    上述思路存在几个问题. 首先比较严重的是, 如果我们`CAS(head, cur, cur -> next)`成功了, 也就是逻辑上执行了`head = head -> next`, 此时如果另外一个线程也执行了出队操作, 并且已经将队头free掉了. 那我们再访问`cur -> next -> val`时明显就出现了访问非法内存. 因此一个核心问题是: **在获取到其队头元素值之前, 其他线程不能执行出队操作**. 
    
    为了保证队头结点不被free掉. 我们可以在`CAS`操作之前访问. 这样保证`CAS`操作成功后, 我们一定已经拿到了队头. 即使此时其他线程执行出队操作. 也不影响该线程的正确性. 具体而言, 我们定义一个`next`指针, 初始`next = cur -> next`. 然后判断`cur`是否等于`head`. 如果不等于则重新获取快照(减少不必要的`CAS`操作). 如果等于, 接着判断`next`是否为空, 如果为空说明此时队列为空, 返回. 接着**先记录`next`结点的值**, 然后尝试通过`CAS`操作移动`head`. 即`CAS(head, cur, cur -> next)`.
    
    - **出队(VERSION-2)**
  
    ```cpp
    bool dequeue (T& _ret) {
		Node *cur, *next;

		while (true) {
			cur = head.load();
			next = cur->next;
			// 如果头指针被移动, 则重新获取其快照 (可能被其他线程移动了, 重新获取以减少CAS操作次数)
			if (cur != head.load())
				continue;
			// 队列为空, 出队失败
			if (next == nullptr)
				return false;

			// 在CAS操作前记录返回值, 如果在CAS操作之后则该结点有可能被其他线程free
			_ret = next->val;

			// 实现出队的操作. head = head -> next
			if (head.compare_exchange_weak(cur, next))
				break;
		}
		// 释放原头结点
		delete cur;
		return true;
	}
    ```

    上述版本还存在一个问题. 如果在判断`next == nullptr`时, 一个入队操作执行完成了第一步, 还没移动`tail`指针. 此时如果队列只有这个一个元素. 那么`head = tail` 且 `next = p`. 出队的话会将`tail`指向被free的原头结点. 具体图示可以查看[酷壳陈皓关于无锁队列的讲解博客](https://coolshell.cn/articles/8239.html)

    解决的思路和入队解决死锁的思路是一样的. 即当我们发现`head == tail` 且 `next != nullptr`时, 我们尝试更新`tail`即可.

    - **出队(VERSION-3)**
  
    ```cpp
	bool dequeue (T& _ret) {
		Node *nowHead, *nowTail, *next;

		while (true) {
			nowHead = head.load(), nowTail = tail.load();
			next = nowHead -> next;
			// 如果头指针被移动, 则重新获取其快照 (可能被其他线程移动了, 重新获取以减少CAS操作次数)
			if (nowHead != head.load())
				continue;
			// 队列为空, 出队失败
			if (nowHead == nowTail and next == nullptr)
				return false;

			// tail指针未正确取值, 尝试移动tail
			if (nowHead == nowTail and next != nullptr) {
				tail.compare_exchange_weak(nowTail, next);
				continue;
			}

			// 在CAS操作前记录返回值, 如果在CAS操作之后则该结点有可能被其他线程free
			_ret = next->val;

			// 实现出队的操作. head = head -> next
			if (head.compare_exchange_weak(nowHead, next))
				break;
		}
		// 释放原头结点
		delete nowHead;
		return true;
	}
    ```

## 代码实现

首先通过上述分析, 使用`cpp11`进行了代码实现. 接着对实现的代码进行**正确性测试、内存检查和性能测试**. 

- 正确性检查思路. 检查每个物品是否只入队一次且只出队一次.
- 内存检查. CLion中使用`Valgrind`的`Memcheck`工具在`WSL`环境下进行检查.
- 性能测试. 与使用`mutex`的版本进行比较.

0. **setting.h**: 全局设置

```cpp
//
// Created by Jsss on 2022/3/12.
//

#ifndef LOCKFREEQUEUE_SETTING_H
#define LOCKFREEQUEUE_SETTING_H

// 生产者数量
int producerWorks = 4;

// 消费者数量
int consumerWorks = 4;

// 每个生产者生产的个数
int producerNums = 1e6;

// 重复次数
int epochs = 10;

#endif //LOCKFREEQUEUE_SETTING_H

```

1. **LockFreeQueue.h**: 无锁队列的实现

```cpp
//
// Created by Jsss on 2022/3/12.
//

#ifndef LOCKFREEQUEUE_LOCKFREEQUEUE_H
#define LOCKFREEQUEUE_LOCKFREEQUEUE_H
#pragma once

#include <atomic>

using std::atomic;


template <typename T>
class LockFreeQueue{

public:
	struct Node {
		T val;
		atomic<Node*> next;
		Node () : val(T()), next(atomic<Node*>(nullptr)) {}
		Node (const T& _v) : val(_v), next(atomic<Node*>(nullptr)){}
		Node (const T& _v, atomic<Node*> _next) : val(_v), next(_next) {}
	};


	LockFreeQueue () {
		Node* dummy = new Node;
		head.store(dummy);
		tail.store(dummy);
	};

	// 入队操作
	void enqueue (T _val) {
		// 插入结点为p
		Node* p = new Node(_val);
		Node *cur, *next;
		Node* null = nullptr;
		while (true) {
			// cur 指针为尾结点的快照
			cur = tail.load();
			// next 指针为为尾结点下一个结点
			next = cur -> next;
			// 如果尾指针被移动, 则重新获取cur和next (可能被其他线程移动了, 重新获取以减少CAS操作次数)
			if (cur != tail.load())
				continue;
			if (next != nullptr) {
				// 当next指针不为空时, 尝试移动tail指针, 防止出现死锁
				tail.compare_exchange_weak(cur, next);
				continue;
			}
			// 实现第一步操作: tail -> next = p
			if (cur->next.compare_exchange_weak(null, p) == true)
				break;
		}
		// 实现第二步操作: tail = p
		tail.compare_exchange_weak(cur, p);
	};

	// version 1
//	bool dequeue (T& _ret) {
//		Node *nowHead, *next;
//
//		while (true) {
//			nowHead = head.load();
//			next = nowHead->next;
//			// 如果头指针被移动, 则重新获取其快照 (可能被其他线程移动了, 重新获取以减少CAS操作次数)
//			if (nowHead != head.load())
//				continue;
//			// 队列为空, 出队失败
//			if (next == nullptr)
//				return false;
//			// 在CAS操作前记录返回值, 如果在CAS操作之后则该结点有可能被其他线程free
//			_ret = next->val;
//			// 实现出队的操作. head = head -> next
//			if (head.compare_exchange_weak(nowHead, next))
//				break;
//		}
//		// 释放原头结点
//		delete nowHead;
//		return true;
//	}

	// version 2
	bool dequeue (T& _ret) {
		Node *nowHead, *nowTail, *next;

		while (true) {
			nowHead = head.load(), nowTail = tail.load();
			next = nowHead -> next;
			// 如果头指针被移动, 则重新获取其快照 (可能被其他线程移动了, 重新获取以减少CAS操作次数)
			if (nowHead != head.load())
				continue;
			// 队列为空, 出队失败
			if (nowHead == nowTail and next == nullptr)
				return false;
			// tail指针未正确取值, 尝试移动tail
			if (nowHead == nowTail and next != nullptr) {
				tail.compare_exchange_weak(nowTail, next);
				continue;
			}
			// 在CAS操作前记录返回值, 如果在CAS操作之后则该结点有可能被其他线程free
			_ret = next->val;
			// 实现出队的操作. head = head -> next
			if (head.compare_exchange_weak(nowHead, next))
				break;
		}
		// 释放原头结点
		delete nowHead;
		return true;
	}

	// 删除拷贝构造函数和拷贝赋值函数
	LockFreeQueue (const LockFreeQueue& ) = delete;
	LockFreeQueue operator = (const LockFreeQueue&) = delete;


private:
	atomic<Node*> head, tail;
};


#endif //LOCKFREEQUEUE_LOCKFREEQUEUE_H
```

2. **check.cpp**: 正确性检查
   
```cpp
//
// Created by Jsss on 2022/3/12.
//

#include <iostream>
#include <vector>
#include "LockFreeQueue.h"
#include "setting.h"
#include <thread>
#include <chrono>

using std::vector;
using std::thread;
using std::cout;

int all = producerWorks * producerNums;

// 记录物品的生产和消费
vector<atomic<int>> in(all), out(all);

// 记录生产者和消费者完成数目
atomic<int> prodFinish(0);

LockFreeQueue<int> qu;

void produce (int _id) {
	// 确定生产者生产编号的范围, 保证每个生产者生产的物品唯一
	int L = _id * producerNums, R = (_id + 1) * producerNums - 1;

	for (int u = L; u <= R; u ++ ) {
		qu.enqueue(u);
		in[u].fetch_add(1) ;
	}
	// 生产完毕则, finish个数 + 1
	cout << "produce " << _id << " finish.\n";
	prodFinish.fetch_add(1);
}

void consume () {
	// 必须使用CAS操作判断, 如果prodFinish的值不为producerWorks的数目, 即生产者还在生产, 就继续尝试消费
	int cur;
//	while (prodFinish.load() < producerWorks) {
	while (prodFinish.compare_exchange_weak(producerWorks, producerWorks) == false) {
		// 取成功则对应位置的 out + 1
		while (qu.dequeue(cur))
			out[cur].fetch_add(1);
	}
}

void lastConsume () {
	int cur;
	while (qu.dequeue(cur))
		out[cur].fetch_add(1);
}

void check() {
	int inCnt = 0, outCnt = 0;
	for (int i = 0; i < all; i ++ )
		if (in[i].load() != 1) {
			cout << "produce object " << i << " error, val = " << in[i].load() << '\n';
			inCnt ++ ;
		}

	for (int i = 0; i < all; i ++ )
		if (out[i].load() != 1) {
			cout << "consume object " << i << " error, val = " << out[i].load() << '\n';
			outCnt ++ ;
		}

	cout << "\nerror in count = " << inCnt << '\n';
	cout << "error out count = " << outCnt << '\n';
}

int main() {
	std::chrono::time_point<std::chrono::system_clock> start_time = std::chrono::system_clock::now();

	// 初始化in 和 out (atomic类禁止拷贝构造和移动构造, 使用atomic_init()函数实现其初始化)
	for (auto& c : in)
		std::atomic_init(&c, 0);
	for (auto& c : out)
		std::atomic_init(&c, 0);

	vector<thread> prod(producerWorks), cons(consumerWorks);

	for (int i = 0; i < producerWorks; i ++ )
		prod[i] = thread(produce, i);

	for (auto& t : cons)
		t = thread(consume);

	for (auto& t : prod)
		t.join();

	for (auto& t : cons)
		t.join();

	// 当生产者生产完毕后, 添加最后一个消费者, 该消费者保证能够将剩下的物品消费完毕
	lastConsume();

	// 消费完毕后, 进行正确性检查.
	check();

	std::chrono::time_point<std::chrono::system_clock> end_time = std::chrono::system_clock::now();
	std::chrono::milliseconds time = std::chrono::duration_cast<std::chrono::milliseconds>(end_time - start_time);
	cout << "\ncost time = " << time.count() << " ms\n";

	return 0;
}

```

3. **benchmark_lockfree.cpp**: 无锁队列性能测试

```cpp
//
// Created by Jsss on 2022/3/13.
//

#include <iostream>
#include <vector>
#include "LockFreeQueue.h"
#include "setting.h"
#include <thread>
#include <chrono>

using std::vector;
using std::thread;
using std::cout;

int all = producerWorks * producerNums;

// 记录生产者和消费者完成数目
atomic<int> prodFinish(0);

LockFreeQueue<int> qu;

void produce (int _id) {
	// 确定生产者生产编号的范围, 保证每个生产者生产的物品唯一
	int L = _id * producerNums, R = (_id + 1) * producerNums - 1;

	for (int u = L; u <= R; u ++ )
		qu.enqueue(u);

	// 生产完毕则, finish个数 + 1
	prodFinish.fetch_add(1);
}

void consume () {
	// 必须使用CAS操作判断, 如果prodFinish的值不为producerWorks的数目, 即生产者还在生产, 就继续尝试消费
	int cur;
//	while (prodFinish.load() < producerWorks) {
	while (prodFinish.compare_exchange_weak(producerWorks, producerWorks) == false) {
		// 取成功则对应位置的 out + 1
		while (qu.dequeue(cur))
			;
	}
}

void lastConsume () {
	int cur;
	while (qu.dequeue(cur))
		;
}

void epochRunLockFree() {
	vector<thread> prod(producerWorks), cons(consumerWorks);

	for (int i = 0; i < producerWorks; i ++ )
		prod[i] = thread(produce, i);

	for (auto& t : cons)
		t = thread(consume);

	for (auto& t : prod)
		t.join();

	for (auto& t : cons)
		t.join();

	// 当生产者生产完毕后, 添加最后一个消费者, 该消费者保证能够将剩下的物品消费完毕
	lastConsume();
}

int main() {

	std::chrono::time_point<std::chrono::system_clock> start_time = std::chrono::system_clock::now();

	for (int i = 0; i < epochs; i ++ )
		epochRunLockFree();

	std::chrono::time_point<std::chrono::system_clock> end_time = std::chrono::system_clock::now();
	std::chrono::milliseconds time = std::chrono::duration_cast<std::chrono::milliseconds>(end_time - start_time);

	cout << "\nepoch cost average time = " << time.count() / epochs << " ms\n";
	return 0;
}
```

## 测试结果

1. 正确性测试

```cpp
produce 1 finish.
produce 0 finish.
produce 3 finish.
produce 2 finish.

error in count = 0
error out count = 0

cost time = 1273 ms

Process finished with exit code 0
```

2. Valgrind内存检查

![img](https://cdn.jsdelivr.net/gh/CsJsss/CsJsss.github.io@hexo/themes/icarus/source/img/2022/3/无锁队列内存检查.png)

3. 无锁队列性能测试

```bash
epoch cost average time = 987 ms

Process finished with exit code 0
```

3. mutex队列性能测试

```bash
epoch cost average time = 378 ms

Process finished with exit code 0
```

## 分析总结

1. 使用无锁队列比`mutex`更慢的原因可能有2. 其一是无锁队列中使用了结构体, 并使用`new`和`delete`申请和释放, 频繁调用导致比`mutex`版本的`int`更慢. 其二是结构体使用了`atomic<Node*>`作为`next`指针. 如果使用普通的`Node*`并使用`__sync_bool_compare_and_swap()`可能会比现在更快.

2. 实验用的机器是自己的笔记本. cpu是5800H, 八核心十六线程. 使用设置`生产线程=2, 消费线程=2, 生产个数=2e6`的时候耗时`700ms`左右; 当设置`生产线程=4, 消费线程=4, 生产个数=1e6`的时候耗时`900ms`左右; 而设置`生产线程=8, 消费线程=8, 生产个数=5e5`的时候耗时在`1300ms`左右. 相同的生产个数, 随着核心数的增加, 耗时却在增加, 这可能是多核情况下必须缓存一致性协议(MESI)来保持多核缓存一致, 而且使用了`atomic`, 导致耗时增加.

3. 无锁队列可能适应于分布式场景下. 利用多设备的计算资源进行业务的分阶段处理.

## 参考

- [酷壳陈皓关于无锁队列的讲解博客](https://coolshell.cn/articles/8239.html)
- [Implementing Lock-Free Queues](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.53.8674&rep=rep1&type=pdf)
- [Simple, Fast, and Practical Non-Blocking and Blocking ConcurrentQueue Algorithms](https://www.cs.rochester.edu/u/scott/papers/1996_PODC_queues.pdf)

## TODO

1. 代码整理, 上传Github
2. 解决`ABA`问题


