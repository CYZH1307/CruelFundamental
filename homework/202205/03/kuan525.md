#### MySQL是怎么保证主从一致的

以 `A:M-B:S` 结构为例子：

A 的更新流程

* A在接受一个来自客户端的更新请求之后，首先在`undolog` 内存中写入，然后存入硬盘，在`redolog`恢复日志 `prepare` 阶段完成之后，写入 `binlog` ，最后再 `commit` 整个 `prepare` ，完成 A这边的一套完整的执行内部事务的更新逻辑。
* B的同步流程
  B 和 A 之间维持了一个长链接，在B上，我们会设置A的账号信息，以及日志位置和偏移量，同步时，我们会主动执行一次 `start slave` 命令，启动 `io_thread` 和 `sql_thread` 两个线程 。`io_thread` 与A建立请求，主库 A 接受请求，然后检验，把A的`binlog` 发送给B 。B终于拿到A 的`binlog` ，写到本地日志，`relay log` 。 `sql_thread` 处理B上的 `relay log` ，解析`binlog`。

> 值得注意的是，在 Mysql5.6 之后，Mysql开始支持多线程复制，这减少了主备延迟的问题

ref:[参考链接](https://blog.csdn.net/qq_28018283/article/details/87863451)