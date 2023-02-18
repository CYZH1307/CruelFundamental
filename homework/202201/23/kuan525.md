#### 2022/1/23、binlog和redo log的区别

mysql三大日志：bin log、 redo log、 undo log

日志是 `mysql `数据库的重要组成部分，记录着数据库运行期间各种状态信息。

日志主要包括**错误日志**、**查询日志**、**慢查询日志**、**事务日志**、**二进制日志**几大类。

##### 一、redo log（事务日志）

　　 重做日志

`redo log `包括两部分：一个是内存中的日志缓冲( `redo log buffer `)，另一个是磁盘上的日志文件( ` redo logfile `)。 `mysql `每执行一条 `DML `语句，先将记录写入 `redo log buffer `，后续某个时间点再一次性将多个操作记录写到 `redo log file `。这种 **先写日志，再写磁盘** 的技术就是 `MySQL`里经常说到的 `WAL(Write-Ahead Logging) `技术。

　　作用：确保事务的持久性。防止在发生故障的时间点，尚有脏页未写入磁盘，在重启 mysql 服务的时候，根据 redo log 进行重做，从而达到事务的持久性这一特性。

　　内容：物理格式的日志，记录的是物理数据页面的修改的信息，其 redo log 是顺序写入 redo log file 的物理文件中去的。

##### 二、binlog（二进制日志）

　　归档日志（二进制日志）

`binlog `用于记录数据库执行的写入性操作(不包括查询)信息，以二进制的形式保存在磁盘中。 `binlog `是 `mysql`的逻辑日志，并且由 `Server `层进行记录，使用任何存储引擎的 `mysql `数据库都会记录 `binlog `日志。

`binlog `是通过追加的方式进行写入的，可以通过 `max_binlog_size `参数设置每个 `binlog`文件的大小，当文件大小达到给定值之后，会生成新的文件来保存日志。

　　作用：用于复制，在主从复制中，从库利用主库上的 binlog 进行重播，实现主从同步。 用于数据库的基于时间点的还原。

　　内容：逻辑格式的日志，可以简单认为就是执行过的事务中的 sql 语句。但又不完全是 sql 语句这么简单，而是包括了执行的 sql 语句（增删改）反向的信息，也就意味着 delete 对应着 delete 本身和其反向的 insert；update 对应着 update 执行前后的版本的信息；insert 对应着 delete 和 insert 本身的信息。

　　binlog 有三种模式：Statement（基于 SQL 语句的复制）、Row（基于行的复制） 以及 Mixed（混合模式）

