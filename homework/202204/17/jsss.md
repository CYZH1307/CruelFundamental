# 针对你熟悉的编程语言和版本，描述锁的实现

## Mutex

Linux中`mutex`同步原语的源代码如下:

```cpp
struct mutex {
        atomic_t                count;          // 代表当前锁的状态, 如果为1, 说明没未上锁, 为0说明上锁, 为负数说明有waiter.
        spinlock_t              wait_lock;      // 保护waiter的自旋锁
        struct list_head        wait_list;      // waiter队列
#if defined(CONFIG_DEBUG_MUTEXES) || defined(CONFIG_MUTEX_SPIN_ON_OWNER)
        struct task_struct      *owner;         // 根据内核设置选择是否开启
#endif
#ifdef CONFIG_MUTEX_SPIN_ON_OWNER
        struct optimistic_spin_queue osq;       // 和 owner配合实现: 乐观自旋
#endif
#ifdef CONFIG_DEBUG_MUTEXES 
        void                    *magic;         // 用于DEBUG
#endif
#ifdef CONFIG_DEBUG_LOCK_ALLOC
        struct lockdep_map      dep_map;        // 用于DEBUG
#endif
};
```

当一个线程尝试获取一个`mutex`时, 会有三种可能的执行路径:

- fastpath
- midpath
- slowpath

当没有其他线程获得该锁时, `count`可以直接递减. 当互斥锁解锁的时候, 也是通用的运行流程. 所有的操作必须是`atomic`的.
当锁已经被其他线程获得时, 可能会执行`midpath`. 当锁拥有线程在执行时, 该控制流程会执行乐观自旋. 只有在没有其他具有更高优先级的进程准备运行时，才会执行此路径. 这个路径称为乐观路径，因为等待的任务不会被休眠和重新调度。这可以避免昂贵的**上下文切换**.
当`fastpath`和`midpath`不执行时，将执行`slowpath`. 这条执行路径类似于**信号量**。如果进程无法获得锁，则该进程将被添加到如下所示的等待队列中

```cpp
/*
 * This is the control structure for tasks blocked on mutex,
 * which resides on the blocked task's kernel stack:
 */
struct mutex_waiter {
	struct list_head	list;
	struct task_struct	*task;
	struct ww_acquire_ctx	*ww_ctx;
#ifdef CONFIG_DEBUG_MUTEXES
	void			*magic;
#endif
};
```

### Mutex API

#### 静态初始化. 

`DEFINE_MUTEX`宏以互斥锁的名称为参数, 并定义`struct muext`互斥锁.

```cpp
#define DEFINE_MUTEX(mutexname) \
        struct mutex mutexname = __MUTEX_INITIALIZER(mutexname)
```

count字段被初始化为1，表示互斥锁为`unlocked`状态。wait_lock自旋锁被初始化为未锁定状态，最后一个字段wait_list被初始化为空的双向链表。

```cpp
#define __MUTEX_INITIALIZER(lockname)         \
{                                                             \
       .count = ATOMIC_INIT(1),                               \
       .wait_lock = __SPIN_LOCK_UNLOCKED(lockname.wait_lock), \
       .wait_list = LIST_HEAD_INIT(lockname.wait_list)        \
}
```

#### 动态初始化. 

```cpp
# define mutex_init(mutex)                \
do {                                                    \
        static struct lock_class_key __key;             \
                                                        \
        __mutex_init((mutex), #mutex, &__key);          \
} while (0)
```

```cpp
void
__mutex_init(struct mutex *lock, const char *name, struct lock_class_key *key)
{
        atomic_set(&lock->count, 1);
        spin_lock_init(&lock->wait_lock);
        INIT_LIST_HEAD(&lock->wait_list);
        mutex_clear_owner(lock);
#ifdef CONFIG_MUTEX_SPIN_ON_OWNER
        osq_lock_init(&lock->osq);
#endif
        debug_mutex_init(lock, name, key);
}
```
