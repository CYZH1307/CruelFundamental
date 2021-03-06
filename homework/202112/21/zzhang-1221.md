# IO模型

IO模型有哪几种? 什么是信号驱动IO？什么是IO多路复用？

Reference:

https://zhuanlan.zhihu.com/p/115912936

### IO模型

#### 阻塞IO，非阻塞IO

阻塞IO：当应用发起读取数据申请时，在哪和数据没有准备好前，会一直处于等待数据的状态，直到内核把数据准备好了交给应用才结束。

非阻塞IO：当应用发起读取数据申请时，若内核没有准备好数据会告诉应用（返回`EWOULDBLOCK` 错误），因此应用若要读数据，需要不断发请求。

### IO多路复用

系统提供一个函数（`select`, `poll`, `epoll`）监控多个fd的操作。应用线程通过调用`select` 函数同时监控多个fd。`select` 监控的fd中只要有任何一个数据状态准备就绪了，`select` 函数就会返回可读状态。这时询问线程再去通知处理数据的线程，对应线程此时再发请求去读取数据。

### 信号驱动IO：

`select` 通过轮询的方式来监控多个fd，大部分情况下都是没有数据可读。因此，可以建立信号驱动IO：在调用sigaction时候建立一个SIGIO的信号联系，当内核数据准备好之后再通过SIGIO信号通知线程数据准备好后的可读状态，当线程收到可读状态的信号后，此时再向内核发起读取数据的请求