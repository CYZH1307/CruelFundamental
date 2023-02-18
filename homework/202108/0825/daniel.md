## SpinLock

多线程环境下,  当一个线程拿到了 spinlock, 其他线程如果尝试去获得锁失败后,不会去休眠,  而是重复尝试, 直到拿到锁

## 优劣

- spinlock 如果被持有的时间比较长,  其他线程一直 busy retry, 很容易造成 CPU飙升, 浪费资源
- 但是在一些网络应用中, 锁持有时间比较短, 这个时候如果不用 spinlock, 反而会让线程在内核和用户态间切换, 造成不必要的时间消耗

## 实现

使用 CAS 指令, 将获取锁状态和设置锁状态组成一个原子操作, 即可避免多个线程同时拿到锁的冲突.

* std::atomic_flag::test_and_set和std::atomic::compare_exchange_strong
* __atomic_test_and_set和__atomic_compare_exchange_n
