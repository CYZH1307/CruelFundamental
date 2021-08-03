# Java ReentrantLock 原理与使用


可重入锁指的是如果当前线程已经获得一个临界区的锁，那么可以再次获得相同的锁，这样的操作不会造成死锁

ReentrantLock 还可以衍生出：

- ReentrantReadWriteLock.ReadLock //可重入读写锁 - 读锁
- ReentrantReadWriteLock.WriteLock //可重入读写锁 - 写锁

一般来说使用可重入锁可以用一下的代码块

```java
Lock l = new ReentrantLock(); 
     l.lock(); // 加锁
     try {
         // business logic
     } finally {
         l.unlock(); // 解锁
     }
```

值得注意的是，如果给当前代码加锁了的话，一定要记得解锁，不然会死锁。

这一点和 synchronized 关键字不一样。
