Q: MVCC
A:
Multiple Version Concurrency Control, 是一种方法, 数据库实现并发访问,编程语言实现事务内存
已提交和可重复读都基于MVCC实现,相对于锁更高效处理读写冲突
MVCC实现:
a) 事务版本号: 每次事务开始前,获取自增长标号
b) 隐式字段: 1) trx\_id: 事务id  2) roll\_pointer:指向undo日志的回滚指针 3) row\_id:非必须,没有主键时自增长
c) undo log: 1) 保证回滚原子性和一致性 2) 用于快照读
d) 版本链
e) 快照读和当前读
f) read view
