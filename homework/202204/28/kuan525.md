#### 数据库主从复制的原理是什么？

MySQL主从复制涉及到三个线程，一个运行在主节点（log dump thread），其余两个(I/O thread, SQL thread)运行在从节点:

## **5.1主节点log dump线程**

当从节点连接主节点时，主节点会为其创建一个log dump 线程，用于发送和读取bin-log的内容。在读取bin-log中的操作时，log dump线程会对主节点上的bin-log加锁，当读取完成，在发送给从节点之前，锁会被释放。**主节点会为自己的每一个从节点创建一个log dump线程**。

## **5.2从节点I/O线程**

当从节点上执行`start slave`命令之后，从节点会创建一个I/O线程用来连接主节点，请求主库中更新的bin-log。I/O线程接收到主节点的blog dump进程发来的更新之后，保存在本地**relay-log（中继日志）**中。

## **5.3从节点SQL线程**

SQL线程负责读取relay-log中的内容，解析成具体的操作并执行，最终保证主从数据的一致性。

对于每一个主从连接，都需要这三个进程来完成。当主节点有多个从节点时，主节点会为每一个当前连接的从节点建一个**log dump进程**，而每个从节点都有自己的**I/O进程，SQL进程**。从节点用两个线程将从主库拉取更新和执行分成独立的任务，这样在执行同步数据任务的时候，不会降低读操作的性能。比如，如果从节点没有运行，此时I/O进程可以很快从主节点获取更新，尽管SQL进程还没有执行。如果在SQL进程执行之前从节点服务停止，至少I/O进程已经从主节点拉取到了最新的变更并且保存在本地relay日志中，当服务再次起来之后，就可以完成数据的同步。

要实施复制，首先**必须打开 Master端的binary log（bin-log功能，否则无法实现**。

因为整个复制过程实际上就是Slave 从Master 端获取该日志然后再在自己身上完全顺序的执行日志中所记录的各种操作。

## **5.4复制的基本过程**

1. 在从节点上执行sart slave命令开启主从复制开关，开始进行主从复制。从节点上的I/O 进程连接主节点，并请求从指定日志文件的指定位置（或者从最开始的日志）之后的日志内容；
2. 主节点接收到来自从节点的I/O请求后，通过负责复制的I/O进程（log dump 线程）根据请求信息读取指定日志指定位置之后的日志信息，返回给从节点。返回信息中除了日志所包含的信息之外，还包括本次返回的信息的bin-log file 的以及bin-log position（bin-log中的下一个指定更新位置）；
3. 从节点的I/O进程接收到主节点发送过来的日志内容、日志文件及位置点后，将接收到的日志内容更新到本机的relay-log（中继日志）的文件（Mysql-relay-bin.xxx）的最末端，并将读取到的binary log（bin-log）文件名和位置保存到**master-info文件**中，以便在下一次读取的时候能够清楚的告诉Master“我需要从某个bin-log 的哪个位置开始往后的日志内容，请发给我”；
4. Slave 的 SQL线程检测到relay-log 中新增加了内容后，会将relay-log的内容解析成在主节点上实际执行过SQL语句，然后在本数据库中按照解析出来的顺序执行，并在**relay-log.info**中记录当前应用中继日志的文件名和位置点。



ref：[参考文档](https://blog.nowcoder.net/n/b90c959437734a8583fddeaa6d102e43)
