### DROP、TRUNCATE 和 DELETE 命令有什么区别？
1、drop、truncate、delete它们的用法是不同的
drop(丢弃数据): drop table 表名 ，直接将表都删除掉，在删除表的时候使用。
truncate (清空数据) : truncate table 表名 ，只删除表中的数据，再插入数据的时候自增长id又从1开始，在清空表中数据的时候使用。
delete（删除数据） : delete from 表名 where 列名=值，删除某一列的数据，如果不加 where 子句和truncate table 表名作用类似。
truncate 和不带 where 子句的 delete、以及 drop 都会删除表内的数据，但是 truncate 和 delete 只删除数据不删除表的结构(定义)，执行drop语句，此表的结构也会删除，也就是执行 drop 之后对应的表不复存在。

2、它们属于不同的数据库语言
truncate和drop 属于DDL(数据定义语言)语句，操作立即生效，原数据不放到 rollback segment 中，不能回滚，操作不触发 trigger。而 delete 语句是DML (数据库操作语言)语句，这个操作会放到 rollback segement 中，事务提交之后才生效。

3、它们的执行速度不同
drop > truncate > delete
