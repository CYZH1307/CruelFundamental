### 什么是事务的ACID特性？ 脏读，不可重复读，幻读的区别是什么？

参考：https://zhuanlan.zhihu.com/p/69380112

##### 原子性：

一个事务中的操作要么全部成功要么全部失败。

##### 一致性：

保证事务前后数据的状态都合法

##### 隔离性：

等价于多线程里的原子性

##### 持久性：

保证数据不丢失




##### 脏读：

一个事务能看到另一个事务未提交的数据


##### 不可重复读和幻读：

不可重复读指的应该是一个事务修改并提交了一个写入导致另一个事务前后读不一致(同一个数据)，幻读指的是即一个事务的写入改变了另一个事务的查询结果的正确性(不一定是同一个数据),对于前者只需要某一记录加锁，后者则可以全局加锁解决。

幻读例子：

公司决定施行007班制，要你写一个轮班管理程序，需求是公司里可以同时有多位员工值班，但至少有一位员工在值班。员工可以申请不值班（例如，进了ICU），只要至少有一个同事正在值班，申请即可通过。

现在想象一下，小明和小红是两位值班员工。两人都感到不适，所以他们都决定请假，他们恰好在同一时间点击按钮下班。

这是两个事务，应用首先检查是否有两个或以上的员工正在值班。由于数据库使用快照隔离，两个事务的读取都返回当前值班员工数量为2 ，所以小明成功更新自己的记录休班了，而小红也做了一样的事情。两个事务都成功提交了，现在没有人值班了。
