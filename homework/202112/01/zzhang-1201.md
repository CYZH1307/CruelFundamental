# 如何解决mysql数据库锁表问题

Reference: https://weikeqin.com/2019/09/05/mysql-lock-table-solution/

1. 查看表是否在使用

   ```
   mysql> show open tables where in_use > 0;
   Empty set (0.00 sec)
   mysql > show open tables where in_use > 0;
   +----------+-------+--------+-------------+
   | Database | Table | In_use | Name_locked |
   +----------+-------+--------+-------------+
   | test     | t     |      1 |           0 |
   +----------+-------+--------+-------------+
   ```

   如果查询结果不为空，继续后续步骤

2. 查看数据库当前的进程，有无正在执行的慢SQL记录线程

   ```
   mysql> show processlist;
   ```

3. 当前运行的所有事务

   ```
   mysql> SELECT * FROM information_schema.INNODB_TRX;
   ```

   - 看事务表INNODB_TRX里面是否有正在锁定的事务线程，看看ID是否在show processlist里面的sleep线程中，如果是，就证明这个sleep的线程事务一直没有commit或者rollback而是卡住了，我们需要手动kill掉

4. 当前出现的锁

   ```
   mysql> SELECT * FROM information_schema.INNODB_LOCKS;
   ```

5. 锁等待的对应关系

   ```
   mysql> SELECT * FROM information_schema.INNODB_LOCK_waits;
   ```

6. 批量删除事务表中的事务

   - 过information_schema.processlist表中的连接信息生成需要处理掉的MySQL连接的语句临时文件，然后执行临时文件中生成的指令。

   ```sql
   SELECT concat('KILL ',id,';') 
   FROM information_schema.processlist p 
   INNER JOIN  information_schema.INNODB_TRX x 
   ON p.id=x.trx_mysql_thread_id 
   WHERE db='test';
   ```



---

MySQL 中的锁🔒：

- 设计的初衷是处理并发问题
- 当出现并发访问的时候，数据库需要合理地控制资源的访问规则

死锁问题：

- 不同线程出现循环资源依赖
- 解决方案：
  - 等待，直到超时。超时时间可以通过参数 `innodb_lock_wait_timeout` 来设置，默认50秒。
  - 发起死锁检测，发现死锁后，主动回滚死锁链条中的某一个事务，让其他事务得以继续执行。将参数 `innodb_deadlock_detect` 设置为 on，表示开启这个逻辑

