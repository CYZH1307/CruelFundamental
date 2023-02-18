# 你知道的中间件有哪些？

## kafka

1. 生产者

kafka会把收到消息写入硬盘中. 采用了顺序IO和内存文件映射(Memory Mapped File)技术来提高性能.

2. 消费者

- Zero-copy.

传统的通过`read/write`系统调用的方式进行网络文件的传输需要四次copy.
1. read将数据从硬盘拷贝到内核buffer中.
2. read将数据从内核buffer拷贝到用户内存空间.
3. write将数据从用户内存空间拷贝到内核socket的buffer中.
4. write将数据从socket的buffer中拷贝到网卡设备进行发送.

通过`sendFile`系统调用实现了内核零拷贝.

1. 将数据从硬盘拷贝进内核空间(DMA).
2. 内核socket的中buffer记录发送数据的基址和偏移量.
3. 利用socket的中buffer记录的基址和偏移量将数据从内核缓冲区拷贝到网络设备进行发送.

对于内核来说, 这个过程**零拷贝**的.
