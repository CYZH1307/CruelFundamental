# Java 自旋锁 SpinLock
- https://tech.meituan.com/2018/11/15/java-lock.html
- https://learnku.com/articles/49689
- https://www.jianshu.com/p/824b2e4f1eed
- https://blog.csdn.net/fuyuwei2015/article/details/83387536
- https://juejin.cn/post/6844903838349000717

## 原因
- 锁定资源的时间是极短的
- TODO: 在循环中，通过CAS自旋
- AtomicReference::compareAndSet(V expect, V update) 成功返回 True
- 后面的线程不挂起，而是不断盯着前面的线程是否已经释放锁
- 不满足CAS，进入循环
- 如果前面线程已释放，则后面线程满足CAS，退出循序，进而获得锁

## 缺点
- 循环浪费资源
- 默认不公平，有可能出现线程饥饿问题
- 为了解决可重入锁，可增加计数器

## 有点
- 不会发生线程切换，程序一直处于用户态，也就是 Active
- 线程不会进入阻塞状态，减少上下文切换，执行速度快

## 使用
- JDK 1.4.2 后引入自旋锁，用参数 -XX:+UseSpinning 来开启
- JDK 1.6 以后默认打开
- JDK 中，自旋锁默认操作10次，通过参数 -XX:PreBlockSpin 来设置
- JDK 1.6后引入自适应自旋锁
