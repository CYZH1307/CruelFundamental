### 简述epoll 和 poll, select 的区别

前提：内核维护了端口->socket->进程的映射

select：每次数据包到了之后，内核把进程唤醒，进程自己去看哪个socket里拿到了数据

epoll：每次数据包到了之后，内核把进程唤醒，并且维护了一下哪个socket拿到了数据(log n)，进程能o1时间找到对应的socket ，epoll用到了map+list的经典结构(map 维护索引，list维护顺序)

poll：和select差不多，但能支持更大的文件列表