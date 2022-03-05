# Mutex的实现和局限性

mutex结构如下：

```c++
struct mutex {
        atomic_long_t    owner;  //owner中记录了锁的持有者的task_struct地址，且低3bit记录了锁的状态
        spinlock_t    wait_lock; //用来保护wait_list链表
        struct list_head   wait_list; //等待链表，等着拿锁的进程会被记录在此list上，操作wait_list需要wait_lock的保护
        .....
};
```
ower的3~63位记录了锁持有者的地址，0~2位记录锁的状态，其中0位表示等锁的wait_list是否为空，1~2位实现防止抢锁的handoff机制。  

局限性：
    1.不支持多线程。
    2.线程陷入睡眠状态wait_list之后会进入内核态，唤醒需要开销昂贵的系统调用。
    3.线程饥饿。
