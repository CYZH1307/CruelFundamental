## binlog redolog区别？
1. binlog是Server层面的, redolog是存储引擎层面的
2. binlog是逻辑日志, redolog是物理日志
3. binlog在事务提交的时候写入磁盘, redolog会不断地写入磁盘
