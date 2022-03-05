# Mutex的实现和局限性

mutex结构如下：

'''
struct mutex {
        atomic_long_t    owner;  //owner中记录了锁的持有者的task_struct地址，且低3bit记录了锁的状态
        spinlock_t    wait_lock; //用来保护wait_list链表
        struct list_head   wait_list; //等待链表，等着拿锁的进程会被记录在此list上，操作wait_list需要wait_lock的保护
        .....
};
'''
