  共享锁（Share Lock）
共享锁又称读锁，简称 S 锁：一个事务获取了一个数据行的共享锁，其他事务能获得该行对应的共享锁，但不能获得排他锁，即一个事务在读取一个数据行的时候，其他事务可以并发读取数据，但不能对该数据行进行增删改，直到已释放所有共享锁

如果事务 T 对数据 A 加上共享锁后，则其他事务只能对 A 再加共享锁，不能加排他锁。获取共享锁的事务只能读数据，不能修改数据

在查询语句后面增加LOCK IN SHARE MODE，MySQL 会对查询结果中的每行都加共享锁，当没有其他线程对查询结果集中的任何一行使用排他锁时，可以成功申请共享锁，否则会被阻塞。其他线程也可以读取使用了共享锁的表，而且这些线程读取的是同一个版本的数据

排他锁（eXclusive Lock）
排他锁又称写锁，简称 X 锁：一个事务获取了一个数据行的排他锁，其他事务就不能再获取该行的其他锁（排他锁或者共享锁），即一个事务在读取一个数据行的时候，其他事务不能对该数据行进行增删改查

如果事务 T 对数据 A 加上排他锁后，则其他事务不能再对 A 加任何类型的封锁。获取排他锁的事务既能读数据，又能修改数据

在查询语句后面增加FOR UPDATE，MySQL 会对查询结果中的每行都加排他锁，当没有其他线程对查询结果集中的任何一行使用排他锁时，可以成功申请排他锁，否则会被阻塞

意向锁
意向锁是表级锁，其设计目的主要是为了在一个事务中揭示下一行将要被请求锁的类型。InnoDB 中的两个表锁：

意向共享锁（IS）：表示事务准备给数据行加入共享锁，也就是说一个数据行加共享锁前必须先取得该表的 IS 锁。如果需要对记录 A 加共享锁，那么此时 InnoDB 会先找到这张表，对该表加意向共享锁之后，再对记录 A 添加共享锁

意向排他锁（IX）：类似上面，表示事务准备给数据行加入排他锁，也就是说事务在给一个数据行加排他锁前必须先取得该表的 IX 锁。如果需要对记录 A 加排他锁，那么此时 InnoDB 会先找到这张表，对该表加意向排他锁之后，再对记录 A 添加排他锁

意向锁是 InnoDB 自动加的，不需要用户干预
