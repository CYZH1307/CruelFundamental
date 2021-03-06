#### 谈谈什么是零拷贝

**传统的IO方式：**

底层实际上通过调用read()和write()来实现。

通过read()把数据从硬盘读取到内核缓冲区，再复制到用户缓冲区；然后再通过write()写入到socket缓冲区，最后写入网卡设备。

整个过程发生了**4次用户态和内核态的上下文切换和4次拷贝**，具体流程如下：

1. 用户进程通过read()方法向操作系统发起调用，此时上下文从用户态转向内核态
2. DMA控制器把数据从硬盘中拷贝到读缓冲区
3. CPU把读缓冲区数据拷贝到应用缓冲区，上下文从内核态转为用户态，read()返回
4. 用户进程通过write()方法发起调用，上下文从用户态转为内核态
5. CPU将应用缓冲区中数据拷贝到socket缓冲区
6. DMA控制器把数据从socket缓冲区拷贝到网卡，上下文从内核态切换回用户态，write()返回

**零拷贝技术**

零拷贝技术是指计算机执行操作时，CPU不需要先将数据从某处内存复制到另一个特定区域，这种技术通常用于通过网络传输文件时节省CPU周期和内存带宽。

那么对于零拷贝而言，并非真的是完全没有数据拷贝的过程，只不过是减少用户态和内核态的切换次数以及CPU拷贝的次数。

这里列举几种常见的零拷贝技术。

1. mmap+write
2. sendfile
3. sendfile+DMA Scatter/Gather