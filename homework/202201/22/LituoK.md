#### 介绍一下MySQL中的各种日志
* redo log: 记录对磁盘页的修改操作, 用于恢复系统崩溃时还没来得及刷新到磁盘当中的脏页
* undo log: 记录数据行的各个版本, 用于实现MVCC和事务回滚
* bin log: 记录对数据行的修改操作, 用于数据库快照和主从复制
* 慢查询日志: 记录查询时间超过阈值的查询语句
* 查询日志: 记录数据库执行过的所有查询
* 中继日志: 用于从服务器记录接收到的记录
* 错误日志: 记录MySQL运行过程中发生的错误