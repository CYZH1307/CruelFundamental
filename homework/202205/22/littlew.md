 进程、线程、协程的区别

1. 进程。是OS分配和调度资源的独立单位，由程序、数据集合和进程控制块三部分组成。每个进程独占内存，相互隔离。

2. 线程。是对进程按照执行顺序的再分。一个进程可以有一个或多个线程，多个线程共享内存，解决了进程切换开销大的问题

3. 协程。一个线程可以拥有多个协程，协程旨在解决的I/O线程耗时和阻塞队列的问题。IO阻塞时，由协程的调度器进行主动调度，通过将数据流立刻yield掉，记录栈上数据，并在阻塞完成后恢复数据。

