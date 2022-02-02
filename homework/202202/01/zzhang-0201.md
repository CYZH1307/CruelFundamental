# Redis 持久化机制

snapshot模式

- 多线程copy on write机制，存储整个内存快照
- Redis是有状态的节点，每次客户端请求服务端对数据的写操作，都会触发状态的改变。在完成线上请求的同时，进行文件IO操作，把snapshot进行保存。
  - save：可通过客户端显式触发、在redis shutdown时触发。save本身通过redis命令，单线程串行化执行保存，阻塞其他的操作。
  - bgsave：可通过客户端显式触发、定时任务触发、在主从模式下由从节点触发。



AOF (append-only file)模式 

- 存对数据的变化，只记录内存改变的指令。
- 三种同步策略：
  - always 每次执行完命令，直接同步触发fsync方法，强制写入磁盘。吞吐低，容错能力强
  - every second 每秒异步触发一次fsync
  - no 不显式调用fsync方法，由操作系统决定

