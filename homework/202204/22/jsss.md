# 谈谈什么是零拷贝

传统的Linux操作系统的I/O接口是基于数据拷贝的, 即当调用`read`或`write`系统调用的时候, 操作系统会将数据先读入/写入到内核缓冲区中, 然后写入磁盘/读到进程的内存空间中, 这样的好处是无需等待较慢的磁盘IO, 由内核提供高速缓冲区. 尽管如此, 数据在传输过程中还是存在极大的拷贝开销, 降低了数据传输性能.

**零拷贝技术**可以减少不要的数据拷贝操作, 提升数据传输的性能. 零拷贝并不是没有拷贝数据, 而是减少用户态/内核态的切换次数以及数据的拷贝次数.

零拷贝的实现方式有:
- mmap + write
    mmap为虚拟文件映射, 它将文件直接映射到进程的虚拟地址空间中, 因此内核缓冲区和应用进程缓冲区共享, 所以可以减少一次从内核缓冲区到用户进程缓冲区的一次拷贝操作.
- sendfile
    sendfile是Linux 2.1内核版本后提供的一个系统调用. 其表示在两个文件描述符中传输数据, 它是在操作系统内核中操作的, 避免了数据的多次拷贝. 具体是先将数据从硬盘中读入到内核缓冲区中(DMA拷贝), 然后把内核缓冲区的文件描述符信息(**数据内存起始地址和偏移量**)发送给`socket`缓冲区, 最后DMA控制器根据文件描述符信息, 将数据从内核缓冲区拷贝到网卡. 实现真正意义上的零拷贝: 即两次数据拷贝都是由DMA完成的, CPU没有处理具体的数据拷贝任务, 从而提升了数据传输的效率.


