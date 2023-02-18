# mysql如何保证主从数据一致

使用`bin log`归档日志实现主从数据的一致性, 该日志是Server层日志, 可以适用于不同的底层存储引擎.

若主库发送故障, `bin log`未能发送会导致从库数据和主库出现不一致性, MySql使用`GTID`实现主从数据的一致性.
 
GTID复制实现的工作原理:
    1 主节点更新数据时，会在事务前产生GTID，一起记录到`bin log`日志中。
    2 从节点的I/O线程将变更的`bin log`, 写入到本地的`relay log`中。
    3 SQL线程从relay log中获取GTID，然后对比本地binlog是否有记录（所以MySQL从节点必须要开启binary log）。
    4 如果有记录，说明该GTID的事务已经执行，从节点会忽略。
    5 如果没有记录，从节点就会从relay log中执行该GTID的事务，并记录到bin log。
    6 在解析过程中会判断是否有主键，如果没有就用二级索引，如果有就用全部扫描。

[参考GTID博客](https://www.cnblogs.com/zejin2008/p/7705473.html)
