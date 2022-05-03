# MySQL是怎么保证主从一致的

设A为主，B为从的一个实例，过程如下：

### A 的更新流程
A在接受一个来自客户端的更新请求之后，首先写入内存中的undolog，然后存入硬盘，在redolog恢复日志prepare阶段完成之后，写入binlog，最后再commit整个prepare，完成 A这边的一套完整的执行内部事务的更新逻辑。


### B的同步流程
B和A之间维持了一个长链接，在B上，我们会设置A的账号信息，以及日志位置和偏移量，同步时，我们会主动执行一次 start slave 命令，启动 io_thread 和 sql_thread 两个线程 。io_thread与A建立请求，主库 A 接受请求，然后检验，把A的binlog 发送给B。B拿到A 的binlog时，写到本地日志，relay log。 sql_thread 处理B上的 relay log ，解析binlog，对B进行重写。
