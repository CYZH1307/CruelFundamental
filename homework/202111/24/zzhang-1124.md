#  MySQL 隔离级别

Reference: https://developer.aliyun.com/article/743691



### 隔离级别

共有4个 （按隔离级别从低到高）

(1) 读未提交 READ UNCOMMITTED：可能脏读、不可重复读、幻读

(2) 读已提交 READ COMMITTED：可能不可重复读、幻读

(3) 可重复读 REPEATABLE READ：可能幻读

- Session B 只能在 Session A 修改过数据并Commit后，自己也commit后，才能读Session A修改的数据。
- Followup: 为什么上了写锁，别的Session还可以执行读操作？
  - 因为InnoDB有MVCC机制（多版本并发控制），可以使用快照读，而不会被阻塞

(4) 可串行化 SERIALIZABLE

- 读读操作不阻塞
- 读写操作阻塞：Session A写完未Commit，Session B此时读会被阻塞，等A Commit完了才读。
- 写读操作阻塞：Session A 读完未Commit，Session B此时写会被阻塞，等A Commit完才写。
- 写写操作阻塞：Session A写完未Commit，Session B此时写会被阻塞，等A Commit完了才写。



### 隔离级别的实现

以  可重复读 REPEATABLE READ 为例：

1. 每条记录在更新时同时记录一条回滚操作日志（undo log）。可通过回滚，回到前一个状态的值
   - 例子：若一个值从1依次变为2、3、4，它的记录是
     -  $$\{[2\to1]\gets[3\to2]\gets[4\to3]\}\gets 4$$ 
     - { } 内的为回滚段。
2. 查询上述例子中的记录时，不同时刻启动的事务会有不同的read-view去读值 1, 2, 4。若要得到 $$1$$ 这个记录，就必须将当前值一次执行图中的所有回滚操作得到。
3. 如果有另外一个事务正在将4改成5，则这个事务跟read-view  去读值1, 2, 4 不冲突。

其他问题：

- Q: 回滚操作日志什么时候删除？A: MySQL会判断当没有事务需要用到这些回滚日志的时候，回滚日志会被删除。
- Q: 什么时候认为没有事务会需要用到这些回滚日志？A: 当系统里没有比这个回滚日志更早的read-view的时候



### 隔离级别相关命令

1. 查看当前Session的隔离界别：

```sql
/* Method 1*/
SHOW VARIABLES LIKE 'transaction_isolation';

/* Method 2*/
SELECT @@transaction_isolation;
```

2. 设置隔离级别

   - 方法1:

   ```sql
   SET [GLOBAL|SESSION] TRANSACTION ISOLATION LEVEL level;
   ```

   关键词 Global：只对执行完该语句之后产生的会话起作用，当前已经存在的会话无效。

   关键词 SESSION：对当前会话的所有后续的事务有效；该语句可以在已经开启的事务中间执行，但不会影响当前正在执行的事务；如果在事务之间执行，则对后续的事务有效。

   无关键词：只对当前会话中下一个即将开启的事务有效；下一个事务执行完后，后续事务将恢复到之前的隔离级别；该语句不能在已经开启的事务中间执行，会报错。

   *level*: `REPEATABLE READ`,`READ COMMITTED`, `READ UNCOMMITTED`, `SERIALIZABLE`

   

   - 方法2: 修改启动参数transaction-isolation的值：`--transaction-isolation=READ UNCOMMITTED`



---

### 事务并发可能出现的情况

1. **脏读 Dirty Read** [只在 (1) 级别出现]

   Session A 读了Session B 刚修改还没commit的数据（若此时Session B rollback，则Session A读到的数据事实上不存在）。

2. **不可重复读 Non-Repeatable Read** [在 (1) (2) 级别出现]

   Session A开启一个事务，Session B 修改了数据并commit了，Session A读到了新数据，Session B又修改了数据并Commit了，Session A读到了新的数据。

3. **幻读 Phantom** [在(1) (2) (3) 级别出现]

   Session A 开启一个事务，查询 id > 0 的记录，查到列表A，Session B 新插入一条 id > 0的记录，Session  A再查询 id > 0 的记录，会得到新的列表A‘。





