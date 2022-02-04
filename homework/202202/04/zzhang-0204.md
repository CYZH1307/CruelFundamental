为什么Redis使用单进程？

- Redis的瓶颈在内存和带宽，CPU不是Redis的瓶颈，因此单进程不会影响性能
- 单进程实现简单，不需要各种锁，不用处理加锁、释放锁、解决死锁等操作
- 采用单线程，避免了不必要的上下文切换和竞争条件，也不存在多进程或者多线程导致的切换而消耗 CPU
- 可以在单机开多个Redis实例：Redis是key-value数据库，数据之间没有约束，只要客户端分清哪些key放在哪个Redis进程上就可以了