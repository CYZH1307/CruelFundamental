## MySQL是怎么保证主从一致的

MySQL主从复制原理直白一点来说，就是master写入数据时会留下写入日志，slave根据master留下的日志模仿其数据执行过程进行数据写入。

主从一致性的原理
以 A:M-B:S 结构为例子：

A 的更新流程
A在接受一个来自客户端的更新请求之后，首先在undolog 内存中写入，然后存入硬盘，在redolog恢复日志 prepare 阶段完成之后，写入 binlog ，最后再 commit 整个 prepare ，完成 A这边的一套完整的执行内部事务的更新逻辑。
B的同步流程
B 和 A 之间维持了一个长链接，在B上，我们会设置A的账号信息，以及日志位置和偏移量，同步时，我们会主动执行一次 start slave 命令，启动 io_thread 和 sql_thread 两个线程 。io_thread 与A建立请求，主库 A 接受请求，然后检验，把A的binlog 发送给B 。B终于拿到A 的binlog ，写到本地日志，relay log 。 sql_thread 处理B上的 relay log ，解析binlog。
`binlog` 的格式分为3种，一种是 `statement` ，一种是 `row` ，一种是 `mixed` 。

以一句简单的 delete 语句来说：

statement 格式的 binlog 会记录 原来的delete 语句
row 格式会记录 具体删除的行的id 和其他信息
mixed 格式 会在判断之后，选择性的记录上面两种
比如 delete limit1 ，有多个where 条件，那么就会产生选择索引导致的行选择问题，这种删除语句，mixed 情况就会记录成 row 格式的 binlog ，如果是简单的不会产生歧义的 sql语句，就会记录成 statement 格式的binlog 。

row 能够保证主从完全一致，但是性能较低，mixed 存在一定的风险，需要配合 read-commited 隔离级别。


binlog 写入机制
当一个事务执行，先把日志写到 binlog cache ，事务提交的时候，其实是redo log prepare/commit 的时候，再把 binlog cache 写到 binlog 文件中，清空 binlog cache。
如果当 binlog 的 size 大于 binlog_cache_size 的时候，就要从内存中写入到磁盘上。
从内存binlog cache 写入磁盘分为两个动作， 即binlog 写入 page cache 还是 持久化到磁盘分别在 write 和 fsync 先后进行。控制这两个动作的发生的是一个叫 sync_binlog 的参数，

如果参数为 0，那么只write ；
如果参数为 1 ，那么只 fsync ；
如果参数为 N(N>1) 那么，write到了N才会 fsync ；
一般这个值被设置为 100-1000 ，既可以较快的处理，减少IO次数，也可以一定程度的防止实际业务中丢失数据的可能性（除非主机掉电）。

redo log 写入机制
redo log 同样是有三种存储状态和存储媒介：

redo log buffer ：Mysql 进程内存
写磁盘 write： 文件系统- page cache
持久化磁盘 fsync ： 硬盘
控制参数 innodb_flush_log_at_trx_commit ：

0 : 事务提交，写入 bnlog 的时机，redo log 是留在 redo log buffer 中；
1 :事务提交，redo log 持久化到磁盘
2 : 事务提交，redo log 写入 page cache
Redo log 持久化到磁盘的几个场景：

InnoDb 引擎每秒会把 redo log buffer 的日志，写到文件系统的page cache ，再 fsync
redo log size >= redo_log_buffer_size ，会写盘 page cache
innodb_flush_log_at_trx_commit 1 会并行的把redo log fsync
双1 ：sync_binlog 和 innodb_flush_log_at_trx_commit 都设置为1 。通过上面的介绍，我们 已经知道，在一个事务提交之前，会进行两次刷盘，一次刷盘（fsync）是binlog ，一次是 redo log 的 prepare 阶段

主从延迟
主备机器性能不一致，往往备库会比主库机器配置差
Solve： 对称部署

备库上随意的无压力控制的操作，影响同步操作
Solve：一主多从、统计类查询交给Hadoop 、Elastic等系统处理，从库更多解决的是A高可用，高并发的问题，并不适合在Mysql底层完全解决。

大事务运行，从 主从一致性的原理主从同步流程看到，一个事务在主库执行完成才会写入binlog ，传递给Slave ，Slave写入 relay log，最后写入从库，如果这个事务有10分钟，从库至少延迟10分钟
解决：

- 可以把Slave 的 双1 关闭，`innodb_flush_log_at_trx_commit` 和 `sync_binlog` ，这样binlog 不会 fsync
- 第二种思路就是减少从库查询负载，有两种办法，0：增加Slave服务器 1:Slavel只作为备份