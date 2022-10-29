幻读和脏读的区别
脏读(针对未提交的数据)：一个事务在更新一条记录，未提交前，第二个事务读到了第一个事务更新后的记录，那么第二个事务就读到了脏数据，会产生对第一个未提交 数据的依赖。一旦第一个事务回滚，那么第二个事务读到的数据，将是错误的脏数据。

幻读(读取结果集条数的对比)：一个事务按相同的查询条件查询之前检索过的数据，确发现检索出来的结果集条数变多或者减少(由其他事务插入、删除的)，类似产生幻觉。

幻读怎么避免的
MVCC加上间隙锁的方式 （1）在快照读情况下，mysql通过mvcc来避免幻读。 （2）在当前读情况下，mysql通过next-key来避免幻读。锁住某个条件下的数据不能更改