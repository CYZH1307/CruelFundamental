事务的ACID特性即事务的原子性、一致性、隔离性和持久性。

- 原子性: 事务是不可再分的, 是数据库执行的最小单位. 
- 一致性: 事务的执行前后必须保证数据库处于一致性状态. 
- 隔离性: 并行事务之间互不影响.
- 持久性: 事务对于数据库的修改是持久性的, 不因断电等原因而消失.

并发一致性问题: 脏读、不可重复读和幻读.

- 脏读: 读取了其他事务未提交的数据, 而那个事务将该数据回滚, 即读到了脏数据. 
- 不可重复读：同一个事务中, 两次查询得到的结果不一致.(针对update) 
- 幻读：同一个事务中, 两次查询得到的结果不一致.(针对insert/delete)
