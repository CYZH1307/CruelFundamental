### 什么是共享锁，排他锁，意向锁？

- 排它锁： 事务对数据加上X锁，只允许钙食物读取和修改此数据，并且其他事物不能对该数据加任何锁
- 共享锁：加S锁后，该事物可以读取而不能修改，其它事务也可以加S锁但不能加X锁
- 意向锁：
    - 一个事务获得某个数据行对象的S锁之前，必须先获得整个表的IS锁或者更强的锁
    - 一个事物在获得某个数据行对象的X锁之前，必须先获得整个表的IX锁或者更强的锁
    - 意向锁的好处，一个事务如果想要对整个表加X\锁，需要先检测是否有其它事务对该表或某一行加了锁，非常耗时，有了意向锁，只需要检测是否存在意向锁即可
