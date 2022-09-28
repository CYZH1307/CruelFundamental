### DROP、TRUNCATE 和 DELETE 命令有什么区别？

#### DROP

drop table

1.drop 是DDL，可以隐式提交。不能回滚，不会触发触发器。

2.删除表结构和所有数据，表的所有空间都被释放。

3.drop将删除表结构依赖的约束、触发器、索引，依赖于该表的存储过程/函数将保留，但是变为invalid状态。

#### TRUNCATE

truncate table

1.truncate是DDL，会隐式提交，所以不能回滚，不会触发触发器。

2.truncate会删除表中所有记录，并且将重新设置高水线，和所有索引。缺省情况下将会将空间释放到minextens个extent，除非使用reuse storage，不会记录日志，所以执行速度很快。但是不能通过rollback撤销操作。

3.对于外键（foreignkey）约束引用的表，不能使用truncate table，而应该使用不带where子句的delete语句。

4.truncatetable不能用于参与了索引视图的表。

#### DELETE

delete from 表名。

1.delete是DML，执行delete操作的时候，每次会从表中删除一行，并且同时将该行的删除操作记录在redo和undo log空间中以便进行回滚和重做操作。但是要注意表空间，要足够大，需要手动提交（commit）操作才能生效，可以通过rollback撤销操作。

2.delete可以根据条件删除表中满足条件的数据，如果不指定where子句，那么删除表中的所有数据。

3.delete语句不影响表所占用的extent，高水位保持原位置不变。



ref:https://www.cnblogs.com/feifuzeng/p/13625991.html