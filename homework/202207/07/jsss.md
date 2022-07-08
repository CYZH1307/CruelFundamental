# 为什么事务提交了但是数据没有保存

为了保证事务的ACID特性, 事务执行需要先写日志(WAL). 即Redo Log. 这是一种追加模式的日志, 即只需顺序写. 

为了提升写的性能, 常采用**批量写**优化策略. 即每隔一定时间间隔后才刷盘. 

写日志的过程一般可以分为三个阶段: 

1. 内存缓冲区: LogBuffer. 在内存中的日志数据
2. 内核缓冲区: OsCache. 通过write系统调用将内存日志写到内核缓冲区中.
3. 文件：LogFile. 通过fsync将内核缓冲区的数据刷盘到文件中.

因此认为写入LogBuffer就是写日志成功, 那么事务提交后系统崩溃导致该日志没落盘以及数据还未更新. 那么就会出现事务提交了但是数据没有保存的情况出现. 同理于写日志的OsCache阶段.

MySQL中参数`innodb_flush_log_at_trx_commit`可以控制Redo Log刷盘策略.

1. 最佳性能: 每隔一秒才将LogBuffer中的数据刷新到OsCache中.

2. 强一致: 直接fsync到磁盘上后才算提交成功日志.

3. 折衷: 直接写入OsCache, 每隔一秒后fsync到磁盘.

对于高并发业务, 推荐**强一致**.


参考链接: [Link](https://blog.csdn.net/shenjian58/article/details/124030259)
