# mutex的实现和局限性

`mutex`也可以称为锁, 是多线程编程下进程线程间互斥和同步的主要方式之一.

## 特点

- 不同于信号量，mutex需要谁拿的锁谁来释放锁。
- 不同于自旋锁，mutex临界区允许睡眠。
- 不同于自旋锁，mutex在拿锁时若锁被别人持有，会根据锁的持有者是否正在运行来决定是乐观自旋或是睡眠等待。

## 实现

```cpp
struct mutex {
        atomic_long_t    owner;  //owner中记录了锁的持有者的task_struct地址，且低3bit记录了锁的状态
        spinlock_t    wait_lock; //用来保护wait_list链表
        struct list_head   wait_list; //等待链表，等着拿锁的进程会被记录在此list上，操作wait_list需要wait_lock的保护
        .....
};
```

互斥锁`mutex`的实现思路基本都是设置一个标志位, 表示锁是否被持有以及被谁持有. 重要的对于该标志位的"判断并且置位"的操作需要是原子的. 一般通过操作系统的指令"test and set"来保证该操作的原子性.

当线程B没有获取到锁时, 如果发现拥有锁的线程A正在执行, 则线程B可以乐观自旋. 因为通常认为A线程正在执行的情况下锁很快会被释放, 而这种情况下阻塞等待再被唤醒的代价一般是大于自选等待的. 如果发现线程A不在运行态, 则线程B进入阻塞态.

## 局限性

- 性能问题
- 若持有锁的线程没正常释放锁, 则可能造成死锁.


## 参考

[浅析mutex实现原理](https://zhuanlan.zhihu.com/p/390107537)