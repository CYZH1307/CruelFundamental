## lock-free queue的实现，lock-free 和 lock-base的区别？

为避免多线程资源冲突，lock-free queue通常采用CAS原子操作实现无锁，CAS用于检查一个内存位置是否包含预期值，如果包含，则把新值复制到内存位置，成功则返回true失败返回false

lock-free和lock-base的区别

本质上是锁的粒度的区别，lock-free直接采用CAS原子操作实现无锁，lock-base采用mutex等，根据之前的学习我们知道其底层同样与CAS操作有关。相对直接使用CAS原子操作的lock-free queue 线程互斥锁的开销比较大，无锁队列更适用于高性能，低延时的场景。
