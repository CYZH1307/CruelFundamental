## mutex的实现和局限性

### mutex的实现

C++中，标准库的std::mutex是pthread_mutex_t的一个封装

```
class mutex {
    pthread_mutex_t _M_mutex;
public:
    mutex() { _M_mutex = PTHREAD_MUTEX_INITIALIZER; }
    ~mutex() { pthread_mutex_destroy(&_M_mutex); }
    void lock() { pthread_mutex_lock(&_M_mutex); }
    bool try_lock() { return pthread_mutex_trylock(&_M_mutex) == 0; }
    void unlock() { pthread_mutex_unlock(&_M_mutex); }
}
```

锁的本质是一块内存空间，这块空间被赋值为1的时候表示加锁，赋值为0时表示解锁。多线程抢占锁资源实际上是抢占这块内存的赋值权。保证一次只有有一个线程成功抢占到锁，实际上需要硬件上的设计保证。

CPU提供一些用来构建锁的atomic指令，完成atomic的compare-and-swap（CAS），用这样的硬件指令可以实现spin lock(自旋锁)

### mutex的局限性

