# MySQL 如何实现事务隔离

所谓的事务隔离是指并发的事务之间彼此不可见，互不影响。
一致性和事务性之间是要取舍的。

数据库有四种事务隔离级别：
* RAED UNCOMMITED 可能会脏读，读到为提交的行
* READ COMMITED 事务内多次查询结果可能不同
* REPEATABLE READ 第一次读有快照。没有解决幻读问题。默认配置。
* SERIALIZABLE 

具体实现方式就是通过加锁。共享锁｜独占锁。
MVCC。