# bin log和redo log的区别



Bin Log:

- 只记录数据库的写入操作，不记录查询信息
- 逻辑日志，即记录SQL语句
- 通过追加的方式写入，可以通过 `max_binlog_size `参数设置每个 `binlog`
  文件的大小，当文件大小达到给定值之后，会生成新的文件来保存日志

- 用途：主从复制，leader把 bin log发给follower们

- 实现： Server 层实现的，所有引擎都可以使用 bin log

Redo Log：

- 记录数据页的物理变化
- 大小固定，循环写入的方式，当写到结尾时，会回到开头循环写日志

- 用途：保证durability，crash的时候能从redo log恢复未写入磁盘的操作

- 实现：InnoDB引擎层实现，并不是所有引擎都有

