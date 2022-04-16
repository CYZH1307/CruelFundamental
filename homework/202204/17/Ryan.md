# 针对你熟悉的编程语言和版本，描述锁的实现
C++: 假定都能使用mutex

## 读写锁rwlock：

三个成员变量：mutex rlock; mutex wlock; 和 int readCnt;  
每次 加/解 读锁，先上rlock，readCnt++/--（若readCnt从0到1，则加wlock，若readCnt从1到0，则解wlock），再解rlock。
每次 加/解 写锁，则 加/解 wlock。

## 自旋锁spinlock

一个成员变量atomic_flag flag;
可支持两种原子操作：

    1.test_and_set： 如果atomic_flag对象被设置，则返回true; 如果atomic_flag对象未被设置，则设置之，返回false
    2.clear： 清除atomic_flag对象
    
当加自旋锁时，while循环flag.test_and_set()。解自旋锁时，flag.clear()。
