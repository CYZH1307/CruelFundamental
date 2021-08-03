# Java ReentrantLock 原理与使用
- https://zhuanlan.zhihu.com/p/82992473
- https://juejin.cn/post/6844903805683761165
- https://crossoverjie.top/2018/01/25/ReentrantLock/

## 简介
- Java 1.5 引入，显示锁，区别于关键字 synchronized 隐式锁
- 基于 AQS，AbstractQueuedSynchronizer，抽象队列同步器
- AQS 有两个队列，同步队列和条件队列
- 同步队列是一个双向链表，存储的是等待获取锁的线程
- 条件队列是一个单项链表，存储的是等待进入同步队列的线程

## ReentrantLock 原理
- 重写 tryAcquire 和 tryRelease，重入锁，不会阻塞自己
- 公平锁，线程依次获取锁
- 非公平锁，多个线程抢锁
- 同一线程，如果被自己锁住，state 加一，被别的锁住，阻塞
