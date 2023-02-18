# 介绍一下MySQL中的各种日志

1、redo log 重做日志：确保事务持久性。记录事务启动后状态。

2、undo log 回滚日志：保存事务发生前状态，用于回滚。

3、binlog：主从复制中，从库用主库的binlog复制。

4、errorlog：记录mysql运行过程中相关错误

5、slow query log：记录时间过长或没有使用查询的语句

6、general log：记录服务器收到的每一个查询 正确错误均查询。

7、relay log 中继日志：和binlog差不多。relay log 从I/O线程将binlog记录读取到从服务器本地文件， 从库读取relaylog到从库服务器。

