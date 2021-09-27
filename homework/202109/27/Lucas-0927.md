# 两阶段锁
- https://zhuanlan.zhihu.com/p/59535337
- https://en.wikipedia.org/wiki/Two-phase_locking
- https://segmentfault.com/a/1190000038163191
- https://www.cnblogs.com/zszmhd/p/3365220.html
- https://www.jianshu.com/p/d1c42fe4c4ed

## 需求
- 保证串行化，用来控制并发的事务
- 扩张阶段，不上上锁，从不释放
- 收缩阶段，陆续释放，从不加锁
- 用来实现隔离性和一致性，可能导致死锁
- 区别于多版本控制 MVCC

## 一次性锁
- 事务开始前，一次性申请所有的锁
- 事务结束后，一次性释放所有的锁
- 不会产生死锁问题
- 但是事务并发度不高

## 两阶段锁
- 事务分成两阶段，加锁阶段，和解锁阶段
- 并发度高，没有解决死锁问题

## 示例
- TODO
