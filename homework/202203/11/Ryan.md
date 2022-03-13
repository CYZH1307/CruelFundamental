# C++: Lock-free queue 的实现。lock-free和lock-based的区别
Lock-free queue一般使用CAS原子操作实现，取代了一般队列中插入/删除操作的“链接→替换”过程。以删除（出队）为例，一般队列若要在队首删除并返回节点A，则必经以下几步：“A = head; head = head->next; A->next = null; return A;”。
这样显然是线程不安全的。而CAS逻辑如下：
```c++
bool CompareAndSwap(T* addr, T& expected, const T& desired){
    if(*addr == expected){
        *addr = desired;
        return true;
    }
    expected = *addr;
    return false;
}
```
使用CAS(head, A, A->next)便可一步实现出队，线程安全。

### lock-free和lock-based的区别
lock-free是采用了CAS等原子操作，其本质还是对变量在更小粒度上了锁，不过这个锁是硬件层面的，开销较小，lock-free让出队和入队操作中节点的相应赋值在原子时间内完成，线程安全。lock-based则对整个队列上锁。
