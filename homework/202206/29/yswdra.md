### 什么是CAS (compare and swap)操作? 它的作用是什么？它是如何在硬件上实现的？ 什么是ABA问题?

------

##### CAS 操作

即 compare and swap 操作，并且具有原子性
它是众多并发操作的基础

##### 硬件实现

```c++
inline jint Atomic::cmpxchg (jint exchange_value, volatile jint* dest, jint compare_value) {
    int mp = os::isMP();  // 判断处理器的类型是否是多处理器
    _asm {
        mov edx, dest
        mov ecx, exchange_value
        mov eax, compare_value
        LOCK_IF_MP(mp)
        cmpxchg dword ptr [edx], ecx
    }
}
```

多线程加锁，单线程则单条指令

##### ABA 问题

CAS 是当且仅当旧的预期值 E 和内存值 V 相同时，才将内存值 V 修改为 U，也就是如果内存值 V 没有发生变化则更新，但是有可能发生内存值原来是 A，中间被改成 B，后来又被改成 A，此时再使用 CAS 进行检查时发现没有变化，但是实际上发生了变化，这就是 ABA 问题。

参考 https://www.zhihu.com/question/425863643/answer/1527001400