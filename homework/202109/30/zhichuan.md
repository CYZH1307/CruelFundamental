# io 复用

- context switch频繁，所以有IO多路复用

- io复用即是操作系统提供一种函数可以监控多个fd，linux有select，poll，epoll

- select，poll，epoll其实都是同步I/O，在事件readt后自己负责进行io，也就是说这个io过程是block的。

- select 有文件数量限制，默认1024（x64 2048)，100万并发，poll用链表，没有限制。

- select，poll 轮询所有fd，而epoll在只要查看就绪列表，通过回调机制节省cost；select，poll每次调用需要拷贝所有fd，而epoll只要一次拷贝。

- poll并发越多，性能逐渐下降，epoll红黑树结构，相对稳定。

- epoll线程安全的，而 select 和 poll 不是。

- epoll事件驱动

- epoll 用了 mmap 共享了用户态和内核态的空间，避免了数据的来回拷贝。
