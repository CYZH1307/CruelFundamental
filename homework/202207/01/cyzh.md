## 2022/07/01

### 什么是MVCC Multi-Version Concurrent Control？

`MVCC`，`Multi-Version Concurrency Control`，多版本并发控制。`MVCC `是一种并发控制的方法，一般在数据库管理系统中，实现对数据库的并发访问；在编程语言中实现事务内存。

当一个 `MVCC `数据库需要更一个一条数据记录的时候，它不会直接用新数据覆盖旧数据，而是将旧数据标记为过时`obsolete`并在别处增加新版本的数据。这样就会有存储多个版本的数据，但是只有一个是最新的。这种方式允许读者读取在他读之前已经存在的数据，即使这些在读的过程中半路被别人修改、删除了，也对先前正在读的用户没有影响。这种多版本的方式避免了填充删除操作在内存和磁盘存储结构造成的空洞的开销，但是需要系统周期性整理`sweep through`以真实删除老的、过时的数据。



#### ref:

https://blog.csdn.net/yajie_12/article/details/80453863