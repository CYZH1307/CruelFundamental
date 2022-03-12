# C++: Lock-free queue 的实现。lock-free和lock-based的区别

## Lock Free Queue

**无锁队列**是指对多线程对于队列的操作无需添加粒度较大的`mutex`, 而实现多线程下的同步. 我认为, 其主要思想是: 使用`mutex`的锁的代价太高, 那就使用更小粒度的锁, 甚至这个锁的粒度是一条赋值指令. 

### CAS操作

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

### 逻辑实现

可以通过链表或数组实现. 数组实现的话由于数组大小需要指定而队列长度不一定知道, 因此需要实现循环队列.

#### 基于链表的实现

用链表实现队列的时候, 为了避免麻烦的边界判断, 我们可以添加`头结点`. 初始化时申请头结点, 并且`head`指针和`tail`指针均指向该结点.


1. **入队操作**

    设入队新建结点的指针为`p`, 则`p -> next = nullptr`, 入队操作需要两步实现. **(1).** tail -> next = p **(2).** tail = p

    为了保证第一步的并发正确性. 我们持续不断获取`tail`的快照, 记为`cur`. 直到`CAS(cur -> next, nullptr, p)`. 即如果`cur -> next = nullptr`成立, 则当前`cur`一定为正确的`tail`. 接着我们将`cur -> next`更新成`p`. 这样是正确的. 因为当某个线程执行完这一步后. `tail`指针还未更新成`p`, 这样其他所有线程都在轮询等待`tail`指针的`next`为空, 无法入队.

    当轮询完成第一步后, 我们退出循环. 然后执行`CAS(tail, cur, p)`. 即将尾指针置为新入队结点.

    以上操作的流程会出现一个问题. 因为只有入队的线程才能将`tail`指针更新, 因此如果该线程入队后还未更新`tail`就结束了. 那么其他所有入队线程都将死锁. 因为他们`CAS(cur -> next, nullptr, p)`时, `cur -> next`一定不为空, 此时可能出现`cur -> next = p`.

    为了解决上述可能存在的问题. 我们主要**解决思路**是让其他线程也可以移动`tail`指针. 

    一个简单的方法是, 如果当前线程`CAS(cur -> next, nullptr, p)`入队失败了, 那么说明当前`cur`不是真正的`tail`, 那么我们尝试`CAS(tail, cur, cur -> next)`. 即尝试让`tail`往后走. 然后再获取`tail`的快照, 按照之前的流程轮询.

    较为复杂的方法是, 获取`tail`的快照`cur`, 并记录`cur -> next`为`next`. 接着判断`cur`是否为`tail`. 如果不为就重新开始. 然后判断`next`是否为空. 如果不为空, 我们就尝试更新`tail`, 更新的语句是`CAS(tail, cur, next)`. 如果为空. 我们就尝试`CAS(tail -> next, next, p)`入队(此时`next = nullptr`).


2. **出队操作**

出队操作类似于入队操作的思路. 我们首先获取`head`的快照`cur`. 然后判断`cur -> next`是否为空, 如果为空说明此时队列为空, 返回队列为空的信号. 如果不为空, 我们`CAS(head, cur, cur -> next)`来重置`head`的指针. 然后返回`cur -> next -> val`, 因为此时`cur`指向之前的头结点, `cur -> next -> val`为出队前的队头. 


### 代码实现和测试

**TODO**


