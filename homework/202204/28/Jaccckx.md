3.数据库主从复制原理
主数据库有个bin log二进制文件，记录了所有增删改查SQL语句 （binlog线程）

从数据库把主数据库的bin log文件的sql语句复制到自己的中继日志relay log(io线程)

从数据库的relay log重做日志文件，再执行一次这些sql语句（sql执行线程）
