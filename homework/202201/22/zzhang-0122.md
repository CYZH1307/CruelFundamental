介绍一下MySQL中的各种日志

redo log 重做日志, undo log 回滚日志, bin log 二进制日志, relay log 中继日志 error log 错误日志, slow query log 慢查询日志, general log 通用日志（查询日志）, 



0. error log
   - 默认开启且无法被禁止
   - 记录：服务器启动和关闭过程中的信息，服务器运行过程中的错误信息，时间调度器运行一个事件时产生的信息，在follower上启动follower进程时产生的信息等。

1. redo log
   - for *durability*: 防止crash的时候，有脏页未写入磁盘，重启的时候，按照re do log 重做
2. undo log
   - 保存了transaction 开始前的数据版本，用于回滚，同时提供多snapshot来support MVCC （多版本并发控制）
3. bin log
   - 主从复制的时候，follower用leader的bin log进行重播，实现主从同步
4. relay log
   - 复制过程中产生的日志，很多方面都跟binary log差不多，区别是: relay log是followerI/O线程将leader的binlog读取过来记录到follower的本地文件，然后follower的SQL线程会读取relay-log的内容并应用到follower上

5. general log
   - 记录了数据库执行的所有命令，不管语句是否正确，都会被记录
   - 有助于帮助我们分析哪些语句执行密集，执行密集的select语句对应的数据是否能够被缓存

6. slow query log
   - 让MySQL记录下查询超过指定时间的语句，之后运维人员通过定位分析



