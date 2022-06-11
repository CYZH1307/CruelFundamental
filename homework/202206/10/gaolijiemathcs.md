### Linux 读写数据的整个流程，内核状态切换

Linux读写操作需要CPU中断，发起I/O请求等待数据读取和拷贝完成。I/O中断会导致CPU的上下文切换：

读

- 用户进程向CPU发起read系统调用读取数据，用户态切换为内核态，然后一直阻塞等待数据的返回。
- CPU接收到指令以后，对磁盘发起I/O请求，将磁盘数据线放入磁盘控制缓冲区。

- 数据准备完以后，磁盘向CPU发起I/O中断。
- CPU收到I/O中断以后将磁盘向CPU发起I/O中断。
- CPU收到I/O中断以后将磁盘缓冲区的数据，拷贝到内存缓冲区，然后再冲内核缓冲区拷贝到用户缓冲区。
- 用户进程向内核态切换回用户态，接触阻塞状态，然后等待CPU下一个等待时钟。

写（DMA方式）

- 用户发起写请求，调用write()向内核发起系统调用，上下文用户态（user space）切换为内核态（kernel space）。
- CPU将用户缓冲区（user buffer）中的数据拷贝到内核空间（kernel space）的网络缓冲区socket buffer.
- CPU通过DAM数据从网络缓冲区，拷贝到网卡进行数据传输。
- 上下文从内核态，切换会用户态，write系统调用执行返回。



ref:https://edgar615.github.io/linux-io-read-method.html