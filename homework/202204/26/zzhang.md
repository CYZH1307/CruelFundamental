数据库主从复制原理
主从复制原理，简言之，分三步曲进行：

主数据库有个bin log二进制文件，记录了所有非查询SQL语句。（binlog线程）
从数据库把主数据库的bin log文件的SQL 语句复制到自己的中继日志 relay log（io线程）
从数据库的relay log重做日志文件，再执行一次这些sql语句。（Sql执行线程）
主从复制过程分了五个步骤进行：

主库的更新SQL(update、insert、delete)被写到binlog
从库发起连接，连接到主库。
此时主库创建一个binlog dump thread，把bin log的内容发送到从库。
从库启动之后，创建一个I/O线程，读取主库传过来的bin log内容并写入到relay log
从库还会创建一个SQL线程，从relay log里面读取内容，从ExecMasterLog_Pos位置开始执行读取到的更新事件，将更新内容写入到slave的db
